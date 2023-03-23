package com.example.chatapp.presentation.login

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
class LoginViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
): ViewModel() {

    private val _loginSate = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginSate

    private val _eventFlow = MutableSharedFlow<LoginUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: LoginEvent) {
        when(event) {
            is LoginEvent.EnteredEmail -> {
                _loginSate.value = loginState.value.copy(
                    email = event.value
                )
            }
            is LoginEvent.EnteredPassword -> {
                _loginSate.value = loginState.value.copy(
                    password = event.value
                )
            }
            is LoginEvent.Login -> {
                viewModelScope.launch {
                    val isValidationSuccessful = validateEmailAndPassword()
                    if (isValidationSuccessful) {
                        login(_loginSate.value.email, _loginSate.value.password)
                        when (_loginSate.value.loginResponse) {
                            is Resource.Success -> {
                                _eventFlow.emit(LoginUiEvent.Login)
                            }
                        }
                    }
                }
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginSate.value = loginState.value.copy(
                loginResponse = Resource.Loading
            )
            _loginSate.value = loginState.value.copy(
                loginResponse = authenticationRepository.login(email,password)
            )
        }
    }

    fun validateEmailAndPassword(): Boolean {
        return _loginSate.value.email.isNotBlank() && _loginSate.value.password.isNotBlank()
    }
}