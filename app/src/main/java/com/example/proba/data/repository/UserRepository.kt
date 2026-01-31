package com.example.proba.data.repository

import com.example.proba.data.model.response.ErrorResponse
import com.example.proba.data.model.response.ProfileResponse
import com.example.proba.data.remote.ApiClient
import com.example.proba.util.Resource
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository {

    private val userApi = ApiClient.userApiService
    private val gson = Gson()

    suspend fun getProfile(userId: String): Resource<ProfileResponse> = withContext(Dispatchers.IO) {
        try {
            val response = userApi.getUserProfile(userId)

            if (response.isSuccessful) {
                response.body()?.let { profile ->
                    return@withContext Resource.Success(profile)
                }
                return@withContext Resource.Error("Empty response body")
            }

            val errorBody = response.errorBody()?.string()
            val errorResponse = parseError(errorBody)
            Resource.Error(
                message = errorResponse?.message ?: "Failed to load profile"
            )
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error occurred")
        }
    }

    private fun parseError(errorBody: String?): ErrorResponse? {
        return try {
            errorBody?.let { gson.fromJson(it, ErrorResponse::class.java) }
        } catch (e: Exception) {
            null
        }
    }
}
