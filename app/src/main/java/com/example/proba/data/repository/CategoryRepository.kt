package com.example.proba.data.repository

import com.example.proba.data.model.response.CategoryResponse
import com.example.proba.data.remote.ApiClient
import com.example.proba.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoryRepository {
    private val categoryApi = ApiClient.categoryApiService

    suspend fun getCategories(): Resource<CategoryResponse> = withContext(Dispatchers.IO) {
        try {
            val response = categoryApi.getCategories()

            if (response.isSuccessful) {
                response.body()?.let { categoryResponse ->
                    return@withContext Resource.Success(categoryResponse)
                }
                return@withContext Resource.Error("Empty response body")
            }

            Resource.Error("Failed to load categories")
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error occurred")
        }
    }
}
