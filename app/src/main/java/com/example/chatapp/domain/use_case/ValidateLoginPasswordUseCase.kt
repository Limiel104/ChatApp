package com.example.chatapp.domain.use_case

import com.example.chatapp.domain.ValidationResult
import com.example.chatapp.util.Constants

class ValidateLoginPasswordUseCase {
    operator fun invoke(password: String): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = Constants.passwordEmptyError
            )
        }
        return ValidationResult(
            isSuccessful = true
        )
    }
}