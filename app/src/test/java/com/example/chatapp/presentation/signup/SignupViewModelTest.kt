package com.example.chatapp.presentation.signup

import com.example.chatapp.data.repository.FakeAuthRepository
import com.example.chatapp.data.repository.FakeUserStorageRepository
import com.example.chatapp.util.Constants.confirmPasswordCorrect
import com.example.chatapp.util.Constants.confirmPasswordError
import com.example.chatapp.util.Constants.confirmPasswordIncorrect
import com.example.chatapp.util.Constants.containsAtLeastOneCapitalLetterError
import com.example.chatapp.util.Constants.containsAtLeastOneDigitError
import com.example.chatapp.util.Constants.containsAtLeastOneSpecialCharError
import com.example.chatapp.util.Constants.digitsInNameError
import com.example.chatapp.util.Constants.emailCorrect
import com.example.chatapp.util.Constants.emailEmptyError
import com.example.chatapp.util.Constants.emptyString
import com.example.chatapp.util.Constants.fieldEmptyError
import com.example.chatapp.util.Constants.firstNameCorrect
import com.example.chatapp.util.Constants.firstNameHasDigit
import com.example.chatapp.util.Constants.firstNameHasSpecialChar
import com.example.chatapp.util.Constants.lastNameCorrect
import com.example.chatapp.util.Constants.lastNameHasSpecialChar
import com.example.chatapp.util.Constants.passwordCorrect
import com.example.chatapp.util.Constants.passwordEmptyError
import com.example.chatapp.util.Constants.passwordIncorrect
import com.example.chatapp.util.Constants.passwordTooShort
import com.example.chatapp.util.Constants.passwordWithoutCapitalLetter
import com.example.chatapp.util.Constants.passwordWithoutDigit
import com.example.chatapp.util.Constants.passwordWithoutSpecialChar
import com.example.chatapp.util.Constants.shortPasswordError
import com.example.chatapp.util.Constants.specialCharsInNameError
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class SignupViewModelTest {

    private lateinit var viewModel: SignupViewModel

    @Before
    fun setUp() {
        viewModel = SignupViewModel(FakeAuthRepository(), FakeUserStorageRepository())
    }

    @Test
    fun validateEmail_isCorrect() {
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
    fun validatePassword_isCorrect() {
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
    fun validatePassword_isOfCorrectLength_returnFalse() {
        val password = passwordTooShort
        val result = viewModel.validatePassword(password)
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(shortPasswordError)
    }

    @Test
    fun validatePassword_containsAtLeastOneDigit_returnFalse() {
        val password = passwordWithoutDigit
        val result = viewModel.validatePassword(password)
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(containsAtLeastOneDigitError)
    }

    @Test
    fun validatePassword_containsAtLeastOneCapitalLetter_returnFalse() {
        val password = passwordWithoutCapitalLetter
        val result = viewModel.validatePassword(password)
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(containsAtLeastOneCapitalLetterError)
    }

    @Test
    fun validatePassword_containsAtLeastOneSpecialChar_returnFalse() {
        val password = passwordWithoutSpecialChar
        val result = viewModel.validatePassword(password)
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(containsAtLeastOneSpecialCharError)
    }

    @Test
    fun validateConfirmPassword_passwordsMatch() {
        val password = passwordCorrect
        val confirmPassword = confirmPasswordCorrect
        val result = viewModel.validateConfirmPassword(password, confirmPassword)
        assertThat(result.isSuccessful).isTrue()
    }

    @Test
    fun validateConfirmPassword_passwordsMatch_returnFalse() {
        val password = passwordCorrect
        val confirmPassword = confirmPasswordIncorrect
        val result = viewModel.validateConfirmPassword(password, confirmPassword)
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(confirmPasswordError)

        val password2 = passwordCorrect
        val confirmPassword2 = emptyString
        val result2 = viewModel.validateConfirmPassword(password2, confirmPassword2)
        assertThat(result2.isSuccessful).isFalse()
        assertThat(result2.errorMessage).isEqualTo(confirmPasswordError)
    }

    @Test
    fun validateName_isCorrect() {
        val name = firstNameCorrect
        val result = viewModel.validateName(name)
        assertThat(result.isSuccessful).isTrue()
    }

    @Test
    fun validateName_isNotBlank_returnFalse() {
        val name = emptyString
        val result = viewModel.validateName(name)
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(fieldEmptyError)
    }

    @Test
    fun validateName_doesNotContainDigit_returnFalse() {
        val name = firstNameHasDigit
        val result = viewModel.validateName(name)
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(digitsInNameError)
    }

    @Test
    fun validateName_doesNotContainSpecialChar_returnFalse() {
        val name = firstNameHasSpecialChar
        val result = viewModel.validateName(name)
        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(specialCharsInNameError)
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