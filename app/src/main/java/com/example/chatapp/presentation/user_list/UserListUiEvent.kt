package com.example.chatapp.presentation.user_list

sealed class UserListUiEvent {
    object Logout: UserListUiEvent()
}