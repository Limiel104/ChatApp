package com.example.chatapp.domain.use_case

import com.example.chatapp.domain.ValidationResult
import com.example.chatapp.util.Constants

class ValidateConfirmPasswordUseCase {
    operator fun invoke(password: String, confirmPassword: String): ValidationResult {
        if (password != confirmPassword) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = Constants.confirmPasswordError
            )
        }
        return ValidationResult(
            isSuccessful = true
        )
    }
}