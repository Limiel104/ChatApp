package com.example.chatapp.presentation.user_list

import com.example.chatapp.domain.model.Message
import com.example.chatapp.domain.model.User

data class UserListState(
    val userList: List<User> = emptyList(),
    val query: String = "",
    val currentUserUID: String = "",
    val profilePicture: String = "",
    val lastMessages: Map<String, Message> = emptyMap()
)