package com.example.chatapp.presentation.login

import com.example.chatapp.util.Resource
import com.google.firebase.auth.FirebaseUser

data class LoginState(
    val email: String = "",
    val password: String = "",
    val loginResponse: Resource<FirebaseUser>? = null
)