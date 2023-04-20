package com.example.chatapp.presentation.signup.composable

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chatapp.presentation.signup.SignupViewModel
import com.example.chatapp.R
import com.example.chatapp.presentation.common.composable.ErrorTextFieldItem
import com.example.chatapp.presentation.signup.SignupEvent
import com.example.chatapp.presentation.signup.SignupUiEvent
import com.example.chatapp.util.Constants.CIRCULAR_INDICATOR
import com.example.chatapp.util.Constants.CONFIRM_PASSWORD_ERROR_TF
import com.example.chatapp.util.Constants.CONFIRM_PASSWORD_TF
import com.example.chatapp.util.Constants.DEVICE_IMAGES
import com.example.chatapp.util.Constants.EMAIL_ERROR_TF
import com.example.chatapp.util.Constants.EMAIL_TF
import com.example.chatapp.util.Constants.FIRST_NAME_ERROR_TF
import com.example.chatapp.util.Constants.FIRST_NAME_TF
import com.example.chatapp.util.Constants.LAST_NAME_ERROR_TF
import com.example.chatapp.util.Constants.LAST_NAME_TF
import com.example.chatapp.util.Constants.PASSWORD_ERROR_TF
import com.example.chatapp.util.Constants.PASSWORD_TF
import com.example.chatapp.util.Constants.SIGNUP_BUTTON
import com.example.chatapp.util.Resource
import com.example.chatapp.util.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignupScreen(
    navController: NavController,
    viewModel: SignupViewModel = hiltViewModel()
) {

    val email = viewModel.signupState.value.email
    val emailError = viewModel.signupState.value.emailError
    val password = viewModel.signupState.value.password
    val passwordError = viewModel.signupState.value.passwordError
    val confirmPassword = viewModel.signupState.value.confirmPassword
    val confirmPasswordError = viewModel.signupState.value.confirmPasswordError
    val firstName = viewModel.signupState.value.firstName
    val firstNameError = viewModel.signupState.value.firstNameError
    val lastName = viewModel.signupState.value.lastName
    val lastNameError = viewModel.signupState.value.lastNameError
    val isLoading = viewModel.signupState.value.signupResponse == Resource.Loading
    val context = LocalContext.current
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { imageUri ->
        imageUri?.let {
            viewModel.onEvent(SignupEvent.SelectedProfilePicture(imageUri))
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is SignupUiEvent.Signup -> {
                    Log.i("TAG","Signup successfully")
                    navController.navigate(Screen.UserListScreen.route) {
                        popUpTo(Screen.LoginScreen.route) { inclusive = true }
                    }
                }
                is SignupUiEvent.ShowErrorMessage -> {
                    Toast.makeText(context,event.message, Toast.LENGTH_LONG).show()
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
                value = email,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                isError = emailError != null,
                label = { Text(stringResource(id = R.string.email)) },
                placeholder = { Text(stringResource(id = R.string.email)) },
                onValueChange = { viewModel.onEvent(SignupEvent.EnteredEmail(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(EMAIL_TF)
            )

            if(emailError != null) {
                ErrorTextFieldItem(
                    errorMessage = emailError,
                    testTag = EMAIL_ERROR_TF
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = password,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                isError = passwordError != null,
                label = { Text(stringResource(id = R.string.password)) },
                placeholder = { Text(stringResource(id = R.string.password)) },
                onValueChange = { viewModel.onEvent(SignupEvent.EnteredPassword(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(PASSWORD_TF)
            )

            if(passwordError != null) {
                ErrorTextFieldItem(
                    errorMessage = passwordError,
                    testTag = PASSWORD_ERROR_TF
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = confirmPassword,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                isError = confirmPasswordError != null,
                label = { Text(stringResource(id = R.string.confirm_password)) },
                placeholder = { Text(stringResource(id = R.string.confirm_password)) },
                onValueChange = { viewModel.onEvent(SignupEvent.EnteredConfirmPassword(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(CONFIRM_PASSWORD_TF)
            )

            if(confirmPasswordError != null) {
                ErrorTextFieldItem(
                    errorMessage = confirmPasswordError,
                    testTag = CONFIRM_PASSWORD_ERROR_TF
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = firstName,
                singleLine = true,
                isError = firstNameError != null,
                label = { Text(stringResource(id = R.string.first_name)) },
                placeholder = { Text(stringResource(id = R.string.first_name)) },
                onValueChange = { viewModel.onEvent(SignupEvent.EnteredFirstName(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(FIRST_NAME_TF)
            )

            if(firstNameError != null) {
                ErrorTextFieldItem(
                    errorMessage = firstNameError,
                    testTag = FIRST_NAME_ERROR_TF
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = lastName,
                singleLine = true,
                isError = lastNameError != null,
                label = { Text(stringResource(id = R.string.last_name)) },
                placeholder = { Text(stringResource(id = R.string.last_name)) },
                onValueChange = { viewModel.onEvent(SignupEvent.EnteredLastName(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(LAST_NAME_TF)
            )

            if(lastNameError != null) {
                ErrorTextFieldItem(
                    errorMessage = lastNameError,
                    testTag = LAST_NAME_ERROR_TF
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.Black),
                onClick = {
                    galleryLauncher.launch(DEVICE_IMAGES)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.choose_picture),
                    color = Color.White,
                    modifier = Modifier.padding(7.dp)
                )
            }
        }

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .testTag(SIGNUP_BUTTON),
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