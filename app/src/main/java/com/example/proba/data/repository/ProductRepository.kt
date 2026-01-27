package com.example.proba.data.repository

import com.example.proba.data.model.response.ErrorResponse
import com.example.proba.data.model.response.ProductResponse
import com.example.proba.data.remote.ApiClient
import com.example.proba.util.Resource
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class ProductRepository {
    private val productApi = ApiClient.productApiService
    private val gson = Gson()

    suspend fun createProduct(
        name: String,
        description: String,
        price: Double,
        categoryId: Int,
        imageFile: File
    ): Resource<ProductResponse> = withContext(Dispatchers.IO) {
        try {
            val nameBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
            val descriptionBody = description.toRequestBody("text/plain".toMediaTypeOrNull())
            val priceBody = price.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val categoryIdBody = categoryId.toString().toRequestBody("text/plain".toMediaTypeOrNull())

            val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
            val imagePart = MultipartBody.Part.createFormData("image", imageFile.name, requestFile)

            val response = productApi.createProduct(
                name = nameBody,
                description = descriptionBody,
                price = priceBody,
                categoryId = categoryIdBody,
                image = imagePart
            )

            if (response.isSuccessful) {
                response.body()?.let { productResponse ->
                    return@withContext Resource.Success(productResponse)
                }
                return@withContext Resource.Error("Empty response body")
            }

            val errorBody = response.errorBody()?.string()
            val errorResponse = parseError(errorBody)
            Resource.Error(
                message = errorResponse?.message ?: "Failed to create product",
                errors = errorResponse?.errors?.map { "${it.field}: ${it.message}" }
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
