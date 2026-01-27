package com.example.proba.data.remote

import com.example.proba.data.model.response.ProductResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ProductApiService {

    @Multipart
    @POST("api/products/")
    suspend fun createProduct(
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("price") price: RequestBody,
        @Part("categoryId") categoryId: RequestBody,
        @Part image: MultipartBody.Part
    ): Response<ProductResponse>
}
