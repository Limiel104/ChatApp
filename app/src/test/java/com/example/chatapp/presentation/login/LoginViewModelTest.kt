package com.example.chatapp.presentation.login

import com.example.chatapp.data.repository.FakeAuthRepository
import com.example.chatapp.util.Constants.emailEmptyError
import com.example.chatapp.util.Constants.passwordEmptyError
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        viewModel = LoginViewModel(FakeAuthRepository())
    }

    @Test
    fun validateEmail_isNotBlank() {
        val email = "test@test.com"
        val result = viewModel.validateEmail(email)
        assertThat(result.isSuccessful).isTrue()
    }

    @Test
    fun validateEmail_isNotBlank_returnFalse() {
        val email = ""
        val result = viewModel.validateEmail(email)
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(emailEmptyError)
    }

    @Test
    fun validatePassword_isNotBlank() {
        val password = "Qwerty1+"
        val result = viewModel.validatePassword(password)
        assertThat(result.isSuccessful).isTrue()
    }

    @Test
    fun validatePassword_isNotBlank_returnFalse() {
        val password = ""
        val result = viewModel.validatePassword(password)
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(passwordEmptyError)
    }

    @Test
    fun isValidationSuccessful_allCorrect() {
        val email = "test@test.com"
        val password = "Qwerty1+"
        val result = viewModel.isValidationSuccessful(email,password)
        assertThat(result).isTrue()
    }

    @Test
    fun isValidationSuccessful_emailIncorrect() {
        val email = ""
        val password = "Qwerty1+"
        val result = viewModel.isValidationSuccessful(email,password)
        assertThat(result).isFalse()
    }

    @Test
    fun isValidationSuccessful_passwordIncorrect() {
        val email = "test@test.com"
        val password = ""
        val result = viewModel.isValidationSuccessful(email,password)
        assertThat(result).isFalse()
    }

    @Test
    fun isValidationSuccessful_allIncorrect() {
        val email = ""
        val password = ""
        val result = viewModel.isValidationSuccessful(email,password)
        assertThat(result).isFalse()
    }
}