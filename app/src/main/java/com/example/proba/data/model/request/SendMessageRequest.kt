package com.example.proba.data.model.request

import com.google.gson.annotations.SerializedName

data class SendMessageRequest(
    @SerializedName("content")
    val content: String
)
