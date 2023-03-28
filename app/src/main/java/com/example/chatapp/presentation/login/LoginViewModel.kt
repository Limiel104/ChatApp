package com.example.chatapp.presentation.login

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
class LoginViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
): ViewModel() {

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    private val _eventFlow = MutableSharedFlow<LoginUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: LoginEvent) {
        when(event) {
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
                    val isValidationSuccessful = validateEmailAndPassword()
                    if (isValidationSuccessful) {
                        login(_loginState.value.email, _loginState.value.password)
                    }
                    else {
                        Log.i("TAG","Invalid login credentials")
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
                loginResponse = authenticationRepository.login(email,password)
            )

            when (_loginState.value.loginResponse) {
                is Resource.Success -> {
                    _eventFlow.emit(LoginUiEvent.Login)
                }
                is Resource.Error -> {
                    Log.i("TAG","Login Error")
                }
                else -> {
                    Log.i("TAG","Loading...")
                }
            }
        }
    }

    fun validateEmailAndPassword(): Boolean {
        return _loginState.value.email.isNotBlank() && _loginState.value.password.isNotBlank()
    }
}