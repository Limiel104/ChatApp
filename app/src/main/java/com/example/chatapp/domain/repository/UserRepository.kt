package com.example.chatapp.domain.repository

import com.example.chatapp.domain.model.User
import com.example.chatapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun addUser(
        userUID: String,
        firstName: String,
        lastName: String,
        avatarURL: String
    ): Resource<Boolean>

    fun getUserList(currentUserUID: String): Flow<Resource<List<User>>>
}