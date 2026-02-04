package com.example.proba.data.model.response

import com.google.gson.annotations.SerializedName

data class FavoritesListResponse(
    @SerializedName("products")
    val products: List<FavoriteProductItem>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("offset")
    val offset: Int
)

data class FavoriteProductItem(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("userId")
    val userId: String,
    @SerializedName("categoryId")
    val categoryId: String,
    @SerializedName("seller")
    val seller: SellerInfo
)

data class SellerInfo(
    @SerializedName("id")
    val id: String,
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("city")
    val city: String,
    @SerializedName("numberOfReviews")
    val numberOfReviews: String,
    @SerializedName("overallReview")
    val overallReview: Double
)
