package com.example.chatapp.presentation.login.composable

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chatapp.presentation.login.LoginEvent
import com.example.chatapp.presentation.login.LoginUiEvent
import com.example.chatapp.presentation.login.LoginViewModel
import kotlinx.coroutines.flow.collectLatest
import com.example.chatapp.R
import com.example.chatapp.util.Resource
import com.example.chatapp.util.Screen

//@Preview
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val login = viewModel.loginState.value.email
    val password = viewModel.loginState.value.password
    val loginResponse = viewModel.loginState.value.loginResponse

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is LoginUiEvent.Login -> {
                    Log.i("TAG","Logged in successfully")
                    navController.navigate(Screen.UserListScreen.route) {
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
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = stringResource(id = R.string.welcome_message),
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold
        )

        Column(
        ) {
            OutlinedTextField(
                value = login,
                singleLine = true,
                label = { Text(stringResource(id = R.string.email)) },
                placeholder = { Text(stringResource(id = R.string.email)) },
                onValueChange = { viewModel.onEvent(LoginEvent.EnteredEmail(it)) },
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = password,
                singleLine = true,
                label = { Text(stringResource(id = R.string.password)) },
                placeholder = { Text(stringResource(id = R.string.password)) },
                onValueChange = { viewModel.onEvent(LoginEvent.EnteredPassword(it)) },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color.Black),
            onClick = { viewModel.onEvent(LoginEvent.Login) }
        ) {
            Text(
                text = stringResource(id = R.string.login),
                color = Color.White,
                modifier = Modifier.padding(7.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.no_account_message)
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = stringResource(id = R.string.signup),
                color = Color.Blue,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .clickable {
                        navController.navigate(Screen.SignupScreen.route)
                    }
            )
        }
    }

    if(loginResponse == Resource.Loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}