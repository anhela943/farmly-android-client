package com.example.proba.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proba.data.model.response.ChatListResponse
import com.example.proba.data.repository.ChatRepository
import com.example.proba.util.Resource
import kotlinx.coroutines.launch

class ChatsViewModel : ViewModel() {

    private val chatRepository = ChatRepository()

    var chatsState: Resource<ChatListResponse> by mutableStateOf(Resource.Loading)
        private set

    init {
        loadChats()
    }

    fun loadChats() {
        viewModelScope.launch {
            chatsState = Resource.Loading
            chatsState = chatRepository.getChats()
        }
    }
}
