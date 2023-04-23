package com.example.chatapp.domain.repository

import android.net.Uri
import com.example.chatapp.domain.model.User
import com.example.chatapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserStorageRepository {

    suspend fun addUser(user: User, imageUri: Uri): Resource<Boolean>

    fun getUser(userUID: String): Flow<Resource<List<User>>>

    fun getUsers(currentUserUID: String): Flow<Resource<List<User>>>

    suspend fun updateUser(user: User, imageUri: Uri): Resource<Boolean>
}