package com.example.chatapp.domain.use_case

import com.example.chatapp.util.Constants.digitsInNameError
import com.example.chatapp.util.Constants.emptyString
import com.example.chatapp.util.Constants.fieldEmptyError
import com.example.chatapp.util.Constants.firstNameCorrect
import com.example.chatapp.util.Constants.firstNameHasDigit
import com.example.chatapp.util.Constants.firstNameHasSpecialChar
import com.example.chatapp.util.Constants.specialCharsInNameError
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class ValidateNameUseCaseTest {

    private lateinit var validateNameUseCase: ValidateNameUseCase

    @Before
    fun setUp() {
        validateNameUseCase = ValidateNameUseCase()
    }

    @Test
    fun validateName_isCorrect() {
        val name = firstNameCorrect
        val result = validateNameUseCase(name)
        assertThat(result.isSuccessful).isTrue()
    }

    @Test
    fun validateName_isNotBlank_returnFalse() {
        val name = emptyString
        val result = validateNameUseCase(name)
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(fieldEmptyError)
    }

    @Test
    fun validateName_doesNotContainDigit_returnFalse() {
        val name = firstNameHasDigit
        val result = validateNameUseCase(name)
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(digitsInNameError)
    }

    @Test
    fun validateName_doesNotContainSpecialChar_returnFalse() {
        val name = firstNameHasSpecialChar
        val result = validateNameUseCase(name)
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(specialCharsInNameError)
    }
}