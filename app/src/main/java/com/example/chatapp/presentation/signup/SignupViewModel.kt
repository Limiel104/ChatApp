package com.example.chatapp.presentation.signup

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.domain.ValidationResult
import com.example.chatapp.domain.repository.AuthenticationRepository
import com.example.chatapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
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
                if (isValidationSuccessful()) {
                    signup(
                        _signupState.value.email,
                        _signupState.value.confirmPassword,
                        _signupState.value.firstName,
                        _signupState.value.lastName
                    )
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
                signupResponse = authenticationRepository.signup(
                    email,
                    password,
                    firstName,
                    lastName
                )
            )

            when (_signupState.value.signupResponse) {
                is Resource.Success -> {
                    _eventFlow.emit(SignupUiEvent.Signup)
                }
                is Resource.Error -> {
                    val errorMessage = (_signupState.value.signupResponse as Resource.Error).message
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
                errorMessage = "Email can't be empty"
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Invalid email"
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
                errorMessage = "Password can't be empty"
            )
        }
        if (password.length < 8) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Password is too short"
            )
        }
        val containsAtLeastOneDigit = password.any { it.isDigit() }
        if (!containsAtLeastOneDigit) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Password should have at least one digit"
            )
        }
        val containsAtLeastOneCapitalLetter = password.any { it.isUpperCase() }
        if (!containsAtLeastOneCapitalLetter) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Password should have at least one capital letter"
            )
        }
        val specialChars = "!@#$%^&*(){}[]:;\"'<,>.?/~`'\\|-_=+"
        val containsAtLeastOneSpecialChar = password.any { it in specialChars }
        if (!containsAtLeastOneSpecialChar) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Password should have at least one special character"
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
                errorMessage = "Passwords don't mach"
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
                errorMessage = "Field can't be empty"
            )
        }
        val containsDigit = name.any { it.isDigit() }
        if (containsDigit) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "No digits allowed for this field"
            )
        }
        val specialChars = "!@#$%^&*(){}[]:;\"'<,>.?/~`'\\|_=+"
        val containsSpecialChar = name.any { it in specialChars }
        if (containsSpecialChar) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = "Special characters not allowed for this field"
            )
        }
        return ValidationResult(
            isSuccessful = true
        )
    }

    fun isValidationSuccessful(): Boolean {
        val emailValidationResult = validateEmail(_signupState.value.email)
        val passwordValidationResult = validatePassword(_signupState.value.password)
        val confirmPasswordValidationResult = validateConfirmPassword(_signupState.value.password,_signupState.value.confirmPassword)
        val firstNameValidationResult = validateName(_signupState.value.firstName)
        val lastNameValidationResult = validateName(_signupState.value.lastName)

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