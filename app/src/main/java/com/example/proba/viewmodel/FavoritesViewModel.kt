package com.example.proba.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proba.data.model.response.FavoriteProductItem
import com.example.proba.data.model.response.FavoritesListResponse
import com.example.proba.data.repository.FavoritesRepository
import com.example.proba.model.ProductUi
import com.example.proba.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel : ViewModel() {

    private val repository = FavoritesRepository()

    private val _favoritesState = MutableStateFlow<Resource<List<ProductUi>>>(Resource.Loading)
    val favoritesState: StateFlow<Resource<List<ProductUi>>> = _favoritesState.asStateFlow()

    private val _actionState = MutableStateFlow<Resource<String>?>(null)
    val actionState: StateFlow<Resource<String>?> = _actionState.asStateFlow()

    private val _favoriteIds = MutableStateFlow<Set<String>>(emptySet())
    val favoriteIds: StateFlow<Set<String>> = _favoriteIds.asStateFlow()

    init {
        loadFavorites()
    }

    fun loadFavorites() {
        viewModelScope.launch {
            _favoritesState.value = Resource.Loading
            when (val result = repository.getFavorites()) {
                is Resource.Success -> {
                    val products = result.data.products.map { it.toProductUi() }
                    val ids = products.map { it.id }.toSet()
                    _favoriteIds.value = ids
                    _favoritesState.value = Resource.Success(products)
                }
                is Resource.Error -> {
                    _favoritesState.value = Resource.Error(
                        result.message,
                        result.errors
                    )
                }
                is Resource.Loading -> {
                    _favoritesState.value = Resource.Loading
                }
            }
        }
    }

    fun isFavorite(productId: String): Boolean {
        return _favoriteIds.value.contains(productId)
    }

    fun isFavorite(product: ProductUi): Boolean {
        return isFavorite(product.id)
    }

    fun toggleFavorite(productId: String) {
        if (isFavorite(productId)) {
            removeFavorite(productId)
        } else {
            addFavorite(productId)
        }
    }

    fun toggleFavorite(product: ProductUi) {
        toggleFavorite(product.id)
    }

    fun addFavorite(productId: String) {
        // Optimistic update
        _favoriteIds.value = _favoriteIds.value + productId

        viewModelScope.launch {
            _actionState.value = Resource.Loading
            when (val result = repository.addFavorite(productId)) {
                is Resource.Success -> {
                    _actionState.value = Resource.Success(result.data.message)
                    loadFavorites()
                }
                is Resource.Error -> {
                    // Revert on error
                    _favoriteIds.value = _favoriteIds.value - productId
                    _actionState.value = Resource.Error(
                        result.message,
                        result.errors
                    )
                }
                is Resource.Loading -> {
                    _actionState.value = Resource.Loading
                }
            }
        }
    }

    fun removeFavorite(productId: String) {
        // Optimistic update
        _favoriteIds.value = _favoriteIds.value - productId

        viewModelScope.launch {
            _actionState.value = Resource.Loading
            when (val result = repository.removeFavorite(productId)) {
                is Resource.Success -> {
                    _actionState.value = Resource.Success(result.data.message)
                    loadFavorites()
                }
                is Resource.Error -> {
                    // Revert on error
                    _favoriteIds.value = _favoriteIds.value + productId
                    _actionState.value = Resource.Error(
                        result.message,
                        result.errors
                    )
                }
                is Resource.Loading -> {
                    _actionState.value = Resource.Loading
                }
            }
        }
    }

    fun clearActionState() {
        _actionState.value = null
    }
}

private fun FavoriteProductItem.toProductUi(): ProductUi {
    return ProductUi(
        id = id,
        name = name,
        price = price,
        producer = seller.fullName,
        producerReview = seller.overallReview.toDouble(),
        city = seller.city,
        imageUrl = imageUrl,
        producerImageUrl = seller.imageUrl,
        producerId = seller.id
    )
}
