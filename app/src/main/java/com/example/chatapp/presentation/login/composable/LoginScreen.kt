package com.example.chatapp.presentation.login.composable

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chatapp.presentation.login.LoginEvent
import com.example.chatapp.presentation.login.LoginUiEvent
import com.example.chatapp.presentation.login.LoginViewModel
import kotlinx.coroutines.flow.collectLatest
import com.example.chatapp.R
import com.example.chatapp.presentation.common.composable.ChatButton
import com.example.chatapp.presentation.common.composable.ChatTextField
import com.example.chatapp.presentation.common.composable.ErrorTextFieldItem
import com.example.chatapp.util.Constants.CIRCULAR_INDICATOR
import com.example.chatapp.util.Constants.EMAIL_ERROR_TF
import com.example.chatapp.util.Constants.EMAIL_TF
import com.example.chatapp.util.Constants.LOGIN_BUTTON
import com.example.chatapp.util.Constants.PASSWORD_ERROR_TF
import com.example.chatapp.util.Constants.PASSWORD_TF
import com.example.chatapp.util.Constants.SIGNUP_TF
import com.example.chatapp.util.Resource
import com.example.chatapp.util.Screen

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val email = viewModel.loginState.value.email
    val emailError = viewModel.loginState.value.emailError
    val password = viewModel.loginState.value.password
    val passwordError = viewModel.loginState.value.passwordError
    val isLoading = viewModel.loginState.value.loginResponse == Resource.Loading
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is LoginUiEvent.Login -> {
                    Log.i("TAG","Logged in successfully")
                    navController.navigate(Screen.UserListScreen.route) {
                        popUpTo(Screen.LoginScreen.route) { inclusive = true }
                    }
                }
                is LoginUiEvent.ShowErrorMessage -> {
                    Toast.makeText(context,event.message,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = stringResource(id = R.string.welcome_message),
            color = MaterialTheme.colors.secondary,
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold
        )

        Column {
            ChatTextField(
                text = email,
                label = stringResource(id = R.string.email),
                placeholder = stringResource(id = R.string.email),
                testTag = EMAIL_TF,
                isError = emailError != null,
                onValueChange = { viewModel.onEvent(LoginEvent.EnteredEmail(it)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                )
            )

            if(emailError != null) {
                ErrorTextFieldItem(
                    errorMessage = emailError,
                    testTag = EMAIL_ERROR_TF
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            ChatTextField(
                text = password,
                label = stringResource(id = R.string.password),
                placeholder = stringResource(id = R.string.password),
                testTag = PASSWORD_TF,
                isError = passwordError != null,
                onValueChange = { viewModel.onEvent(LoginEvent.EnteredPassword(it)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation()
            )

            if(passwordError != null) {
                ErrorTextFieldItem(
                    errorMessage = passwordError,
                    testTag = PASSWORD_ERROR_TF
                )
            }
        }

        ChatButton(
            text = stringResource(id = R.string.login),
            testTag = LOGIN_BUTTON,
            onClick = { viewModel.onEvent(LoginEvent.Login) }
        )

        stringResource(id = R.string.edit_password)

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
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .clickable { navController.navigate(Screen.SignupScreen.route) }
                    .testTag(SIGNUP_TF)
            )
        }
    }

    if(isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .testTag(CIRCULAR_INDICATOR),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}