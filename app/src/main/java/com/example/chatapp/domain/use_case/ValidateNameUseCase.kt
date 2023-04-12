package com.example.chatapp.domain.use_case

import com.example.chatapp.domain.ValidationResult
import com.example.chatapp.util.Constants

class ValidateNameUseCase {
    operator fun invoke(name: String): ValidationResult {
        if (name.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = Constants.fieldEmptyError
            )
        }
        val containsDigit = name.any { it.isDigit() }
        if (containsDigit) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = Constants.digitsInNameError
            )
        }
        val containsSpecialChar = name.any { it in Constants.specialChars }
        if (containsSpecialChar) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = Constants.specialCharsInNameError
            )
        }
        return ValidationResult(
            isSuccessful = true
        )
    }
}