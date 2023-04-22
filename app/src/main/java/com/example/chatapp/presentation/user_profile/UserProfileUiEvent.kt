package com.example.chatapp.presentation.user_profile

sealed class UserProfileUiEvent {
    object SaveNewProfilePicture: UserProfileUiEvent()
    object Save: UserProfileUiEvent()
}