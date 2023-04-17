package com.example.chatapp.presentation.chat

sealed class ChatEvent {
    data class EnteredMessage(val value: String): ChatEvent()
    object SendMessage: ChatEvent()
    object GoBack: ChatEvent()
}