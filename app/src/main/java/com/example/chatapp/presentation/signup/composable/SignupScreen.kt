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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import com.example.chatapp.presentation.signup.SignupViewModel
import com.example.chatapp.R
import com.example.chatapp.presentation.common.composable.ChatButton
import com.example.chatapp.presentation.common.composable.ChatTextField
import com.example.chatapp.presentation.common.composable.ErrorTextFieldItem
import com.example.chatapp.presentation.signup.SignupEvent
import com.example.chatapp.presentation.signup.SignupUiEvent
import com.example.chatapp.util.Constants.CHOOSE_PROFILE_PICTURE_BUTTON
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

    val imageCropLauncher = rememberLauncherForActivityResult(CropImageContract()) { result ->
        viewModel.onEvent(SignupEvent.SelectedProfilePicture(result.uriContent))
    }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { imageUri ->
        imageUri?.let {
            val cropImageOptions = CropImageOptions(
                cropShape = CropImageView.CropShape.OVAL,
                maxCropResultWidth = 1500,
                maxCropResultHeight = 1500,
                fixAspectRatio = true
            )
            val cropOptions = CropImageContractOptions(imageUri, cropImageOptions)
            imageCropLauncher.launch(cropOptions)
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is SignupUiEvent.Signup -> {
                    Log.i("TAG", "Signup successfully")
                    navController.navigate(Screen.UserListScreen.route) {
                        popUpTo(Screen.LoginScreen.route) { inclusive = true }
                    }
                }
                is SignupUiEvent.ShowErrorMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
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
            text = stringResource(id = R.string.create_an_account),
            fontSize = 32.sp,
            color = MaterialTheme.colors.secondary,
            fontWeight = FontWeight.SemiBold
        )

        Column() {
            ChatTextField(
                text = email,
                label = stringResource(id = R.string.email),
                placeholder = stringResource(id = R.string.email),
                testTag = EMAIL_TF,
                isError = emailError != null,
                onValueChange = { viewModel.onEvent(SignupEvent.EnteredEmail(it)) },
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
                onValueChange = { viewModel.onEvent(SignupEvent.EnteredPassword(it)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                )
            )

            if(passwordError != null) {
                ErrorTextFieldItem(
                    errorMessage = passwordError,
                    testTag = PASSWORD_ERROR_TF
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            ChatTextField(
                text = confirmPassword,
                label = stringResource(id = R.string.confirm_password),
                placeholder = stringResource(id = R.string.confirm_password),
                testTag = CONFIRM_PASSWORD_TF,
                isError = confirmPasswordError != null,
                onValueChange = { viewModel.onEvent(SignupEvent.EnteredConfirmPassword(it)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                )
            )

            if(confirmPasswordError != null) {
                ErrorTextFieldItem(
                    errorMessage = confirmPasswordError,
                    testTag = CONFIRM_PASSWORD_ERROR_TF
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            ChatTextField(
                text = firstName,
                label = stringResource(id = R.string.first_name),
                placeholder = stringResource(id = R.string.first_name),
                testTag = FIRST_NAME_TF,
                isError = firstNameError != null,
                onValueChange = { viewModel.onEvent(SignupEvent.EnteredFirstName(it)) }
            )

            if(firstNameError != null) {
                ErrorTextFieldItem(
                    errorMessage = firstNameError,
                    testTag = FIRST_NAME_ERROR_TF
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            ChatTextField(
                text = lastName,
                label = stringResource(id = R.string.last_name),
                placeholder = stringResource(id = R.string.last_name),
                testTag = LAST_NAME_TF,
                isError = lastNameError != null,
                onValueChange = { viewModel.onEvent(SignupEvent.EnteredLastName(it)) }
            )

            if(lastNameError != null) {
                ErrorTextFieldItem(
                    errorMessage = lastNameError,
                    testTag = LAST_NAME_ERROR_TF
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            ChatButton(
                text = stringResource(id = R.string.choose_picture),
                testTag = CHOOSE_PROFILE_PICTURE_BUTTON,
                onClick = { galleryLauncher.launch(DEVICE_IMAGES) }
            )

            Spacer(modifier = Modifier.height(10.dp))

            ChatButton(
                text = stringResource(id = R.string.signup),
                testTag = SIGNUP_BUTTON,
                onClick = { viewModel.onEvent(SignupEvent.Signup) }
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