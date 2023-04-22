package com.example.chatapp.presentation.user_profile.composable

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import com.example.chatapp.R
import com.example.chatapp.presentation.common.composable.ErrorTextFieldItem
import com.example.chatapp.presentation.common.composable.ProfilePicture
import com.example.chatapp.presentation.user_profile.UserProfileEvent
import com.example.chatapp.presentation.user_profile.UserProfileUiEvent
import com.example.chatapp.presentation.user_profile.UserProfileViewModel
import com.example.chatapp.util.Constants
import kotlinx.coroutines.flow.collectLatest

@Composable
fun UserProfileScreen(
    navController: NavController,
    viewModel: UserProfileViewModel = hiltViewModel()
) {
    val isEditSectionVisible = viewModel.userProfileState.value.isEditSectionVisible
    val name = viewModel.userProfileState.value.name
    val firstName = viewModel.userProfileState.value.firstName
    val firstNameError = viewModel.userProfileState.value.firstNameError
    val lastName = viewModel.userProfileState.value.lastName
    val lastNameError = viewModel.userProfileState.value.lastNameError
    val profilePictureUrl = viewModel.userProfileState.value.profilePictureUrl
    val wasProfilePictureChanged = viewModel.userProfileState.value.wasProfilePictureChanged

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
                is UserProfileUiEvent.SaveNewProfilePicture -> {
                    navController.popBackStack()
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
            ProfilePicture(
                size = 150,
                profilePictureUrl = profilePictureUrl,
                isClickable = true,
                onClick = { galleryLauncher.launch(Constants.DEVICE_IMAGES) }
            )

            Text(
                text = name,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }

        Column(
            modifier = Modifier
                .weight(1F)
        ) {
            if (isEditSectionVisible) {
                OutlinedTextField(
                    value = firstName,
                    singleLine = true,
                    isError = firstNameError != null,
                    label = { Text(stringResource(id = R.string.first_name)) },
                    placeholder = { Text(stringResource(id = R.string.first_name)) },
                    onValueChange = { viewModel.onEvent(UserProfileEvent.EnteredFirstName(it)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(Constants.FIRST_NAME_TF)
                )

                if(firstNameError != null) {
                    ErrorTextFieldItem(
                        errorMessage = firstNameError,
                        testTag = Constants.FIRST_NAME_ERROR_TF
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = lastName,
                    singleLine = true,
                    label = { Text(stringResource(id = R.string.last_name)) },
                    placeholder = { Text(stringResource(id = R.string.last_name)) },
                    onValueChange = { viewModel.onEvent(UserProfileEvent.EnteredLastName(it)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(Constants.LAST_NAME_TF)
                )

                if(lastNameError != null) {
                    ErrorTextFieldItem(
                        errorMessage = lastNameError,
                        testTag = Constants.LAST_NAME_ERROR_TF
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))

                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(Color.Black),
                    onClick = { viewModel.onEvent(UserProfileEvent.Save) }
                ) {
                    Text(
                        text = stringResource(id = R.string.save),
                        color = Color.White,
                        modifier = Modifier.padding(7.dp)
                    )
                }
            }
            else if(wasProfilePictureChanged) {
                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(Color.Black),
                    onClick = { viewModel.onEvent(UserProfileEvent.SaveNewProfilePicture) }
                ) {
                    Text(
                        text = stringResource(id = R.string.save),
                        color = Color.White,
                        modifier = Modifier.padding(7.dp)
                    )
                }
            }
            else {
                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(Color.Black),
                    onClick = { viewModel.onEvent(UserProfileEvent.EditSectionVisibilityChange) }
                ) {
                    Text(
                        text = stringResource(id = R.string.edit_profile_info),
                        color = Color.White,
                        modifier = Modifier.padding(7.dp)
                    )
                }
            }
        }
    }
}