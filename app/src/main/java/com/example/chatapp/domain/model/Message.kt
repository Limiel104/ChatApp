package com.example.chatapp.domain.model

import java.util.Date

data class Message(
    val text: String = "",
    val senderUID: String = "",
    val receiverUID: String = "",
    val date: Date = Date(),
    val messageId: String = ""
)