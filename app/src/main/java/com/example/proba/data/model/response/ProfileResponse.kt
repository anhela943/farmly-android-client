package com.example.proba.data.model.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("review")
    val review: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("phoneNumber")
    val phoneNumber: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("overallReview")
    val overallReview: String?,
    @SerializedName("numberOfReviews")
    val numberOfReviews: String?
)
