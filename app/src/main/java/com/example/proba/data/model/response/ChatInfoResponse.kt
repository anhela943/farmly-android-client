package com.example.proba.data.model.response

import com.google.gson.annotations.SerializedName

data class ChatInfoResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("user")
    val user: ChatUserInfo,
    @SerializedName("product")
    val product: ChatProductInfo?,
    @SerializedName("reviewAllowed")
    val reviewAllowed: Boolean?
)

data class ChatUserInfo(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("overallRating")
    val overallRating: Double?,
    @SerializedName("imageUrl")
    val imageUrl: String?
)

data class ChatProductInfo(
    @SerializedName("id")
    val id: String,
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("price")
    val price: Double,
    @SerializedName("name")
    val name: String
)
