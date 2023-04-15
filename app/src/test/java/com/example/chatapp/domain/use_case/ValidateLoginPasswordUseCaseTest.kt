package com.example.chatapp.domain.use_case

import com.example.chatapp.util.Constants.emptyString
import com.example.chatapp.util.Constants.passwordCorrect
import com.example.chatapp.util.Constants.passwordEmptyError
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class ValidateLoginPasswordUseCaseTest {

    private lateinit var validateLoginPasswordUseCase: ValidateLoginPasswordUseCase

    @Before
    fun setUp() {
        validateLoginPasswordUseCase = ValidateLoginPasswordUseCase()
    }

    @Test
    fun validatePassword_isNotBlank() {
        val password = passwordCorrect
        val result = validateLoginPasswordUseCase(password)
        assertThat(result.isSuccessful).isTrue()
    }

    @Test
    fun validatePassword_isNotBlank_returnFalse() {
        val password = emptyString
        val result = validateLoginPasswordUseCase(password)
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(passwordEmptyError)
    }
}