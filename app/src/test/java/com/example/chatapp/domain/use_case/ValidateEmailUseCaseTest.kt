package com.example.chatapp.domain.use_case

import com.example.chatapp.util.Constants.emailCorrect
import com.example.chatapp.util.Constants.emailEmptyError
import com.example.chatapp.util.Constants.emptyString
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class ValidateEmailUseCaseTest {

    private lateinit var validateEmailUseCase: ValidateEmailUseCase

    @Before
    fun setUp() {
        validateEmailUseCase = ValidateEmailUseCase()
    }

    @Test
    fun validateEmail_isCorrect() {
        val email = emailCorrect
        val result = validateEmailUseCase(email)
        assertThat(result.isSuccessful).isTrue()
    }

    @Test
    fun validateEmail_isNotBlank_returnFalse() {
        val email = emptyString
        val result = validateEmailUseCase(email)
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(emailEmptyError)
    }
}