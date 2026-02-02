package com.example.proba.viewmodel

import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.proba.data.model.response.ProductListItem
import com.example.proba.data.repository.UserRepository
import com.example.proba.util.Resource
import com.example.proba.util.TokenManager
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MyProductsViewModel(
    private val tokenManager: TokenManager
) : ViewModel() {

    private val userRepository = UserRepository()

    private val _products = MutableStateFlow<Resource<List<ProductListItem>>>(Resource.Loading)
    val products: StateFlow<Resource<List<ProductListItem>>> = _products.asStateFlow()

    init {
        loadMyProducts()
    }

    fun loadMyProducts() {
        viewModelScope.launch {
            _products.value = Resource.Loading

            val token = tokenManager.accessToken.first()
            if (token == null) {
                _products.value = Resource.Error("Not logged in")
                return@launch
            }

            val userId = extractUserIdFromToken(token)
            if (userId == null) {
                _products.value = Resource.Error("Invalid token")
                return@launch
            }

            when (val result = userRepository.getUserProducts(userId)) {
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

    private fun extractUserIdFromToken(token: String): String? {
        return try {
            val parts = token.split(".")
            if (parts.size < 2) return null

            val payload = parts[1]
            val decodedBytes = Base64.decode(payload, Base64.URL_SAFE or Base64.NO_PADDING)
            val decodedString = String(decodedBytes, Charsets.UTF_8)

            val jsonObject = Gson().fromJson(decodedString, JsonObject::class.java)
            when {
                jsonObject.has("id") -> jsonObject.get("id").asString
                jsonObject.has("sub") -> jsonObject.get("sub").asString
                else -> null
            }
        } catch (e: Exception) {
            null
        }
    }

    class Factory(private val tokenManager: TokenManager) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MyProductsViewModel(tokenManager) as T
        }
    }
}
