package com.example.chatapp.presentation.signup

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.domain.repository.AuthRepository
import com.example.chatapp.domain.repository.UserStorageRepository
import com.example.chatapp.domain.use_case.ChatUseCases
import com.example.chatapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userStorageRepository: UserStorageRepository,
    private val chatUseCases: ChatUseCases
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
                    when(val addUserResponse = userStorageRepository.addUser(authRepository.currentUser!!.uid, firstName, lastName, "avatarURL")) {
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

    fun isValidationSuccessful(
        email: String,
        password: String,
        confirmPassword: String,
        firstName: String,
        lastName: String
    ): Boolean {
        val emailValidationResult = chatUseCases.validateEmailUseCase(email)
        val passwordValidationResult = chatUseCases.validateSignupPasswordUseCase(password)
        val confirmPasswordValidationResult = chatUseCases.validateConfirmPasswordUseCase(password, confirmPassword)
        val firstNameValidationResult = chatUseCases.validateNameUseCase(firstName)
        val lastNameValidationResult = chatUseCases.validateNameUseCase(lastName)

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