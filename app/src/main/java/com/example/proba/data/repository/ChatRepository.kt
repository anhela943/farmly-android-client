package com.example.proba.data.repository

import com.example.proba.data.model.request.CreateChatRequest
import com.example.proba.data.model.request.SendMessageRequest
import com.example.proba.data.model.response.ChatInfoResponse
import com.example.proba.data.model.response.ChatListResponse
import com.example.proba.data.model.response.CreateChatResponse
import com.example.proba.data.model.response.ErrorResponse
import com.example.proba.data.model.response.MessageResponse
import com.example.proba.data.model.response.MessagesListResponse
import com.example.proba.data.remote.ApiClient
import com.example.proba.util.Resource
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChatRepository {

    private val chatApi = ApiClient.chatApiService
    private val gson = Gson()

    suspend fun createChat(recipientId: Int, productId: Int): Resource<CreateChatResponse> = withContext(Dispatchers.IO) {
        try {
            val request = CreateChatRequest(recipientId = recipientId, productId = productId)
            val response = chatApi.createChat(request)

            if (response.isSuccessful) {
                response.body()?.let { chatResponse ->
                    return@withContext Resource.Success(chatResponse)
                }
                return@withContext Resource.Error("Empty response body")
            }

            val errorBody = response.errorBody()?.string()
            val errorResponse = parseError(errorBody)
            Resource.Error(
                message = errorResponse?.message ?: "Failed to create chat"
            )
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error occurred")
        }
    }

    suspend fun getChats(limit: Int = 20, offset: Int = 0): Resource<ChatListResponse> = withContext(Dispatchers.IO) {
        try {
            val response = chatApi.getChats(limit = limit, offset = offset)

            if (response.isSuccessful) {
                response.body()?.let { chatList ->
                    return@withContext Resource.Success(chatList)
                }
                return@withContext Resource.Error("Empty response body")
            }

            val errorBody = response.errorBody()?.string()
            val errorResponse = parseError(errorBody)
            Resource.Error(
                message = errorResponse?.message ?: "Failed to load chats"
            )
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error occurred")
        }
    }

    suspend fun getChatInfo(chatId: String): Resource<ChatInfoResponse> = withContext(Dispatchers.IO) {
        try {
            val response = chatApi.getChatInfo(chatId)

            if (response.isSuccessful) {
                response.body()?.let { chatInfo ->
                    return@withContext Resource.Success(chatInfo)
                }
                return@withContext Resource.Error("Empty response body")
            }

            val errorBody = response.errorBody()?.string()
            val errorResponse = parseError(errorBody)
            Resource.Error(
                message = errorResponse?.message ?: "Failed to load chat info"
            )
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error occurred")
        }
    }

    suspend fun getMessages(chatId: String, limit: Int = 50, offset: Int = 0): Resource<MessagesListResponse> = withContext(Dispatchers.IO) {
        try {
            val response = chatApi.getMessages(chatId, limit, offset)

            if (response.isSuccessful) {
                response.body()?.let { messagesResponse ->
                    return@withContext Resource.Success(messagesResponse)
                }
                return@withContext Resource.Error("Empty response body")
            }

            val errorBody = response.errorBody()?.string()
            val errorResponse = parseError(errorBody)
            Resource.Error(
                message = errorResponse?.message ?: "Failed to load messages"
            )
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error occurred")
        }
    }

    suspend fun sendMessage(chatId: String, content: String): Resource<MessageResponse> = withContext(Dispatchers.IO) {
        try {
            val request = SendMessageRequest(content = content)
            val response = chatApi.sendMessage(chatId, request)

            if (response.isSuccessful) {
                response.body()?.let { message ->
                    return@withContext Resource.Success(message)
                }
                return@withContext Resource.Error("Empty response body")
            }

            val errorBody = response.errorBody()?.string()
            val errorResponse = parseError(errorBody)
            Resource.Error(
                message = errorResponse?.message ?: "Failed to send message"
            )
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network error occurred")
        }
    }

    private fun parseError(errorBody: String?): ErrorResponse? {
        return try {
            errorBody?.let { gson.fromJson(it, ErrorResponse::class.java) }
        } catch (e: Exception) {
            null
        }
    }
}
