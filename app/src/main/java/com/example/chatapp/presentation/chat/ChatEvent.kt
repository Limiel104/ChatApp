package com.example.chatapp.presentation.chat

import com.example.chatapp.domain.model.Message

sealed class ChatEvent {
    data class EnteredMessage(val value: String): ChatEvent()
    object SendMessage: ChatEvent()
    object GoBack: ChatEvent()
    data class ClickedMessage(val value: Message): ChatEvent()
    object DismissDialog: ChatEvent()
    object DeleteMessage: ChatEvent()
    object RestoreMessage: ChatEvent()
}