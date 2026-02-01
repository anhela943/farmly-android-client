package com.example.proba.data.remote

import com.example.proba.data.model.response.ProductResponse
import com.example.proba.data.model.response.ProductsListResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ProductApiService {

    @GET("api/products")
    suspend fun getProducts(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<ProductsListResponse>

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
