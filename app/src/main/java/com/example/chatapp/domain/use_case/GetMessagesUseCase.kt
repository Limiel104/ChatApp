package com.example.chatapp.domain.use_case

import com.example.chatapp.domain.model.Message
import com.example.chatapp.domain.repository.MessageStorageRepository
import com.example.chatapp.util.Resource
import kotlinx.coroutines.flow.Flow

class GetMessagesUseCase(
    private val messageStorageRepository: MessageStorageRepository
) {
    operator fun invoke(
        currentUserUID: String,
        chatParticipantUserUID: String
    ): Flow<Resource<List<Message>>> {
        return messageStorageRepository.getMessages(currentUserUID, chatParticipantUserUID)
    }
}