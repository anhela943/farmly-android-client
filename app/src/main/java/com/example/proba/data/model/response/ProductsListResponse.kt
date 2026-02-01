package com.example.proba.data.model.response

import com.google.gson.annotations.SerializedName

data class ProductsListResponse(
    @SerializedName("products")
    val products: List<ProductListItem>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("offset")
    val offset: Int
)

data class ProductListItem(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("producer")
    val producer: ProducerInfo
)

data class ProducerInfo(
    @SerializedName("id")
    val id: String,
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("city")
    val city: String,
    @SerializedName("overallReview")
    val overallReview: OverallReview
)

data class OverallReview(
    @SerializedName("numberOfReviews")
    val numberOfReviews: Int?,
    @SerializedName("overallReview")
    val overallReview: Double?
)
