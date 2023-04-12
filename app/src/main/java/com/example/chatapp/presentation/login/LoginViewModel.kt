package com.example.chatapp.presentation.login

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.domain.repository.AuthRepository
import com.example.chatapp.domain.use_case.ChatUseCases
import com.example.chatapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val chatUseCases: ChatUseCases
) : ViewModel() {

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    private val _eventFlow = MutableSharedFlow<LoginUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnteredEmail -> {
                _loginState.value = loginState.value.copy(
                    email = event.value
                )
            }
            is LoginEvent.EnteredPassword -> {
                _loginState.value = loginState.value.copy(
                    password = event.value
                )
            }
            is LoginEvent.Login -> {
                val email = _loginState.value.email
                val password = _loginState.value.password

                if (isValidationSuccessful(email, password)) {
                    login(email, password)
                } else {
                    Log.i("TAG", "Invalid login credentials")
                }
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = loginState.value.copy(
                loginResponse = Resource.Loading
            )
            _loginState.value = loginState.value.copy(
                loginResponse = authRepository.login(email, password)
            )

            when (_loginState.value.loginResponse) {
                is Resource.Success -> {
                    _eventFlow.emit(LoginUiEvent.Login)
                }
                is Resource.Error -> {
                    val errorMessage = (_loginState.value.loginResponse as Resource.Error).message
                    Log.i("TAG", "Login Error")
                    _loginState.value = loginState.value.copy(
                        emailError = null,
                        passwordError = null
                    )
                    _eventFlow.emit(LoginUiEvent.ShowErrorMessage(errorMessage))
                }
                else -> {
                    Log.i("TAG", "Loading...")
                }
            }
        }
    }

    fun isValidationSuccessful(email: String, password: String): Boolean {
        val emailValidationResult = chatUseCases.validateLoginEmailUseCase(email)
        val passwordValidationResult = chatUseCases.validateLoginPasswordUseCase(password)

        val hasError = listOf(
            emailValidationResult,
            passwordValidationResult
        ).any { !it.isSuccessful }

        if (hasError) {
            _loginState.value = loginState.value.copy(
                emailError = emailValidationResult.errorMessage,
                passwordError = passwordValidationResult.errorMessage
            )
            return false
        }
        return true
    }
}