package com.example.proba.viewmodel

import android.util.Base64
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.proba.data.model.response.MessageResponse
import com.example.proba.data.remote.SocketManager
import com.example.proba.data.repository.ChatRepository
import com.example.proba.util.Resource
import kotlinx.coroutines.launch
import org.json.JSONObject

class ChatMessagesViewModel(
    private val chatId: String,
    private val token: String
) : ViewModel() {

    private val chatRepository = ChatRepository()
    private val socketManager = SocketManager(token)

    private val _messages = mutableStateListOf<MessageResponse>()
    val messages: List<MessageResponse> get() = _messages

    var isLoading by mutableStateOf(false)
        private set

    var isLoadingMore by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    private var total = 0
    private var offset = 0
    private val limit = 20

    val currentUserId: String? = getUserIdFromToken(token)

    val hasMore: Boolean get() = offset < total

    init {
        loadMessages()
        connectSocket()
    }

    private fun loadMessages() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            when (val result = chatRepository.getMessages(chatId, limit, 0)) {
                is Resource.Success -> {
                    _messages.clear()
                    _messages.addAll(result.data.messages)
                    total = result.data.total
                    offset = result.data.messages.size
                }
                is Resource.Error -> {
                    errorMessage = result.message
                }
                is Resource.Loading -> {}
            }
            isLoading = false
        }
    }

    fun loadMoreMessages() {
        if (isLoadingMore || !hasMore) return
        viewModelScope.launch {
            isLoadingMore = true
            when (val result = chatRepository.getMessages(chatId, limit, offset)) {
                is Resource.Success -> {
                    val newMessages = result.data.messages.filter { new ->
                        _messages.none { it.id == new.id }
                    }
                    _messages.addAll(0, newMessages)
                    offset += result.data.messages.size
                    total = result.data.total
                }
                is Resource.Error -> { /* silently fail for load more */ }
                is Resource.Loading -> {}
            }
            isLoadingMore = false
        }
    }

    private fun connectSocket() {
        socketManager.connect(chatId)
        viewModelScope.launch {
            socketManager.incomingMessages.collect { message ->
                if (_messages.none { it.id == message.id }) {
                    _messages.add(message)
                }
            }
        }
    }

    fun sendMessage(content: String) {
        val trimmed = content.trim()
        if (trimmed.isEmpty()) return
        socketManager.sendMessage(chatId, trimmed)
    }

    fun retry() {
        loadMessages()
    }

    override fun onCleared() {
        super.onCleared()
        socketManager.disconnect(chatId)
    }

    private fun getUserIdFromToken(token: String): String? {
        return try {
            val parts = token.split(".")
            if (parts.size != 3) return null
            val payload = String(
                Base64.decode(parts[1], Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP)
            )
            val json = JSONObject(payload)
            when {
                json.has("id") -> json.get("id").toString()
                json.has("sub") -> json.get("sub").toString()
                json.has("userId") -> json.get("userId").toString()
                else -> null
            }
        } catch (e: Exception) {
            null
        }
    }

    class Factory(
        private val chatId: String,
        private val token: String
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ChatMessagesViewModel(chatId, token) as T
        }
    }
}
