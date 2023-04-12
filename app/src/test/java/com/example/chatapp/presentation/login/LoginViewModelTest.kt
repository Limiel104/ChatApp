package com.example.chatapp.presentation.login

import com.example.chatapp.data.repository.FakeAuthRepository
import com.example.chatapp.domain.use_case.*
import com.example.chatapp.util.Constants.emailCorrect
import com.example.chatapp.util.Constants.emptyString
import com.example.chatapp.util.Constants.passwordCorrect
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        viewModel = LoginViewModel(FakeAuthRepository(), ChatUseCases(
            ValidateEmailUseCase(),
            ValidateLoginPasswordUseCase(),
            ValidateSignupPasswordUseCase(),
            ValidateConfirmPasswordUseCase(),
            ValidateNameUseCase()
        ))
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