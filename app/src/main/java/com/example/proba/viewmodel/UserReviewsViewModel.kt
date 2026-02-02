package com.example.proba.viewmodel

import android.util.Base64
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.proba.data.model.response.ReviewsResponse
import com.example.proba.data.repository.UserRepository
import com.example.proba.util.Resource
import com.example.proba.util.TokenManager
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UserReviewsViewModel(
    private val tokenManager: TokenManager
) : ViewModel() {

    private val userRepository = UserRepository()

    var reviewsState: Resource<ReviewsResponse> by mutableStateOf(Resource.Loading)
        private set

    init {
        loadReviews()
    }

    fun loadReviews() {
        viewModelScope.launch {
            reviewsState = Resource.Loading

            val token = tokenManager.accessToken.first()
            if (token == null) {
                reviewsState = Resource.Error("Not logged in")
                return@launch
            }

            val userId = extractUserIdFromToken(token)
            if (userId == null) {
                reviewsState = Resource.Error("Invalid token")
                return@launch
            }

            reviewsState = userRepository.getUserReviews(userId)
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
            return UserReviewsViewModel(tokenManager) as T
        }
    }
}
