package com.example.proba.data.remote

import android.util.Log
import com.example.proba.data.model.response.MessageResponse
import com.example.proba.data.model.response.MessageSenderResponse
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import org.json.JSONObject
import java.net.URI

class SocketManager(private val token: String) {

    companion object {
        private const val TAG = "SocketManager"
        private const val BASE_URL = "https://farmly-rest-api.onrender.com/"
    }

    private var socket: Socket? = null

    private val _incomingMessages = MutableSharedFlow<MessageResponse>(extraBufferCapacity = 64)
    val incomingMessages: SharedFlow<MessageResponse> = _incomingMessages

    fun connect(chatId: String) {
        if (token.isBlank()) {
            Log.e(TAG, "Cannot connect: token is empty")
            return
        }

        val opts = IO.Options().apply {
            auth = mapOf("token" to token)
        }

        socket = IO.socket(URI.create(BASE_URL), opts).apply {
            on(Socket.EVENT_CONNECT) {
                Log.d(TAG, "Socket connected")
                emit("join-chat", chatId)
            }

            on(Socket.EVENT_DISCONNECT) {
                Log.d(TAG, "Socket disconnected")
            }

            on(Socket.EVENT_CONNECT_ERROR) { args ->
                Log.e(TAG, "Socket connection error: ${args.firstOrNull()}")
            }

            on("joined-chat") { args ->
                Log.d(TAG, "Joined chat: ${args.firstOrNull()}")
            }

            on("new-message") { args ->
                try {
                    val data = args[0] as JSONObject
                    val senderObj = data.getJSONObject("sender")
                    val message = MessageResponse(
                        id = data.getString("id"),
                        sentAt = data.getString("sentAt"),
                        content = data.getString("content"),
                        chatId = data.getString("chatId"),
                        senderId = data.getString("senderId"),
                        sender = MessageSenderResponse(
                            id = senderObj.getString("id"),
                            fullName = senderObj.getString("fullName")
                        )
                    )
                    _incomingMessages.tryEmit(message)
                } catch (e: Exception) {
                    Log.e(TAG, "Error parsing message: ${e.message}")
                }
            }

            on("error") { args ->
                Log.e(TAG, "Socket error: ${args.firstOrNull()}")
            }

            connect()
        }
    }

    fun sendMessage(chatId: String, content: String) {
        val data = JSONObject().apply {
            put("chatId", chatId)
            put("content", content)
        }
        socket?.emit("send-message", data)
    }

    fun disconnect(chatId: String) {
        socket?.emit("leave-chat", chatId)
        socket?.disconnect()
        socket?.off()
        socket = null
    }
}
