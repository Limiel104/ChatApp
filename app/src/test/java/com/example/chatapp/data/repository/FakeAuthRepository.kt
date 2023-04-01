package com.example.chatapp.data.repository

import com.example.chatapp.domain.repository.AuthRepository
import com.example.chatapp.util.Resource
import com.google.firebase.auth.FirebaseUser

class FakeAuthRepository: AuthRepository {
    override val currentUser: FirebaseUser
        get() = TODO("Not yet implemented")

    override suspend fun login(email: String, password: String): Resource<FirebaseUser> {
        TODO("Not yet implemented")
    }

    override suspend fun signup(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): Resource<FirebaseUser> {
        TODO("Not yet implemented")
    }

    override fun logout() {
        TODO("Not yet implemented")
    }
}