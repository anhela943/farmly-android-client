package com.example.proba.data.model.request

import com.google.gson.annotations.SerializedName

data class CreateReviewRequest(
    @SerializedName("producerId")
    val producerId: Int,
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("comment")
    val comment: String
)
