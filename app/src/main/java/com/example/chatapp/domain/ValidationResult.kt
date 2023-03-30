package com.example.chatapp.domain

data class ValidationResult(
    val isSuccessful: Boolean,
    val errorMessage: String? = null
)