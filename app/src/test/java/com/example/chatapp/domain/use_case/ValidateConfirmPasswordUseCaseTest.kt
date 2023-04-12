package com.example.chatapp.domain.use_case

import com.example.chatapp.util.Constants.confirmPasswordCorrect
import com.example.chatapp.util.Constants.confirmPasswordError
import com.example.chatapp.util.Constants.confirmPasswordIncorrect
import com.example.chatapp.util.Constants.emptyString
import com.example.chatapp.util.Constants.passwordCorrect
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class ValidateConfirmPasswordUseCaseTest {

    private lateinit var validateConfirmPasswordUseCase: ValidateConfirmPasswordUseCase

    @Before
    fun setUp() {
        validateConfirmPasswordUseCase = ValidateConfirmPasswordUseCase()
    }

    @Test
    fun validateConfirmPassword_passwordsMatch() {
        val password = passwordCorrect
        val confirmPassword = confirmPasswordCorrect
        val result = validateConfirmPasswordUseCase(password, confirmPassword)
        assertThat(result.isSuccessful).isTrue()
    }

    @Test
    fun validateConfirmPassword_passwordsMatch_returnFalse() {
        val password = passwordCorrect
        val confirmPassword = confirmPasswordIncorrect
        val result = validateConfirmPasswordUseCase(password, confirmPassword)
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(confirmPasswordError)

        val password2 = passwordCorrect
        val confirmPassword2 = emptyString
        val result2 = validateConfirmPasswordUseCase(password2, confirmPassword2)
        assertThat(result2.isSuccessful).isFalse()
        assertThat(result2.errorMessage).isEqualTo(confirmPasswordError)
    }
}