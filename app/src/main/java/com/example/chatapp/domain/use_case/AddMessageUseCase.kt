package com.example.chatapp.domain.use_case

import com.example.chatapp.domain.model.Message
import com.example.chatapp.domain.repository.MessageStorageRepository
import com.example.chatapp.util.Resource

class AddMessageUseCase(
    private val messageStorageRepository: MessageStorageRepository
) {
    suspend operator fun invoke(message: Message) : Resource<Boolean> {
        return messageStorageRepository.addMessage(message)
    }
}