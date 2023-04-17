package com.example.chatapp.domain.repository

import com.example.chatapp.domain.model.User
import com.example.chatapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserStorageRepository {

    suspend fun addUser(user: User): Resource<Boolean>

    fun getUser(userUID: String): Flow<Resource<List<User>>>

    fun getUsers(currentUserUID: String): Flow<Resource<List<User>>>
}