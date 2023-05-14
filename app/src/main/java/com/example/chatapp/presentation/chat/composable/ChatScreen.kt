package com.example.chatapp.presentation.chat.composable

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chatapp.R
import com.example.chatapp.presentation.chat.ChatEvent
import com.example.chatapp.presentation.chat.ChatUiEvent
import com.example.chatapp.presentation.chat.ChatViewModel
import com.example.chatapp.util.Constants.emptyString
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun ChatScreen(
    navController: NavController,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val messages = viewModel.chatState.value.messageList
    val currentUserUID = viewModel.chatState.value.currentUserUID
    val messageToSend = viewModel.chatState.value.messageToSend
    val name = viewModel.chatState.value.chatParticipantName
    val profilePictureUrl = viewModel.chatState.value.profilePictureUrl
    val isDialogActivated = viewModel.chatState.value.isDialogActivated
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is ChatUiEvent.ShowErrorMessage -> {
                    Toast.makeText(context,event.message,Toast.LENGTH_SHORT).show()
                }
                is ChatUiEvent.GoBack -> {
                    navController.popBackStack()
                }
            }
        }
    }
    
    Scaffold(
        topBar = {
            ChatTopBar(
                name = name,
                profilePictureUrl = profilePictureUrl,
                onClick = { viewModel.onEvent(ChatEvent.GoBack) }
        ) },
        bottomBar = {
            ChatBottomBar(
                message = messageToSend,
                onValueChange = { viewModel.onEvent(ChatEvent.EnteredMessage(it)) },
                onClick = {
                    if(messageToSend != emptyString) {
                        viewModel.onEvent(ChatEvent.SendMessage)
                    }
                }
            ) },
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(10.dp)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                reverseLayout = true
            ) {
                itemsIndexed(messages) { _, message ->
                    Message(
                        text = message.text,
                        isSendByCurrentUser = currentUserUID == message.senderUID,
                        onClick = { viewModel.onEvent(ChatEvent.ClickedMessage(message)) }
                    )
                }
            }
        }

        if(isDialogActivated){
            DeleteDialog(
                onDelete = {
                    viewModel.onEvent(ChatEvent.DeleteMessage)
                    scope.launch {
                        val result = scaffoldState.snackbarHostState.showSnackbar(
                            message = context.resources.getString(R.string.message_deleted),
                            actionLabel = context.resources.getString(R.string.undo)
                        )
                        if(result == SnackbarResult.ActionPerformed) {
                            viewModel.onEvent(ChatEvent.RestoreMessage)
                        }
                    }
                },
                onDismiss = { viewModel.onEvent(ChatEvent.DismissDialog) }
            )
        }
    }
}