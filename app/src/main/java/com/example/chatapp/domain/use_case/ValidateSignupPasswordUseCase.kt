package com.example.chatapp.domain.use_case

import com.example.chatapp.domain.ValidationResult
import com.example.chatapp.util.Constants

class ValidateSignupPasswordUseCase {
    operator fun invoke(password: String): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = Constants.passwordEmptyError
            )
        }
        if (password.length < 8) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = Constants.shortPasswordError
            )
        }
        val containsAtLeastOneDigit = password.any { it.isDigit() }
        if (!containsAtLeastOneDigit) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = Constants.containsAtLeastOneDigitError
            )
        }
        val containsAtLeastOneCapitalLetter = password.any { it.isUpperCase() }
        if (!containsAtLeastOneCapitalLetter) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = Constants.containsAtLeastOneCapitalLetterError
            )
        }
        val containsAtLeastOneSpecialChar = password.any { it in Constants.specialChars }
        if (!containsAtLeastOneSpecialChar) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = Constants.containsAtLeastOneSpecialCharError
            )
        }
        return ValidationResult(
            isSuccessful = true
        )
    }
}