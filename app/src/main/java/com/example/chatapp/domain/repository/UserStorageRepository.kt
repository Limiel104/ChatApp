package com.example.chatapp.domain.repository

import com.example.chatapp.domain.model.User
import com.example.chatapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserStorageRepository {

    suspend fun addUser(user: User): Resource<Boolean>

    fun getUserList(currentUserUID: String): Flow<Resource<List<User>>>
}