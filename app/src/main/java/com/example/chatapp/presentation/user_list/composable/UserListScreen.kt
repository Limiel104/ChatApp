package com.example.chatapp.presentation.user_list.composable

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chatapp.domain.model.Message
import com.example.chatapp.presentation.user_list.UserListEvent
import com.example.chatapp.presentation.user_list.UserListUiEvent
import com.example.chatapp.presentation.user_list.UserListViewModel
import com.example.chatapp.util.Constants.emptyString
import com.example.chatapp.util.Screen
import kotlinx.coroutines.flow.collectLatest
import java.text.DateFormat

@Composable
fun UserListScreen(
    navController: NavController,
    viewModel: UserListViewModel = hiltViewModel()
) {
    val userList = viewModel.userListState.value.userList
    val query = viewModel.userListState.value.query
    val profilePictureUrl = viewModel.userListState.value.profilePicture
    val currentUserUID = viewModel.userListState.value.currentUserUID
    val lastMessages: Map<String, Message> = viewModel.userListState.value.lastMessages

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is UserListUiEvent.Logout -> {
                    Log.i("TAG","Logout")
                    navController.navigate(Screen.LoginScreen.route) {
                        popUpTo(Screen.LoginScreen.route) { inclusive = true }
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            UserListTopBar(
                query = query,
                profilePictureUrl = profilePictureUrl,
                onValueChange = { viewModel.onEvent(UserListEvent.OnQueryChange(it)) },
                onClick = { viewModel.onEvent(UserListEvent.Logout) },
                onImageClick = { navController.navigate(Screen.UserProfileScreen.route+ "userUID=$currentUserUID") }
            )
        },
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
                    .fillMaxSize()
            ) {
                itemsIndexed(userList) { index, user ->
                    val message = if(lastMessages[user.userUID] != null) {
                        lastMessages[user.userUID]!!.text
                    }
                    else {
                        emptyString
                    }
                    val date = if(lastMessages[user.userUID] != null) {
                        DateFormat.getDateInstance(DateFormat.MONTH_FIELD).format(lastMessages[user.userUID]!!.date)
                    }
                    else {
                        emptyString
                    }

                    UserListItem(
                        user = user,
                        message = message,
                        date = date,
                        onItemClick = {
                            navController.navigate(Screen.ChatScreen.route + "userUID=${ user.userUID }")
                        }
                    )
                }
            }
        }
    }
}