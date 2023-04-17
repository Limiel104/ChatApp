package com.example.chatapp.domain.use_case

import com.example.chatapp.domain.model.User
import com.example.chatapp.domain.repository.UserStorageRepository
import com.example.chatapp.util.Resource
import kotlinx.coroutines.flow.Flow

class GetUserUseCase(
    private val userStorageRepository: UserStorageRepository
) {
    operator fun invoke(userUID: String): Flow<Resource<List<User>>> {
        return userStorageRepository.getUser(userUID)
    }
}