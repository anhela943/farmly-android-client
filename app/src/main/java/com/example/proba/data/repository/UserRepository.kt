package com.example.proba.data.repository

import com.example.proba.data.model.request.CreateReviewRequest
import com.example.proba.data.model.response.ErrorResponse
import com.example.proba.data.model.response.ProductsListResponse
import com.example.proba.data.model.response.RegisterErrorResponse
import com.example.proba.data.model.response.ProfileResponse
import com.example.proba.data.model.response.ReviewsResponse
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

    suspend fun updateProfile(
        fullName: String? = null,
        email: String? = null,
        password: String? = null,
        phoneNumber: String? = null,
        city: String? = null,
        description: String? = null,
        imageFile: File? = null,
        imageMimeType: String? = null
    ): Resource<ProfileResponse> = withContext(Dispatchers.IO) {
        try {
            val textType = "text/plain".toMediaTypeOrNull()
            val fullNameBody = fullName?.toRequestBody(textType)
            val emailBody = email?.toRequestBody(textType)
            val passwordBody = password?.toRequestBody(textType)
            val phoneNumberBody = phoneNumber?.toRequestBody(textType)
            val cityBody = city?.toRequestBody(textType)
            val descriptionBody = description?.toRequestBody(textType)

            val imagePart = imageFile?.let { file ->
                val mimeType = (imageMimeType ?: "image/jpeg").toMediaTypeOrNull()
                val requestFile = file.asRequestBody(mimeType)
                MultipartBody.Part.createFormData("image", file.name, requestFile)
            }

            val response = userApi.updateProfile(
                fullName = fullNameBody,
                email = emailBody,
                password = passwordBody,
                phoneNumber = phoneNumberBody,
                city = cityBody,
                description = descriptionBody,
                image = imagePart
            )

            if (response.isSuccessful) {
                response.body()?.let { profile ->
                    return@withContext Resource.Success(profile)
                }
                return@withContext Resource.Error("Empty response body")
            }

            val errorBody = response.errorBody()?.string()
            val errorResponse = parseError(errorBody)
            Resource.Error(
                message = errorResponse?.message ?: "Failed to update profile",
                errors = errorResponse?.errors?.map { "${it.field}: ${it.message}" }
            )
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error occurred")
        }
    }

    suspend fun getUserProducts(userId: String): Resource<ProductsListResponse> = withContext(Dispatchers.IO) {
        try {
            val response = userApi.getUserProducts(userId)

            if (response.isSuccessful) {
                response.body()?.let { productsResponse ->
                    return@withContext Resource.Success(productsResponse)
                }
                return@withContext Resource.Error("Empty response body")
            }

            val errorBody = response.errorBody()?.string()
            val errorResponse = parseError(errorBody)
            Resource.Error(
                message = errorResponse?.message ?: "Failed to load user products"
            )
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error occurred")
        }
    }

    suspend fun getUserReviews(userId: String): Resource<ReviewsResponse> = withContext(Dispatchers.IO) {
        try {
            val response = userApi.getUserReviews(userId)

            if (response.isSuccessful) {
                response.body()?.let { reviewsResponse ->
                    return@withContext Resource.Success(reviewsResponse)
                }
                return@withContext Resource.Error("Empty response body")
            }

            val errorBody = response.errorBody()?.string()
            val errorResponse = parseError(errorBody)
            Resource.Error(
                message = errorResponse?.message ?: "Failed to load reviews"
            )
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error occurred")
        }
    }

    suspend fun createReview(producerId: Int, rating: Int, comment: String): Resource<Unit> = withContext(Dispatchers.IO) {
        try {
            val request = CreateReviewRequest(
                producerId = producerId,
                rating = rating,
                comment = comment
            )
            val response = userApi.createReview(request)

            if (response.isSuccessful) {
                return@withContext Resource.Success(Unit)
            }

            val errorBody = response.errorBody()?.string()
            // Try to parse as RegisterErrorResponse (array of error messages)
            val arrayErrorResponse = try {
                errorBody?.let { gson.fromJson(it, RegisterErrorResponse::class.java) }
            } catch (e: Exception) {
                null
            }
            if (arrayErrorResponse != null && arrayErrorResponse.error.isNotEmpty()) {
                return@withContext Resource.Error(
                    message = arrayErrorResponse.error.first(),
                    errors = arrayErrorResponse.error
                )
            }

            // Fall back to standard ErrorResponse
            val errorResponse = parseError(errorBody)
            Resource.Error(
                message = errorResponse?.message ?: "Failed to create review",
                errors = errorResponse?.errors?.map { it.message }
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
