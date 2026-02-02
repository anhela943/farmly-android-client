package com.example.proba.data.model.request

import com.google.gson.annotations.SerializedName

data class CreateChatRequest(
    @SerializedName("recipientId")
    val recipientId: Int,
    @SerializedName("productId")
    val productId: Int
)
