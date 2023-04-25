package com.example.chatapp.presentation.user_profile

sealed class UserProfileUiEvent {
    data class ShowErrorMessage(val message: String): UserProfileUiEvent()
    object Save: UserProfileUiEvent()
    object GoBack: UserProfileUiEvent()
}