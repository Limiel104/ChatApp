package com.example.chatapp.presentation.chat

import com.example.chatapp.domain.model.Message

data class ChatState(
    val messageList: List<Message> = emptyList(),
    val currentUserUID: String = "",
    val chatParticipantUserUID: String = "",
    val messageToSend: String = "",
    val chatParticipantName: String = "",
    val isDialogActivated: Boolean = false,
    val selectedMessage: Message? = null,
    val profilePictureUrl: String = ""
)