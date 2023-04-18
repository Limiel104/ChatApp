package com.example.chatapp.data.repository

import com.example.chatapp.domain.model.Message
import com.example.chatapp.domain.repository.MessageStorageRepository
import com.example.chatapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeMessageStorageRepository: MessageStorageRepository {

    private val messageList = mutableListOf<Message>()

    override suspend fun addMessage(message: Message): Resource<Boolean> {
        messageList.add(message)
        return Resource.Success(true)
    }

    override fun getMessages(
        currentUserUID: String,
        chatParticipantUserUID: String
    ): Flow<Resource<List<Message>>> {
        val messagesBetweenSenderAndReceiver = messageList.filter { message ->
            (message.senderUID == currentUserUID && message.receiverUID == chatParticipantUserUID) ||
                    (message.senderUID == chatParticipantUserUID && message.receiverUID == currentUserUID)
        }.sortedBy { message ->
            message.date
        }

        return flow { emit(Resource.Success(messagesBetweenSenderAndReceiver)) }
    }

    override suspend fun deleteMessage(messageId: String): Resource<Boolean> {
        val messageToDelete = messageList.filter { message ->
            message.messageId == messageId
        }[0]
        messageList.remove(messageToDelete)

        return Resource.Success(true)
    }
}