package com.example.chatapp.presentation.login

sealed class LoginUiEvent {
    object Login: LoginUiEvent()
    data class ShowErrorMessage(val message: String): LoginUiEvent()
}