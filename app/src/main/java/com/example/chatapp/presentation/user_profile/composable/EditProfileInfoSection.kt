package com.example.chatapp.presentation.user_profile.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chatapp.R
import com.example.chatapp.presentation.common.composable.ChatButton
import com.example.chatapp.presentation.common.composable.ErrorTextFieldItem
import com.example.chatapp.ui.theme.ChatAppTheme
import com.example.chatapp.util.Constants
import com.example.chatapp.util.Constants.EDIT_PROFILE_INFO_SAVE_BUTTON
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
        OutlinedTextField(
            value = firstName,
            singleLine = true,
            isError = firstNameError != null,
            label = { Text(stringResource(id = R.string.first_name)) },
            placeholder = { Text(stringResource(id = R.string.first_name)) },
            onValueChange = { onFirstNameValueChange(it) },
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
            onValueChange = { onLastNameValueChange(it) },
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