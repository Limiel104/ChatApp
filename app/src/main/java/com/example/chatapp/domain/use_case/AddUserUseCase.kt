package com.example.chatapp.domain.use_case

import com.example.chatapp.domain.model.User
import com.example.chatapp.domain.repository.UserStorageRepository
import com.example.chatapp.util.Resource

class AddUserUseCase(
    private val userStorageRepository: UserStorageRepository
) {
    suspend operator fun invoke(user: User): Resource<Boolean> {
        return userStorageRepository.addUser(user)
    }
}