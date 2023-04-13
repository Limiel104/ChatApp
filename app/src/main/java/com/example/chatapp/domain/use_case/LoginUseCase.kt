package com.example.chatapp.domain.use_case

import com.example.chatapp.domain.repository.AuthRepository
import com.example.chatapp.util.Resource
import com.google.firebase.auth.FirebaseUser

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Resource<FirebaseUser> {
        return authRepository.login(email, password)
    }
}