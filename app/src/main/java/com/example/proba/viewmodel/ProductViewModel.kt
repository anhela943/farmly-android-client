package com.example.proba.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proba.data.model.response.ProductListItem
import com.example.proba.data.repository.ProductRepository
import com.example.proba.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val repository = ProductRepository()

    private val _products = MutableStateFlow<Resource<List<ProductListItem>>>(Resource.Loading)
    val products: StateFlow<Resource<List<ProductListItem>>> = _products.asStateFlow()

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            _products.value = Resource.Loading
            when (val result = repository.getProducts(offset = 0, limit = 5)) {
                is Resource.Success -> {
                    _products.value = Resource.Success(result.data.products)
                }
                is Resource.Error -> {
                    _products.value = Resource.Error(result.message, result.errors)
                }
                is Resource.Loading -> {}
            }
        }
    }
}
