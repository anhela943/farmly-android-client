package com.example.proba.data.remote

import com.example.proba.data.model.response.CategoryResponse
import retrofit2.Response
import retrofit2.http.GET

interface CategoryApiService {

    @GET("api/categories")
    suspend fun getCategories(): Response<CategoryResponse>
}
