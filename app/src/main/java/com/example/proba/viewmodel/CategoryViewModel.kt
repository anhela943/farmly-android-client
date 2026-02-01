package com.example.proba.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proba.data.model.response.CategoryItem
import com.example.proba.data.repository.CategoryRepository
import com.example.proba.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {
    private val repository = CategoryRepository()

    private val _categories = MutableStateFlow<Resource<List<CategoryItem>>>(Resource.Loading)
    val categories: StateFlow<Resource<List<CategoryItem>>> = _categories.asStateFlow()

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            _categories.value = Resource.Loading
            when (val result = repository.getCategories()) {
                is Resource.Success -> {
                    _categories.value = Resource.Success(result.data.categories)
                }
                is Resource.Error -> {
                    _categories.value = Resource.Error(result.message, result.errors)
                }
                is Resource.Loading -> {}
            }
        }
    }
}
