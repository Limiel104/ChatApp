package com.example.chatapp.domain.use_case

import com.example.chatapp.domain.repository.AuthRepository
import com.example.chatapp.util.Resource

class UpdateUserEmailUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String): Resource<Boolean> {
       return authRepository.updateEmail(email)
    }
}