package com.example.proba.data.remote

import com.example.proba.data.model.request.CreateReviewRequest
import com.example.proba.data.model.response.ProductsListResponse
import com.example.proba.data.model.response.ProfileResponse
import com.example.proba.data.model.response.ReviewsResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.Part
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApiService {

    @GET("/api/users/{userId}/profile")
    suspend fun getUserProfile(
        @Path("userId") userId: String
    ): Response<ProfileResponse>

    @GET("/api/users/{userId}/products")
    suspend fun getUserProducts(
        @Path("userId") userId: String
    ): Response<ProductsListResponse>

    @Multipart
    @PATCH("/api/users/profile")
    suspend fun updateProfile(
        @Part("fullName") fullName: RequestBody? = null,
        @Part("email") email: RequestBody? = null,
        @Part("password") password: RequestBody? = null,
        @Part("phoneNumber") phoneNumber: RequestBody? = null,
        @Part("city") city: RequestBody? = null,
        @Part("description") description: RequestBody? = null,
        @Part image: MultipartBody.Part? = null
    ): Response<ProfileResponse>

    @GET("/api/users/{userId}/reviews")
    suspend fun getUserReviews(
        @Path("userId") userId: String
    ): Response<ReviewsResponse>

    @POST("/api/reviews/")
    suspend fun createReview(
        @Body request: CreateReviewRequest
    ): Response<Unit>
}
