package com.example.proba.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.proba.data.model.response.ProductDetailResponse
import com.example.proba.data.repository.ProductRepository
import com.example.proba.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val productId: String
) : ViewModel() {

    private val repository = ProductRepository()

    private val _productState = MutableStateFlow<Resource<ProductDetailResponse>>(Resource.Loading)
    val productState: StateFlow<Resource<ProductDetailResponse>> = _productState.asStateFlow()

    init {
        fetchProduct()
    }

    private fun fetchProduct() {
        viewModelScope.launch {
            _productState.value = Resource.Loading
            _productState.value = repository.getProductById(productId)
        }
    }

    class Factory(private val productId: String) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProductDetailViewModel(productId) as T
        }
    }
}
