package com.example.chatapp.domain.repository

import com.example.chatapp.util.Resource
import com.google.firebase.auth.FirebaseUser

interface AuthenticationRepository {
    val currentUser: FirebaseUser?

    suspend fun login(email: String, password: String): Resource<FirebaseUser>

    suspend fun signup(email: String, password: String, firstName: String, lastName: String): Resource<FirebaseUser>

    fun logout()
}