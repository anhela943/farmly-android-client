package com.example.proba.data.model.response

import com.google.gson.annotations.SerializedName

data class ReviewsResponse(
    @SerializedName("reviews")
    val reviews: List<Review>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("offset")
    val offset: Int
)

data class Review(
    @SerializedName("id")
    val id: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("user")
    val user: ReviewUser
)

data class ReviewUser(
    @SerializedName("id")
    val id: String,
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("rating")
    val rating: Int
)
