package com.example.chatapp.presentation.login

import com.example.chatapp.data.repository.FakeAuthRepository
import com.example.chatapp.util.Constants.emailCorrect
import com.example.chatapp.util.Constants.emailEmptyError
import com.example.chatapp.util.Constants.emptyString
import com.example.chatapp.util.Constants.passwordCorrect
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
        val email = emailCorrect
        val result = viewModel.validateEmail(email)
        assertThat(result.isSuccessful).isTrue()
    }

    @Test
    fun validateEmail_isNotBlank_returnFalse() {
        val email = emptyString
        val result = viewModel.validateEmail(email)
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(emailEmptyError)
    }

    @Test
    fun validatePassword_isNotBlank() {
        val password = passwordCorrect
        val result = viewModel.validatePassword(password)
        assertThat(result.isSuccessful).isTrue()
    }

    @Test
    fun validatePassword_isNotBlank_returnFalse() {
        val password = emptyString
        val result = viewModel.validatePassword(password)
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(passwordEmptyError)
    }

    @Test
    fun isValidationSuccessful_allCorrect() {
        val email = emailCorrect
        val password = passwordCorrect
        val result = viewModel.isValidationSuccessful(email,password)
        assertThat(result).isTrue()
    }

    @Test
    fun isValidationSuccessful_emailIncorrect() {
        val email = emptyString
        val password = passwordCorrect
        val result = viewModel.isValidationSuccessful(email,password)
        assertThat(result).isFalse()
    }

    @Test
    fun isValidationSuccessful_passwordIncorrect() {
        val email = emailCorrect
        val password = emptyString
        val result = viewModel.isValidationSuccessful(email,password)
        assertThat(result).isFalse()
    }

    @Test
    fun isValidationSuccessful_allIncorrect() {
        val email = emptyString
        val password = emptyString
        val result = viewModel.isValidationSuccessful(email,password)
        assertThat(result).isFalse()
    }
}