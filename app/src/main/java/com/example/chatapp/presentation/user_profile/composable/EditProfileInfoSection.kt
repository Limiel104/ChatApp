package com.example.chatapp.presentation.user_profile.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chatapp.R
import com.example.chatapp.presentation.common.composable.ChatButton
import com.example.chatapp.presentation.common.composable.ChatTextField
import com.example.chatapp.presentation.common.composable.ErrorTextFieldItem
import com.example.chatapp.ui.theme.ChatAppTheme
import com.example.chatapp.util.Constants.EDIT_PROFILE_INFO_SAVE_BUTTON
import com.example.chatapp.util.Constants.FIRST_NAME_ERROR_TF
import com.example.chatapp.util.Constants.FIRST_NAME_TF
import com.example.chatapp.util.Constants.LAST_NAME_ERROR_TF
import com.example.chatapp.util.Constants.LAST_NAME_TF
import com.example.chatapp.util.Constants.firstNameCorrect
import com.example.chatapp.util.Constants.lastNameCorrect

@Composable
fun EditProfileInfoSection(
    firstName: String,
    firstNameError: String?,
    lastName: String,
    lastNameError: String?,
    onFirstNameValueChange: (String) -> Unit,
    onLastNameValueChange: (String) -> Unit,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
    ) {
        ChatTextField(
            text = firstName,
            label = stringResource(id = R.string.first_name),
            placeholder = stringResource(id = R.string.first_name),
            testTag = FIRST_NAME_TF,
            isError = firstNameError != null,
            onValueChange = { onFirstNameValueChange(it) }
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
            onValueChange = { onLastNameValueChange(it) }
        )

        if(lastNameError != null) {
            ErrorTextFieldItem(
                errorMessage = lastNameError,
                testTag = LAST_NAME_ERROR_TF
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        ChatButton(
            text = stringResource(id = R.string.save),
            testTag = EDIT_PROFILE_INFO_SAVE_BUTTON,
            onClick = { onClick() }
        )
    }
}

@Preview
@Composable
fun EditProfileInfoSection() {
    ChatAppTheme() {
        EditProfileInfoSection(
            firstName = firstNameCorrect,
            firstNameError = null,
            lastName = lastNameCorrect,
            lastNameError = null,
            onFirstNameValueChange = {},
            onLastNameValueChange = {},
            onClick = {}
        )
    }
}