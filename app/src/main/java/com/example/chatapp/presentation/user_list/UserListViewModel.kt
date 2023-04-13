package com.example.chatapp.presentation.user_list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.domain.repository.UserStorageRepository
import com.example.chatapp.domain.use_case.ChatUseCases
import com.example.chatapp.util.Constants.emptyString
import com.example.chatapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val userStorageRepository: UserStorageRepository,
    private val chatUseCases: ChatUseCases
): ViewModel() {

    private val _userListState = mutableStateOf(UserListState())
    val userListState: State<UserListState> = _userListState

    private val _eventFlow = MutableSharedFlow<UserListUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var searchQueryJob: Job? = null

    init {
        Log.i("TAG", "UserListViewModel")
        getUsers()
    }

    fun onEvent(event: UserListEvent) {
        when(event) {
            is UserListEvent.OnQueryChange -> {
                _userListState.value = userListState.value.copy(
                    query = event.value
                )
                searchQueryJob?.cancel()
                searchQueryJob = viewModelScope.launch {
                    delay(500L)
                    getUsers()
                }
            }
            is UserListEvent.Logout -> {
                logout()
            }
        }
    }

    fun getUsers(
        query: String = _userListState.value.query
    ) {
        viewModelScope.launch {
            val currentUserUID = chatUseCases.getCurrentUserUseCase()!!.uid
            userStorageRepository.getUserList(currentUserUID).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        if(query != emptyString) {
                            val filteredResponse = response.result.filter {  user ->
                                user.firstName.contains(query,true)
                            }
                            _userListState.value = userListState.value.copy(
                                userList = filteredResponse
                            )
                        }
                        else {
                            _userListState.value = userListState.value.copy(
                                userList = response.result
                            )
                        }

                    }
                    is Resource.Loading -> {}
                    is Resource.Error -> {
                        Log.i("TAG",response.message)
                    }
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            chatUseCases.logoutUseCase()
            _eventFlow.emit(UserListUiEvent.Logout)
        }
    }
}