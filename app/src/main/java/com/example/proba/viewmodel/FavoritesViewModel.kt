package com.example.proba.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.proba.model.ProductUi

class FavoritesViewModel : ViewModel() {
    private val _favorites = mutableStateListOf<ProductUi>()
    val favorites = _favorites

    fun isFavorite(product: ProductUi): Boolean {
        return _favorites.any { it.id == product.id }
    }

    fun toggleFavorite(product: ProductUi) {
        if (isFavorite(product)) {
            removeFavorite(product)
        } else {
            addFavorite(product)
        }
    }

    fun addFavorite(product: ProductUi) {
        if (!isFavorite(product)) {
            _favorites.add(product)
        }
    }

    fun removeFavorite(product: ProductUi) {
        _favorites.removeAll { it.id == product.id }
    }
}
