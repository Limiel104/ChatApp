package com.example.chatapp.presentation.user_list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.domain.repository.UserRepository
import com.example.chatapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    private val _userListState = mutableStateOf(UserListState())
    val userListState: State<UserListState> = _userListState

    init {
        Log.i("TAG", "UserListViewModel")
        getUsers()
    }

    fun getUsers() {
        viewModelScope.launch {
            userRepository.getUserList().collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _userListState.value = userListState.value.copy(
                            userList = response.result
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
}