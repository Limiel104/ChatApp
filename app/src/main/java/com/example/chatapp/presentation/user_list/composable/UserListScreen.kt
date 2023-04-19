package com.example.chatapp.presentation.user_list.composable

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chatapp.presentation.user_list.UserListEvent
import com.example.chatapp.presentation.user_list.UserListUiEvent
import com.example.chatapp.presentation.user_list.UserListViewModel
import com.example.chatapp.util.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun UserListScreen(
    navController: NavController,
    viewModel: UserListViewModel = hiltViewModel()
) {
    val userList = viewModel.userListState.value.userList
    val query = viewModel.userListState.value.query

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { viewModel.onEvent(UserListEvent.OnQueryChange(it)) },
                textStyle = TextStyle(fontSize = 13.sp),
                placeholder = {
                    Text(
                        text = "Search ...",
                        fontSize = 13.sp
                    )
                },
                maxLines = 1,
                singleLine = true,
                shape = RoundedCornerShape(30.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            IconButton(
                onClick = { viewModel.onEvent(UserListEvent.Logout) }
            ) {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = "Logout"
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            itemsIndexed(userList) { index, user ->
                UserListItem(
                    user = user,
                    onItemClick = {
                        navController.navigate(Screen.ChatScreen.route + "userUID=${ user.userUID }")
                        Log.i("TAG",user.userUID)
                        Log.i("TAG",user.firstName + " " + user.lastName)
                    }
                )
            }
        }
    }
}