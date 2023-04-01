package com.example.chatapp.presentation.signup

sealed class SignupUiEvent {
    object Signup: SignupUiEvent()
    data class ShowErrorMessage(val message: String): SignupUiEvent()
}