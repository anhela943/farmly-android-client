package com.example.proba.data.remote

import com.example.proba.data.model.request.CreateChatRequest
import com.example.proba.data.model.request.SendMessageRequest
import com.example.proba.data.model.response.ChatInfoResponse
import com.example.proba.data.model.response.ChatListResponse
import com.example.proba.data.model.response.CreateChatResponse
import com.example.proba.data.model.response.MessageResponse
import com.example.proba.data.model.response.MessagesListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ChatApiService {

    @POST("/api/chats")
    suspend fun createChat(
        @Body request: CreateChatRequest
    ): Response<CreateChatResponse>

    @GET("/api/chats")
    suspend fun getChats(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): Response<ChatListResponse>

    @GET("/api/chats/{chatId}/info")
    suspend fun getChatInfo(
        @Path("chatId") chatId: String
    ): Response<ChatInfoResponse>

    @GET("/api/chats/{chatId}/messages")
    suspend fun getMessages(
        @Path("chatId") chatId: String,
        @Query("limit") limit: Int = 50,
        @Query("offset") offset: Int = 0
    ): Response<MessagesListResponse>

    @POST("/api/chats/{chatId}/messages")
    suspend fun sendMessage(
        @Path("chatId") chatId: String,
        @Body request: SendMessageRequest
    ): Response<MessageResponse>
}
