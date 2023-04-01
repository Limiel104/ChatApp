package com.example.chatapp.presentation.signup

import com.example.chatapp.data.repository.FakeAuthRepository
import com.example.chatapp.util.Constants.confirmPasswordError
import com.example.chatapp.util.Constants.containsAtLeastOneCapitalLetterError
import com.example.chatapp.util.Constants.containsAtLeastOneDigitError
import com.example.chatapp.util.Constants.containsAtLeastOneSpecialCharError
import com.example.chatapp.util.Constants.digitsInNameError
import com.example.chatapp.util.Constants.emailEmptyError
import com.example.chatapp.util.Constants.fieldEmptyError
import com.example.chatapp.util.Constants.passwordEmptyError
import com.example.chatapp.util.Constants.shortPasswordError
import com.example.chatapp.util.Constants.specialCharsInNameError
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class SignupViewModelTest {

    private lateinit var viewModel: SignupViewModel

    @Before
    fun setUp() {
        viewModel = SignupViewModel(FakeAuthRepository())
    }

    @Test
    fun validateEmail_isCorrect() {
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
    fun validatePassword_isCorrect() {
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
    fun validatePassword_isOfCorrectLength_returnFalse() {
        val password = "Qwe"
        val result = viewModel.validatePassword(password)
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(shortPasswordError)
    }

    @Test
    fun validatePassword_containsAtLeastOneDigit_returnFalse() {
        val password = "Qwerty++"
        val result = viewModel.validatePassword(password)
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(containsAtLeastOneDigitError)
    }

    @Test
    fun validatePassword_containsAtLeastOneCapitalLetter_returnFalse() {
        val password = "qwerty1+"
        val result = viewModel.validatePassword(password)
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(containsAtLeastOneCapitalLetterError)
    }

    @Test
    fun validatePassword_containsAtLeastOneSpecialChar_returnFalse() {
        val password = "Qwerty12"
        val result = viewModel.validatePassword(password)
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(containsAtLeastOneSpecialCharError)
    }

    @Test
    fun validateConfirmPassword_passwordsMatch() {
        val password = "Qwerty1+"
        val confirmPassword = "Qwerty1+"
        val result = viewModel.validateConfirmPassword(password, confirmPassword)
        assertThat(result.isSuccessful).isTrue()
    }

    @Test
    fun validateConfirmPassword_passwordsMatch_returnFalse() {
        val password = "Qwerty1+"
        val confirmPassword = "Qwerty1+++++"
        val result = viewModel.validateConfirmPassword(password, confirmPassword)
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(confirmPasswordError)

        val password2 = "Qwerty1+"
        val confirmPassword2 = ""
        val result2 = viewModel.validateConfirmPassword(password2, confirmPassword2)
        assertThat(result2.isSuccessful).isFalse()
        assertThat(result2.errorMessage).isEqualTo(confirmPasswordError)
    }

    @Test
    fun validateName_isCorrect() {
        val name = "John"
        val result = viewModel.validateName(name)
        assertThat(result.isSuccessful).isTrue()
    }

    @Test
    fun validateName_isNotBlank_returnFalse() {
        val name = ""
        val result = viewModel.validateName(name)
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(fieldEmptyError)
    }

    @Test
    fun validateName_doesNotContainDigit_returnFalse() {
        val name = "John 3rd"
        val result = viewModel.validateName(name)
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(digitsInNameError)
    }

    @Test
    fun validateName_doesNotContainSpecialChar_returnFalse() {
        val name = "John's"
        val result = viewModel.validateName(name)
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(specialCharsInNameError)
    }

    @Test
    fun isValidationSuccessful_allCorrect() {
        val email = "test@test.com"
        val password = "Qwerty1+"
        val confirmPassword = "Qwerty1+"
        val firstName = "John"
        val lastName = "Smith"

        val result = viewModel.isValidationSuccessful(email,password,confirmPassword,firstName,lastName)
        assertThat(result).isTrue()
    }

    @Test
    fun isValidationSuccessful_emailIncorrect() {
        val email = ""
        val password = "Qwerty1+"
        val confirmPassword = "Qwerty1+"
        val firstName = "John"
        val lastName = "Smith"

        val result = viewModel.isValidationSuccessful(email,password,confirmPassword,firstName,lastName)
        assertThat(result).isFalse()
    }

    @Test
    fun isValidationSuccessful_passwordIncorrect() {
        val email = "test@test.com"
        val password = "qwerty"
        val confirmPassword = "qwerty"
        val firstName = "John"
        val lastName = "Smith"

        val result = viewModel.isValidationSuccessful(email,password,confirmPassword,firstName,lastName)
        assertThat(result).isFalse()
    }

    @Test
    fun isValidationSuccessful_confirmPasswordIncorrect() {
        val email = "test@test.com"
        val password = "Qwerty1+"
        val confirmPassword = "qwerty"
        val firstName = "John"
        val lastName = "Smith"

        val result = viewModel.isValidationSuccessful(email,password,confirmPassword,firstName,lastName)
        assertThat(result).isFalse()
    }

    @Test
    fun isValidationSuccessful_firstNameIncorrect() {
        val email = "test@test.com"
        val password = "Qwerty1+"
        val confirmPassword = "Qwerty1+"
        val firstName = "John 3rd"
        val lastName = "Smith"

        val result = viewModel.isValidationSuccessful(email,password,confirmPassword,firstName,lastName)
        assertThat(result).isFalse()
    }

    @Test
    fun isValidationSuccessful_lastNameIncorrect() {
        val email = "test@test.com"
        val password = "Qwerty1+"
        val confirmPassword = "Qwerty1+"
        val firstName = "John"
        val lastName = "Smith's"

        val result = viewModel.isValidationSuccessful(email,password,confirmPassword,firstName,lastName)
        assertThat(result).isFalse()
    }

    @Test
    fun isValidationSuccessful_allIncorrect() {
        val email = ""
        val password = "qwerty"
        val confirmPassword = "Qwerty1+"
        val firstName = "John 3rd"
        val lastName = "Smith's"

        val result = viewModel.isValidationSuccessful(email,password,confirmPassword,firstName,lastName)
        assertThat(result).isFalse()
    }
}