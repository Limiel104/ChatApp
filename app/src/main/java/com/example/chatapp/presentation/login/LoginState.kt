package com.example.chatapp.presentation.login

import com.example.chatapp.util.Resource
import com.google.firebase.auth.FirebaseUser

data class LoginState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val loginResponse: Resource<FirebaseUser>? = null
)