package com.example.chatapp.presentation.signup.composable

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chatapp.presentation.signup.SignupViewModel
import com.example.chatapp.R
import com.example.chatapp.presentation.signup.SignupEvent
import com.example.chatapp.presentation.signup.SignupUiEvent
import com.example.chatapp.util.Resource
import com.example.chatapp.util.Screen
import kotlinx.coroutines.flow.collectLatest

//@Preview
@Composable
fun SignupScreen(
    navController: NavController,
    viewModel: SignupViewModel = hiltViewModel()
) {

    val login = viewModel.signupState.value.email
    val password = viewModel.signupState.value.password
    val confirmPassword = viewModel.signupState.value.confirmPassword
    val firstName = viewModel.signupState.value.firstName
    val lastName = viewModel.signupState.value.lastName
    val signupResponse = viewModel.signupState.value.signupResponse

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is SignupUiEvent.Signup -> {
                    Log.i("TAG","Signup successfully")
                    navController.navigate(Screen.UserListScreen.route) {
                        popUpTo(Screen.SignupScreen.route) { inclusive = true }
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
            text = stringResource(id = R.string.create_an_account),
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
                onValueChange = { viewModel.onEvent(SignupEvent.EnteredEmail(it)) },
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = password,
                singleLine = true,
                label = { Text(stringResource(id = R.string.password)) },
                placeholder = { Text(stringResource(id = R.string.password)) },
                onValueChange = { viewModel.onEvent(SignupEvent.EnteredPassword(it)) },
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = confirmPassword,
                singleLine = true,
                label = { Text(stringResource(id = R.string.confirm_password)) },
                placeholder = { Text(stringResource(id = R.string.confirm_password)) },
                onValueChange = { viewModel.onEvent(SignupEvent.EnteredConfirmPassword(it)) },
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = firstName,
                singleLine = true,
                label = { Text(stringResource(id = R.string.first_name)) },
                placeholder = { Text(stringResource(id = R.string.first_name)) },
                onValueChange = { viewModel.onEvent(SignupEvent.EnteredFirstName(it)) },
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = lastName,
                singleLine = true,
                label = { Text(stringResource(id = R.string.last_name)) },
                placeholder = { Text(stringResource(id = R.string.last_name)) },
                onValueChange = { viewModel.onEvent(SignupEvent.EnteredLastName(it)) },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color.Black),
            onClick = { viewModel.onEvent(SignupEvent.Signup) }
        ) {
            Text(
                text = stringResource(id = R.string.signup),
                color = Color.White,
                modifier = Modifier.padding(7.dp)
            )
        }
    }

    if(signupResponse == Resource.Loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}