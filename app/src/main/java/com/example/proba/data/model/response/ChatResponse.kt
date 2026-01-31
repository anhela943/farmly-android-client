package com.example.proba.data.model.response

import com.google.gson.annotations.SerializedName

data class ChatListResponse(
    @SerializedName("chats")
    val chats: List<ChatResponse>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("offset")
    val offset: Int
)

data class ChatResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("lastMessage")
    val lastMessage: String?,
    @SerializedName("createdAt")
    val createdAt: String?
)
