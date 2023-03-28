package com.example.chatapp.presentation.signup

import com.example.chatapp.util.Resource
import com.google.firebase.auth.FirebaseUser

data class SignupState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val signupResponse: Resource<FirebaseUser>? = null
)