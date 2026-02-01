package com.example.proba.data.model.response

import com.google.gson.annotations.SerializedName

data class MessagesListResponse(
    @SerializedName("messages") val messages: List<MessageResponse>,
    @SerializedName("total") val total: Int,
    @SerializedName("limit") val limit: Int,
    @SerializedName("offset") val offset: Int
)

data class MessageResponse(
    @SerializedName("id") val id: String,
    @SerializedName("sentAt") val sentAt: String,
    @SerializedName("content") val content: String,
    @SerializedName("chatId") val chatId: String,
    @SerializedName("senderId") val senderId: String,
    @SerializedName("sender") val sender: MessageSenderResponse
)

data class MessageSenderResponse(
    @SerializedName("id") val id: String,
    @SerializedName("fullName") val fullName: String
)
