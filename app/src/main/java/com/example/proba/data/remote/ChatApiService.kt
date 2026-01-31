package com.example.proba.data.remote

import com.example.proba.data.model.response.ChatListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ChatApiService {

    @GET("/api/chats")
    suspend fun getChats(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): Response<ChatListResponse>
}
