package com.example.chatapp.presentation.user_list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.domain.model.Message
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
    private val chatUseCases: ChatUseCases
): ViewModel() {

    private val _userListState = mutableStateOf(UserListState())
    val userListState: State<UserListState> = _userListState

    private val _eventFlow = MutableSharedFlow<UserListUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var searchQueryJob: Job? = null

    init {
        Log.i("TAG", "UserListViewModel")
        _userListState.value = userListState.value.copy(
            currentUserUID = chatUseCases.getCurrentUserUseCase()!!.uid
        )

        val currentUserUID = _userListState.value.currentUserUID
        getUsers(currentUserUID)
        getCurrentUserProfilePictureUrl(_userListState.value.currentUserUID)
        getAllUserMessages(currentUserUID)
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
                    getUsers(_userListState.value.currentUserUID)
                }
            }
            is UserListEvent.Logout -> {
                logout()
            }
        }
    }

    fun getUsers(
        currentUserUID: String,
        query: String = _userListState.value.query
    ) {
        viewModelScope.launch {
            chatUseCases.getUsersUseCase(currentUserUID).collect { response ->
                when(response) {
                    is Resource.Success -> {
                        if(query != emptyString) {
                            _userListState.value = userListState.value.copy(
                                userList = chatUseCases.filterUsersUseCase(query,response.result)
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

    fun getCurrentUserProfilePictureUrl(currentUserUID: String) {
        viewModelScope.launch {
            chatUseCases.getUserUseCase(currentUserUID).collect { response ->
                when(response) {
                    is Resource.Success -> {
                        _userListState.value = userListState.value.copy(
                            profilePicture = response.result[0].profilePictureUrl
                        )
                    }
                    is Resource.Loading -> {}
                    is Resource.Error -> {
                        Log.i("TAG",response.message)
                    }
                }
            }
        }
    }

    fun getAllUserMessages(currentUserUID: String) {
        viewModelScope.launch {
            chatUseCases.getAllUserMessagesUseCase(currentUserUID).collect { response ->
                when(response) {
                    is Resource.Success -> {
                        val messages = response.result
                        Log.i("TAG",messages.toString())
                        getLastMessages(messages, currentUserUID)
                    }
                    is Resource.Loading -> {}
                    is Resource.Error -> {
                        Log.i("TAG",response.message)
                    }
                }
            }
        }
    }

    fun getLastMessages(allMessages: List<Message>, currentUserUID: String) {
        val lastMessages =  chatUseCases.getLastMessagesUseCase(allMessages, currentUserUID)
        _userListState.value = userListState.value.copy(
            lastMessages = lastMessages
        )
    }

    fun logout() {
        viewModelScope.launch {
            chatUseCases.logoutUseCase()
            _eventFlow.emit(UserListUiEvent.Logout)
        }
    }
}