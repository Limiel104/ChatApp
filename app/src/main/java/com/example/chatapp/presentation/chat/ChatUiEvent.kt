package com.example.chatapp.presentation.chat

sealed class ChatUiEvent {
    data class ShowErrorMessage(val message: String): ChatUiEvent()
    object GoBack: ChatUiEvent()
}