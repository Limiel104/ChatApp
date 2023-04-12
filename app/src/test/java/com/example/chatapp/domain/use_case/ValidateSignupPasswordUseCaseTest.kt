package com.example.chatapp.domain.use_case

import com.example.chatapp.util.Constants.containsAtLeastOneCapitalLetterError
import com.example.chatapp.util.Constants.containsAtLeastOneDigitError
import com.example.chatapp.util.Constants.containsAtLeastOneSpecialCharError
import com.example.chatapp.util.Constants.emptyString
import com.example.chatapp.util.Constants.passwordCorrect
import com.example.chatapp.util.Constants.passwordEmptyError
import com.example.chatapp.util.Constants.passwordTooShort
import com.example.chatapp.util.Constants.passwordWithoutCapitalLetter
import com.example.chatapp.util.Constants.passwordWithoutDigit
import com.example.chatapp.util.Constants.passwordWithoutSpecialChar
import com.example.chatapp.util.Constants.shortPasswordError
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class ValidateSignupPasswordUseCaseTest {

    private lateinit var validateSignupPasswordUseCase: ValidateSignupPasswordUseCase

    @Before
    fun setUp() {
        validateSignupPasswordUseCase = ValidateSignupPasswordUseCase()
    }

    @Test
    fun validatePassword_isCorrect() {
        val password = passwordCorrect
        val result = validateSignupPasswordUseCase(password)
        assertThat(result.isSuccessful).isTrue()
    }

    @Test
    fun validatePassword_isNotBlank_returnFalse() {
        val password = emptyString
        val result = validateSignupPasswordUseCase(password)
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(passwordEmptyError)
    }

    @Test
    fun validatePassword_isOfCorrectLength_returnFalse() {
        val password = passwordTooShort
        val result = validateSignupPasswordUseCase(password)
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(shortPasswordError)
    }

    @Test
    fun validatePassword_containsAtLeastOneDigit_returnFalse() {
        val password = passwordWithoutDigit
        val result = validateSignupPasswordUseCase(password)
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(containsAtLeastOneDigitError)
    }

    @Test
    fun validatePassword_containsAtLeastOneCapitalLetter_returnFalse() {
        val password = passwordWithoutCapitalLetter
        val result = validateSignupPasswordUseCase(password)
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(containsAtLeastOneCapitalLetterError)
    }

    @Test
    fun validatePassword_containsAtLeastOneSpecialChar_returnFalse() {
        val password = passwordWithoutSpecialChar
        val result = validateSignupPasswordUseCase(password)
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(containsAtLeastOneSpecialCharError)
    }
}