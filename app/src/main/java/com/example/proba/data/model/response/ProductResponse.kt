package com.example.proba.data.model.response
import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("categoryId")
    val categoryId: Int,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)
