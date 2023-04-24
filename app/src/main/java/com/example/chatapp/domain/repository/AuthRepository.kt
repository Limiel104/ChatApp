package com.example.chatapp.domain.repository

import com.example.chatapp.util.Resource
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    val currentUser: FirebaseUser?

    suspend fun login(email: String, password: String): Resource<FirebaseUser>

    suspend fun signup(email: String, password: String, firstName: String, lastName: String): Resource<FirebaseUser>

    suspend fun updateEmail(email: String): Resource<Boolean>

    suspend fun updatePassword(password: String): Resource<Boolean>

    fun logout()
}