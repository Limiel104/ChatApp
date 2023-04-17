package com.example.chatapp.presentation.chat.composable

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chatapp.presentation.chat.ChatEvent
import com.example.chatapp.presentation.chat.ChatUiEvent
import com.example.chatapp.presentation.chat.ChatViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ChatScreen(
    navController: NavController,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val messages = viewModel.chatState.value.messageList
    val currentUserUID = viewModel.chatState.value.currentUserUID
    val messageToSend = viewModel.chatState.value.messageToSend
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is ChatUiEvent.ShowErrorMessage -> {
                    Toast.makeText(context,event.message,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    Scaffold(
        topBar = { ChatTopBar("John Smith") },
        bottomBar = {
            ChatBottomBar(
                message = messageToSend,
                onValueChange = { viewModel.onEvent(ChatEvent.EnteredMessage(it)) },
                onClick = { viewModel.onEvent(ChatEvent.SendMessage) }
            ) },
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
                        isSendByCurrentUser = currentUserUID == message.senderUID
                    )
                }
            }
        }
    }
}