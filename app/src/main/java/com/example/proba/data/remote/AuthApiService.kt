package com.example.proba.data.remote

import com.example.proba.data.model.request.LoginRequest
import com.example.proba.data.model.request.RegisterRequest
import com.example.proba.data.model.response.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("api/users/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>
}
