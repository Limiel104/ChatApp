package com.example.chatapp.domain.repository

import com.example.chatapp.domain.model.Message
import com.example.chatapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface MessageStorageRepository {

    suspend fun addMessage(message: Message): Resource<Boolean>

    fun getMessages(senderUID: String, receiverUID: String): Flow<Resource<List<Message>>>
}