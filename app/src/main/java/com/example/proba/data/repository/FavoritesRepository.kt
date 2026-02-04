package com.example.proba.data.repository

import com.example.proba.data.model.response.FavoriteActionResponse
import com.example.proba.data.model.response.FavoritesListResponse
import com.example.proba.data.model.response.RegisterErrorResponse
import com.example.proba.data.remote.ApiClient
import com.example.proba.util.Resource
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoritesRepository {

    private val userApi = ApiClient.userApiService
    private val gson = Gson()

    suspend fun getFavorites(): Resource<FavoritesListResponse> = withContext(Dispatchers.IO) {
        try {
            val response = userApi.getFavorites()

            if (response.isSuccessful) {
                response.body()?.let { favoritesResponse ->
                    return@withContext Resource.Success(favoritesResponse)
                }
                return@withContext Resource.Error("Empty response body")
            }

            val errorBody = response.errorBody()?.string()
            val errorResponse = parseArrayError(errorBody)
            Resource.Error(
                message = errorResponse?.error?.firstOrNull() ?: "Failed to load favorites",
                errors = errorResponse?.error
            )
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error occurred")
        }
    }

    suspend fun addFavorite(productId: String): Resource<FavoriteActionResponse> = withContext(Dispatchers.IO) {
        try {
            val response = userApi.addFavorite(productId)

            if (response.isSuccessful) {
                response.body()?.let { actionResponse ->
                    return@withContext Resource.Success(actionResponse)
                }
                return@withContext Resource.Error("Empty response body")
            }

            val errorBody = response.errorBody()?.string()
            val errorResponse = parseArrayError(errorBody)
            Resource.Error(
                message = errorResponse?.error?.firstOrNull() ?: "Failed to add to favorites",
                errors = errorResponse?.error
            )
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error occurred")
        }
    }

    suspend fun removeFavorite(productId: String): Resource<FavoriteActionResponse> = withContext(Dispatchers.IO) {
        try {
            val response = userApi.removeFavorite(productId)

            if (response.isSuccessful) {
                response.body()?.let { actionResponse ->
                    return@withContext Resource.Success(actionResponse)
                }
                return@withContext Resource.Error("Empty response body")
            }

            val errorBody = response.errorBody()?.string()
            val errorResponse = parseArrayError(errorBody)
            Resource.Error(
                message = errorResponse?.error?.firstOrNull() ?: "Failed to remove from favorites",
                errors = errorResponse?.error
            )
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error occurred")
        }
    }

    private fun parseArrayError(errorBody: String?): RegisterErrorResponse? {
        return try {
            errorBody?.let { gson.fromJson(it, RegisterErrorResponse::class.java) }
        } catch (e: Exception) {
            null
        }
    }
}
