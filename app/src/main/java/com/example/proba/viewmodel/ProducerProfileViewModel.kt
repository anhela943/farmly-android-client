package com.example.proba.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.proba.data.model.response.ProductListItem
import com.example.proba.data.model.response.ProfileResponse
import com.example.proba.data.model.response.Review
import com.example.proba.data.repository.UserRepository
import com.example.proba.util.Resource
import kotlinx.coroutines.launch

class ProducerProfileViewModel(
    private val userId: String
) : ViewModel() {

    private val userRepository = UserRepository()

    var profileState: Resource<ProfileResponse> by mutableStateOf(Resource.Loading)
        private set

    var productsState: Resource<List<ProductListItem>> by mutableStateOf(Resource.Loading)
        private set

    var reviewsState: Resource<List<Review>> by mutableStateOf(Resource.Loading)
        private set

    init {
        loadAll()
    }

    fun loadAll() {
        loadProfile()
        loadProducts()
        loadReviews()
    }

    fun loadProfile() {
        viewModelScope.launch {
            profileState = Resource.Loading
            profileState = userRepository.getProfile(userId)
        }
    }

    fun loadProducts() {
        viewModelScope.launch {
            productsState = Resource.Loading
            when (val result = userRepository.getUserProducts(userId)) {
                is Resource.Success -> {
                    productsState = Resource.Success(result.data.products)
                }
                is Resource.Error -> {
                    productsState = Resource.Error(result.message, result.errors)
                }
                is Resource.Loading -> {}
            }
        }
    }

    fun loadReviews() {
        viewModelScope.launch {
            reviewsState = Resource.Loading
            when (val result = userRepository.getUserReviews(userId)) {
                is Resource.Success -> {
                    reviewsState = Resource.Success(result.data.reviews)
                }
                is Resource.Error -> {
                    reviewsState = Resource.Error(result.message, result.errors)
                }
                is Resource.Loading -> {}
            }
        }
    }

    class Factory(private val userId: String) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProducerProfileViewModel(userId) as T
        }
    }
}
