package com.example.proba.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.proba.data.model.response.ChatInfoResponse
import com.example.proba.data.repository.ChatRepository
import com.example.proba.util.Resource
import kotlinx.coroutines.launch

class ChatInfoViewModel(
    private val chatId: String
) : ViewModel() {

    private val chatRepository = ChatRepository()

    var chatInfoState: Resource<ChatInfoResponse> by mutableStateOf(Resource.Loading)
        private set

    init {
        loadChatInfo()
    }

    fun loadChatInfo() {
        viewModelScope.launch {
            chatInfoState = Resource.Loading
            chatInfoState = chatRepository.getChatInfo(chatId)
        }
    }

    class Factory(private val chatId: String) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ChatInfoViewModel(chatId) as T
        }
    }
}
