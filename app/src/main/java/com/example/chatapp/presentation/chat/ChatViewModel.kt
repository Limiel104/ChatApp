package com.example.chatapp.presentation.chat

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.domain.model.Message
import com.example.chatapp.domain.use_case.ChatUseCases
import com.example.chatapp.util.Constants.USER_UID
import com.example.chatapp.util.Constants.emptyString
import com.example.chatapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatUseCases: ChatUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _chatState = mutableStateOf(ChatState())
    val chatState: State<ChatState> = _chatState

    private var deletedMessage: Message? = null

    private val _eventFlow = MutableSharedFlow<ChatUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<String>(USER_UID)?.let { userUID ->
            _chatState.value = chatState.value.copy(
                chatParticipantUserUID = userUID
            )
        }

        getChatParticipantUserData()

        _chatState.value = chatState.value.copy(
            currentUserUID = chatUseCases.getCurrentUserUseCase()!!.uid,
        )

        getMessages(
            _chatState.value.currentUserUID,
            _chatState.value.chatParticipantUserUID
        )
    }

    fun onEvent(event: ChatEvent) {
        when(event) {
            is ChatEvent.EnteredMessage -> {
                _chatState.value = chatState.value.copy(
                    messageToSend = event.value
                )
            }
            is ChatEvent.SendMessage -> {
                val message = Message(
                    text = _chatState.value.messageToSend,
                    senderUID = _chatState.value.currentUserUID,
                    receiverUID = _chatState.value.chatParticipantUserUID,
                    date = Date()
                )
                addMessage(message)

                _chatState.value = chatState.value.copy(
                    messageToSend = emptyString
                )
            }
            is ChatEvent.GoBack -> {
                goBack()
            }
            is ChatEvent.ClickedMessage -> {
                _chatState.value = chatState.value.copy(
                    isDialogActivated = true,
                    selectedMessage = event.value
                )
            }
            is ChatEvent.DismissDialog -> {
                _chatState.value = chatState.value.copy(
                    isDialogActivated = false,
                    selectedMessage = null
                )
            }
            is ChatEvent.DeleteMessage -> {
                deletedMessage = _chatState.value.selectedMessage
                deleteMessage(_chatState.value.selectedMessage!!.messageId)
                _chatState.value = chatState.value.copy(
                    isDialogActivated = false,
                    selectedMessage = null
                )
            }
            is ChatEvent.RestoreMessage -> {
                addMessage(deletedMessage!!)
                deletedMessage = null
            }
        }
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

    fun addMessage(message: Message) {
        viewModelScope.launch {
            when(val addMessageResponse = chatUseCases.addMessageUseCase(message)) {
                is Resource.Error -> {
                    Log.i("TAG","Error while adding new message")
                    val errorMessage = addMessageResponse.message
                    _eventFlow.emit(ChatUiEvent.ShowErrorMessage(errorMessage))
                }
                else -> {}
            }
        }
    }

    fun goBack() {
        viewModelScope.launch {
            _eventFlow.emit(ChatUiEvent.GoBack)
        }
    }

    fun getChatParticipantUserData(
        userUID: String = _chatState.value.chatParticipantUserUID
    ) {
        viewModelScope.launch {
            chatUseCases.getUserUseCase(userUID).collect { response ->
                when(response) {
                    is Resource.Success -> {
                        val name = response.result[0].firstName + " " + response.result[0].lastName
                        _chatState.value = chatState.value.copy(
                            chatParticipantName = name,
                            profilePictureUrl = response.result[0].profilePictureUrl
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

    fun deleteMessage(messageId: String) {
        viewModelScope.launch {
            when(val deleteResponse= chatUseCases.deleteMessageUseCase(messageId)) {
                is Resource.Error -> {
                    Log.i("TAG","Error while adding new user")
                    val errorMessage = deleteResponse.message
                    _eventFlow.emit(ChatUiEvent.ShowErrorMessage(errorMessage))
                }
                else -> {}
            }
        }
    }
}