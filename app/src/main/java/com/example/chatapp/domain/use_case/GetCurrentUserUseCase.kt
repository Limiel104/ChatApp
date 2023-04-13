package com.example.chatapp.domain.use_case

import com.example.chatapp.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser

class GetCurrentUserUseCase(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): FirebaseUser? {
        return authRepository.currentUser
    }
}