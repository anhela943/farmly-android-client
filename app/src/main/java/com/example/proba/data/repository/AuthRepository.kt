package com.example.proba.data.repository


import com.example.proba.data.model.request.LoginRequest
import com.example.proba.data.model.request.RegisterRequest
import com.example.proba.data.model.response.ErrorResponse
import com.example.proba.data.remote.ApiClient
import com.example.proba.util.Resource
import com.example.proba.util.TokenManager
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository(private val tokenManager: TokenManager) {

    private val authApi = ApiClient.authApiService
    private val gson = Gson()

    suspend fun login(email: String, password: String): Resource<Unit> = withContext(Dispatchers.IO) {
        try {
            val response = authApi.login(LoginRequest(email, password))

            if (response.isSuccessful) {
                response.body()?.let { authResponse ->
                    tokenManager.saveToken(authResponse.accessToken)
                    return@withContext Resource.Success(Unit)
                }
                return@withContext Resource.Error("Empty response body")
            }

            val errorBody = response.errorBody()?.string()
            val errorResponse = parseError(errorBody)
            Resource.Error(
                message = errorResponse?.message ?: "Login failed",
                errors = errorResponse?.errors?.map { "${it.field}: ${it.message}" }
            )
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error occurred")
        }
    }

    suspend fun register(
        fullName: String,
        city: String,
        phoneNumber: String,
        email: String,
        password: String
    ): Resource<Unit> = withContext(Dispatchers.IO) {
        try {
            val request = RegisterRequest(fullName, city, phoneNumber, email, password)
            val response = authApi.register(request)

            if (response.isSuccessful) {
                response.body()?.let { authResponse ->
                    tokenManager.saveToken(authResponse.accessToken)
                    return@withContext Resource.Success(Unit)
                }
                return@withContext Resource.Error("Empty response body")
            }

            val errorBody = response.errorBody()?.string()
            val errorResponse = parseError(errorBody)
            Resource.Error(
                message = errorResponse?.message ?: "Registration failed",
                errors = errorResponse?.errors?.map { "${it.field}: ${it.message}" }
            )
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error occurred")
        }
    }

    suspend fun logout() {
        tokenManager.clearToken()
    }

    suspend fun isLoggedIn(): Boolean {
        return tokenManager.hasToken()
    }

    private fun parseError(errorBody: String?): ErrorResponse? {
        return try {
            errorBody?.let { gson.fromJson(it, ErrorResponse::class.java) }
        } catch (e: Exception) {
            null
        }
    }
}
