package com.example.chatapp.domain.use_case

import com.example.chatapp.domain.repository.AuthRepository
import com.example.chatapp.util.Resource
import com.google.firebase.auth.FirebaseUser

class SignupUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String, firstName: String, lastName: String): Resource<FirebaseUser> {
        return authRepository.signup(
            email,
            password,
            firstName,
            lastName
        )
    }
}