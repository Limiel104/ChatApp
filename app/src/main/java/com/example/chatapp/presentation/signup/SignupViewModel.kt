package com.example.chatapp.presentation.signup

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.domain.ValidationResult
import com.example.chatapp.domain.repository.AuthRepository
import com.example.chatapp.domain.repository.UserStorageRepository
import com.example.chatapp.util.Constants.confirmPasswordError
import com.example.chatapp.util.Constants.containsAtLeastOneCapitalLetterError
import com.example.chatapp.util.Constants.containsAtLeastOneDigitError
import com.example.chatapp.util.Constants.containsAtLeastOneSpecialCharError
import com.example.chatapp.util.Constants.digitsInNameError
import com.example.chatapp.util.Constants.emailEmptyError
import com.example.chatapp.util.Constants.fieldEmptyError
import com.example.chatapp.util.Constants.passwordEmptyError
import com.example.chatapp.util.Constants.shortPasswordError
import com.example.chatapp.util.Constants.specialChars
import com.example.chatapp.util.Constants.specialCharsInNameError
import com.example.chatapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserStorageRepository
) : ViewModel() {

    private val _signupState = mutableStateOf(SignupState())
    val signupState: State<SignupState> = _signupState

    private val _eventFlow = MutableSharedFlow<SignupUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: SignupEvent) {
        when (event) {
            is SignupEvent.EnteredEmail -> {
                _signupState.value = signupState.value.copy(
                    email = event.value
                )
            }
            is SignupEvent.EnteredPassword -> {
                _signupState.value = signupState.value.copy(
                    password = event.value
                )
            }
            is SignupEvent.EnteredConfirmPassword -> {
                _signupState.value = signupState.value.copy(
                    confirmPassword = event.value
                )
            }
            is SignupEvent.EnteredFirstName -> {
                _signupState.value = signupState.value.copy(
                    firstName = event.value
                )
            }
            is SignupEvent.EnteredLastName -> {
                _signupState.value = signupState.value.copy(
                    lastName = event.value
                )
            }
            is SignupEvent.Signup -> {
                val email = _signupState.value.email
                val password = _signupState.value.password
                val confirmPassword = _signupState.value.confirmPassword
                val firstName = _signupState.value.firstName
                val lastName = _signupState.value.lastName

                if (isValidationSuccessful(email, password, confirmPassword, firstName, lastName)) {
                    signup(email,password,firstName,lastName)
                } else {
                    Log.i("TAG", "Invalid signup credentials")
                }
            }
        }
    }

    fun signup(email: String, password: String, firstName: String, lastName: String) {
        viewModelScope.launch {
            _signupState.value = signupState.value.copy(
                signupResponse = Resource.Loading
            )

            _signupState.value = signupState.value.copy(
                signupResponse = authRepository.signup(
                    email,
                    password,
                    firstName,
                    lastName
                )
            )

            when (val signupResponse = _signupState.value.signupResponse) {
                is Resource.Success -> {
                    when(val addUserResponse = userRepository.addUser(authRepository.currentUser!!.uid, firstName, lastName, "avatarURL")) {
                        is Resource.Error -> {
                            Log.i("TAG","Error while adding new user")
                            val errorMessage = addUserResponse.message
                            _eventFlow.emit(SignupUiEvent.ShowErrorMessage(errorMessage))
                        }
                        else -> {}
                    }
                    _eventFlow.emit(SignupUiEvent.Signup)
                }
                is Resource.Error -> {
                    val errorMessage = signupResponse.message
                    Log.i("TAG", "Signup Error")
                    _signupState.value = signupState.value.copy(
                        emailError =  null,
                        passwordError = null,
                        confirmPasswordError = null,
                        firstNameError = null,
                        lastNameError = null
                    )
                    _eventFlow.emit(SignupUiEvent.ShowErrorMessage(errorMessage))
                }
                else -> {
                    Log.i("TAG", "Loading...")
                }
            }
        }
    }

    fun validateEmail(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = emailEmptyError
            )
        }
        return ValidationResult(
            isSuccessful = true
        )
    }

    fun validatePassword(password: String): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = passwordEmptyError
            )
        }
        if (password.length < 8) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = shortPasswordError
            )
        }
        val containsAtLeastOneDigit = password.any { it.isDigit() }
        if (!containsAtLeastOneDigit) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = containsAtLeastOneDigitError
            )
        }
        val containsAtLeastOneCapitalLetter = password.any { it.isUpperCase() }
        if (!containsAtLeastOneCapitalLetter) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = containsAtLeastOneCapitalLetterError
            )
        }
        val containsAtLeastOneSpecialChar = password.any { it in specialChars }
        if (!containsAtLeastOneSpecialChar) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = containsAtLeastOneSpecialCharError
            )
        }
        return ValidationResult(
            isSuccessful = true
        )
    }

    fun validateConfirmPassword(password: String, confirmPassword: String): ValidationResult {
        if (password != confirmPassword) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = confirmPasswordError
            )
        }
        return ValidationResult(
            isSuccessful = true
        )
    }

    fun validateName(name: String): ValidationResult {
        if (name.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = fieldEmptyError
            )
        }
        val containsDigit = name.any { it.isDigit() }
        if (containsDigit) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = digitsInNameError
            )
        }
        val containsSpecialChar = name.any { it in specialChars }
        if (containsSpecialChar) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = specialCharsInNameError
            )
        }
        return ValidationResult(
            isSuccessful = true
        )
    }

    fun isValidationSuccessful(
        email: String,
        password: String,
        confirmPassword: String,
        firstName: String,
        lastName: String
    ): Boolean {
        val emailValidationResult = validateEmail(email)
        val passwordValidationResult = validatePassword(password)
        val confirmPasswordValidationResult = validateConfirmPassword(password,confirmPassword)
        val firstNameValidationResult = validateName(firstName)
        val lastNameValidationResult = validateName(lastName)

        val hasError = listOf(
            emailValidationResult,
            passwordValidationResult,
            confirmPasswordValidationResult,
            firstNameValidationResult,
            lastNameValidationResult
        ).any { !it.isSuccessful }

        if(hasError) {
            _signupState.value = signupState.value.copy(
                emailError =  emailValidationResult.errorMessage,
                passwordError = passwordValidationResult.errorMessage,
                confirmPasswordError = confirmPasswordValidationResult.errorMessage,
                firstNameError = firstNameValidationResult.errorMessage,
                lastNameError = lastNameValidationResult.errorMessage
            )
            return false
        }
        return true
    }
}