package com.example.chatapp.presentation.signup

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                val isValidationSuccessful = validateForm()
                if (isValidationSuccessful) {
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
                    Log.i("TAG", "Signup Error")
                }
                else -> {
                    Log.i("TAG", "Loading...")
                }
            }
        }
    }

    fun validateForm(): Boolean {
        return validateFormIsFilled() && validatePasswordsAreTheSame()
    }

    fun validateFormIsFilled(): Boolean {
        return _signupState.value.email.isNotBlank() &&
                _signupState.value.password.isNotBlank() &&
                _signupState.value.confirmPassword.isNotBlank() &&
                _signupState.value.firstName.isNotBlank() &&
                _signupState.value.lastName.isNotBlank()
    }

    fun validatePasswordsAreTheSame(): Boolean {
        return _signupState.value.password == _signupState.value.confirmPassword
    }
}