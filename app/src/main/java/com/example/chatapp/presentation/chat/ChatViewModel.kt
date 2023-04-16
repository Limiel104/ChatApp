package com.example.chatapp.presentation.chat

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.domain.use_case.ChatUseCases
import com.example.chatapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatUseCases: ChatUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _chatState = mutableStateOf(ChatState())
    val chatState: State<ChatState> = _chatState

    init {
        savedStateHandle.get<String>("userUID")?.let { userUID ->
            _chatState.value = chatState.value.copy(
                chatParticipantUserUID = userUID
            )
        }

        _chatState.value = chatState.value.copy(
            currentUserUID = chatUseCases.getCurrentUserUseCase()!!.uid
        )

        getMessages(
            _chatState.value.currentUserUID,
            _chatState.value.chatParticipantUserUID
        )
    }

    fun getMessages(currentUserUID: String, chatParticipantUserUID: String) {
        viewModelScope.launch {
            chatUseCases.getMessagesUseCase(currentUserUID,chatParticipantUserUID).collect { response ->
                when (response) {
                    is Resource.Error -> {
                        Log.i("TAG",response.message)
                    }
                    is Resource.Loading -> {
                        Log.i("TAG","Loading")
                    }
                    is Resource.Success -> {
                        _chatState.value = chatState.value.copy(
                            messageList = response.result
                        )
                        Log.i("TAG", response.result.toString())
                    }
                }
            }
        }
    }
}