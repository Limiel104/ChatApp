package com.example.chatapp.presentation.user_list

sealed class UserListEvent {
    data class OnQueryChange(val value: String): UserListEvent()
}