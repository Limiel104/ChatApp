package com.example.chatapp.presentation.user_profile.composable

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import com.example.chatapp.presentation.user_profile.UserProfileEvent
import com.example.chatapp.presentation.user_profile.UserProfileUiEvent
import com.example.chatapp.presentation.user_profile.UserProfileViewModel
import com.example.chatapp.util.Constants
import com.example.chatapp.util.Resource
import kotlinx.coroutines.flow.collectLatest

@Composable
fun UserProfileScreen(
    navController: NavController,
    viewModel: UserProfileViewModel = hiltViewModel()
) {
    val name = viewModel.userProfileState.value.name
    val firstName = viewModel.userProfileState.value.firstName
    val firstNameError = viewModel.userProfileState.value.firstNameError
    val lastName = viewModel.userProfileState.value.lastName
    val lastNameError = viewModel.userProfileState.value.lastNameError
    val email = viewModel.userProfileState.value.email
    val emailError = viewModel.userProfileState.value.emailError
    val password = viewModel.userProfileState.value.password
    val passwordError = viewModel.userProfileState.value.passwordError
    val confirmPassword = viewModel.userProfileState.value.confirmPassword
    val confirmPasswordError = viewModel.userProfileState.value.confirmPasswordError
    val profilePictureUrl = viewModel.userProfileState.value.profilePictureUrl
    val wasProfilePictureChanged = viewModel.userProfileState.value.wasProfilePictureChanged
    val isEditProfileInfoVisible = viewModel.userProfileState.value.isEditProfileInfoVisible
    val isEditEmailVisible = viewModel.userProfileState.value.isEditEmailVisible
    val isEditPasswordVisible = viewModel.userProfileState.value.isEditPasswordVisible

    val isLoading = viewModel.userProfileState.value.updateUserResponse == Resource.Loading ||
            viewModel.userProfileState.value.updateProfilePictureResponse == Resource.Loading
    val context = LocalContext.current

    val imageCropLauncher = rememberLauncherForActivityResult(CropImageContract()) { result ->
        viewModel.onEvent(UserProfileEvent.SelectedProfilePicture(result.uriContent))
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
                is UserProfileUiEvent.Save -> {
                    navController.popBackStack()
                }
                is UserProfileUiEvent.ShowErrorMessage -> {
                    Toast.makeText(context,event.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 10.dp)
            .padding(top = 60.dp, bottom = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UserProfile(
                profilePictureUrl = profilePictureUrl,
                name = name,
                onClick = { galleryLauncher.launch(Constants.DEVICE_IMAGES) }
            )
        }

        Column(
            modifier = Modifier
                .weight(1F)
        ) {
            if (isEditProfileInfoVisible) {
                EditProfileInfoSection(
                    firstName = firstName,
                    firstNameError = firstNameError,
                    lastName = lastName,
                    lastNameError = lastNameError,
                    onFirstNameValueChange = { viewModel.onEvent(UserProfileEvent.EnteredFirstName(it)) },
                    onLastNameValueChange = { viewModel.onEvent(UserProfileEvent.EnteredLastName(it)) },
                    onClick = { viewModel.onEvent(UserProfileEvent.Save) }
                )
            }
            else if(isEditEmailVisible) {
                EditEmailSection(
                    email = email,
                    emailError = emailError,
                    onValueChange = { viewModel.onEvent(UserProfileEvent.EnteredEmail(it)) },
                    onClick = { viewModel.onEvent(UserProfileEvent.Save) }
                )
            }
            else if(isEditPasswordVisible) {
                EditPasswordSection(
                    password = password,
                    passwordError = passwordError,
                    confirmPassword = confirmPassword,
                    confirmPasswordError = confirmPasswordError,
                    onPasswordValueChange = { viewModel.onEvent(UserProfileEvent.EnteredPassword(it)) },
                    onConfirmPasswordValueChange = { viewModel.onEvent(UserProfileEvent.EnteredConfirmPassword(it)) },
                    onClick = { viewModel.onEvent(UserProfileEvent.Save) }
                )
            }
            else if(wasProfilePictureChanged) {
                EditProfilePictureSection(
                    onClick = { viewModel.onEvent(UserProfileEvent.Save) }
                )
            }
            else {
                EditSectionOptions(
                    onEditProfileInfoClick = { viewModel.onEvent(UserProfileEvent.EditProfileInfoVisibilityChange) },
                    onEditEmailClick = { viewModel.onEvent(UserProfileEvent.EditEmailVisibilityChange) },
                    onEditPasswordClick = { viewModel.onEvent(UserProfileEvent.EditPasswordVisibilityChange) }
                )
            }
        }
    }

    if(isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .testTag(Constants.CIRCULAR_INDICATOR),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}