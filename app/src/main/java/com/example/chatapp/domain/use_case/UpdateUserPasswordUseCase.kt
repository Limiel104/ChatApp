package com.example.chatapp.domain.use_case

import com.example.chatapp.domain.repository.AuthRepository
import com.example.chatapp.util.Resource

class UpdateUserPasswordUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(password: String): Resource<Boolean> {
        return authRepository.updatePassword(password)
    }
}