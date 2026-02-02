package com.example.proba.data.model.response

import com.google.gson.annotations.SerializedName

data class ProductDetailResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("description")
    val description: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("producer")
    val producer: ProducerInfo
)
