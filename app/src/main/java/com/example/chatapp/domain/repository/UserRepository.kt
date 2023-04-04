package com.example.chatapp.domain.repository

import com.example.chatapp.domain.model.User
import com.example.chatapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserList(): Flow<Resource<List<User>>>
}