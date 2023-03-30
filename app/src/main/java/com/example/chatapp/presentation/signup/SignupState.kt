package com.example.chatapp.presentation.signup

import com.example.chatapp.util.Resource
import com.google.firebase.auth.FirebaseUser

data class SignupState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val confirmPassword: String = "",
    val confirmPasswordError: String? = null,
    val firstName: String = "",
    val firstNameError: String? = null,
    val lastName: String = "",
    val lastNameError: String? = null,
    val signupResponse: Resource<FirebaseUser>? = null
)