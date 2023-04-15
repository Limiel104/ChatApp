package com.example.chatapp.presentation.signup

import com.example.chatapp.data.repository.FakeAuthRepository
import com.example.chatapp.data.repository.FakeUserStorageRepository
import com.example.chatapp.domain.use_case.*
import com.example.chatapp.util.Constants.confirmPasswordCorrect
import com.example.chatapp.util.Constants.confirmPasswordIncorrect
import com.example.chatapp.util.Constants.emailCorrect
import com.example.chatapp.util.Constants.emptyString
import com.example.chatapp.util.Constants.firstNameCorrect
import com.example.chatapp.util.Constants.firstNameHasDigit
import com.example.chatapp.util.Constants.lastNameCorrect
import com.example.chatapp.util.Constants.lastNameHasSpecialChar
import com.example.chatapp.util.Constants.passwordCorrect
import com.example.chatapp.util.Constants.passwordIncorrect
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class SignupViewModelTest {

    private lateinit var viewModel: SignupViewModel

    @Before
    fun setUp() {
        val fakeAuthRepository = FakeAuthRepository()
        val fakeUserStorageRepository = FakeUserStorageRepository()

        viewModel = SignupViewModel(
            ChatUseCases(
                loginUseCase = LoginUseCase(fakeAuthRepository),
                signupUseCase = SignupUseCase(fakeAuthRepository),
                logoutUseCase = LogoutUseCase(fakeAuthRepository),
                getCurrentUserUseCase = GetCurrentUserUseCase(fakeAuthRepository),
                addUserUseCase = AddUserUseCase(fakeUserStorageRepository),
                getUsersUseCase = GetUsersUseCase(fakeUserStorageRepository),
                filterUsersUseCase = FilterUsersUseCase(),
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
        val confirmPassword = confirmPasswordCorrect
        val firstName = firstNameCorrect
        val lastName = lastNameCorrect

        val result = viewModel.isValidationSuccessful(email,password,confirmPassword,firstName,lastName)
        assertThat(result).isTrue()
    }

    @Test
    fun isValidationSuccessful_emailIncorrect() {
        val email = emptyString
        val password = passwordCorrect
        val confirmPassword = confirmPasswordCorrect
        val firstName = firstNameCorrect
        val lastName = lastNameCorrect

        val result = viewModel.isValidationSuccessful(email,password,confirmPassword,firstName,lastName)
        assertThat(result).isFalse()
    }

    @Test
    fun isValidationSuccessful_passwordIncorrect() {
        val email = emailCorrect
        val password = passwordIncorrect
        val confirmPassword = passwordIncorrect
        val firstName = firstNameCorrect
        val lastName = lastNameCorrect

        val result = viewModel.isValidationSuccessful(email,password,confirmPassword,firstName,lastName)
        assertThat(result).isFalse()
    }

    @Test
    fun isValidationSuccessful_confirmPasswordIncorrect() {
        val email = emailCorrect
        val password = passwordCorrect
        val confirmPassword = confirmPasswordIncorrect
        val firstName = firstNameCorrect
        val lastName = lastNameCorrect

        val result = viewModel.isValidationSuccessful(email,password,confirmPassword,firstName,lastName)
        assertThat(result).isFalse()
    }

    @Test
    fun isValidationSuccessful_firstNameIncorrect() {
        val email = emailCorrect
        val password = passwordCorrect
        val confirmPassword = confirmPasswordIncorrect
        val firstName = firstNameHasDigit
        val lastName = lastNameCorrect

        val result = viewModel.isValidationSuccessful(email,password,confirmPassword,firstName,lastName)
        assertThat(result).isFalse()
    }

    @Test
    fun isValidationSuccessful_lastNameIncorrect() {
        val email = emailCorrect
        val password = passwordCorrect
        val confirmPassword = confirmPasswordCorrect
        val firstName = firstNameCorrect
        val lastName = lastNameHasSpecialChar

        val result = viewModel.isValidationSuccessful(email,password,confirmPassword,firstName,lastName)
        assertThat(result).isFalse()
    }

    @Test
    fun isValidationSuccessful_allIncorrect() {
        val email = emptyString
        val password = passwordIncorrect
        val confirmPassword = confirmPasswordIncorrect
        val firstName = firstNameHasDigit
        val lastName = lastNameHasSpecialChar

        val result = viewModel.isValidationSuccessful(email,password,confirmPassword,firstName,lastName)
        assertThat(result).isFalse()
    }
}