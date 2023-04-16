package com.example.chatapp.presentation.login

import com.example.chatapp.data.repository.FakeAuthRepository
import com.example.chatapp.data.repository.FakeMessageStorageRepository
import com.example.chatapp.data.repository.FakeUserStorageRepository
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
        val fakeAuthRepository = FakeAuthRepository()
        val fakeUserStorageRepository = FakeUserStorageRepository()
        val fakeMessageStorageRepository = FakeMessageStorageRepository()

        viewModel = LoginViewModel(
            ChatUseCases(
                loginUseCase = LoginUseCase(fakeAuthRepository),
                signupUseCase = SignupUseCase(fakeAuthRepository),
                logoutUseCase = LogoutUseCase(fakeAuthRepository),
                getCurrentUserUseCase = GetCurrentUserUseCase(fakeAuthRepository),
                addUserUseCase = AddUserUseCase(fakeUserStorageRepository),
                getUsersUseCase = GetUsersUseCase(fakeUserStorageRepository),
                filterUsersUseCase = FilterUsersUseCase(),
                addMessageUseCase = AddMessageUseCase(fakeMessageStorageRepository),
                getMessagesUseCase = GetMessagesUseCase(fakeMessageStorageRepository),
                validateEmailUseCase = ValidateEmailUseCase(),
                validateLoginPasswordUseCase = ValidateLoginPasswordUseCase(),
                validateSignupPasswordUseCase = ValidateSignupPasswordUseCase(),
                validateConfirmPasswordUseCase = ValidateConfirmPasswordUseCase(),
                validateNameUseCase = ValidateNameUseCase()
            )
        )
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