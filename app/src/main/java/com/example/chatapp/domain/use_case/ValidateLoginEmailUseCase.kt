package com.example.chatapp.domain.use_case

import com.example.chatapp.domain.ValidationResult
import com.example.chatapp.util.Constants

class ValidateLoginEmailUseCase {
    operator fun invoke(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = Constants.emailEmptyError
            )
        }
        return ValidationResult(
            isSuccessful = true
        )
    }
}