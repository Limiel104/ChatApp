package com.example.chatapp.presentation.chat

import com.example.chatapp.domain.model.Message

data class ChatState(
    val messageList: List<Message> = emptyList()
)