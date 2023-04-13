package com.example.chatapp.domain.use_case

import com.example.chatapp.domain.repository.UserStorageRepository
import com.example.chatapp.util.Resource

class AddUserUseCase(
    private val userStorageRepository: UserStorageRepository
) {
    suspend operator fun invoke(
        userUID: String,
        firstName: String,
        lastName: String,
        avatarURL: String
    ): Resource<Boolean> {
        return userStorageRepository.addUser(
            userUID,
            firstName,
            lastName,
            avatarURL
        )
    }
}