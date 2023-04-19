package com.example.chatapp.domain.use_case

import com.example.chatapp.domain.repository.MessageStorageRepository
import com.example.chatapp.util.Resource

class DeleteMessageUseCase(
    private val messageStorageRepository: MessageStorageRepository
) {
    suspend operator fun invoke(messageId: String): Resource<Boolean> {
        return messageStorageRepository.deleteMessage(messageId)
    }
}