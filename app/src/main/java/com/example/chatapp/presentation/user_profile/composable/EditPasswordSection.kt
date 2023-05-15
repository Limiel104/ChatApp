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
import com.example.chatapp.util.Constants.CONFIRM_PASSWORD_ERROR_TF
import com.example.chatapp.util.Constants.CONFIRM_PASSWORD_TF
import com.example.chatapp.util.Constants.EDIT_PASSWORD_SAVE_BUTTON
import com.example.chatapp.util.Constants.PASSWORD_ERROR_TF
import com.example.chatapp.util.Constants.PASSWORD_TF
import com.example.chatapp.util.Constants.confirmPasswordCorrect
import com.example.chatapp.util.Constants.passwordCorrect

@Composable
fun EditPasswordSection(
    password: String,
    passwordError: String?,
    confirmPassword: String,
    confirmPasswordError: String?,
    onPasswordValueChange: (String) -> Unit,
    onConfirmPasswordValueChange: (String) -> Unit,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
    ) {
        ChatTextField(
            text = password,
            label = stringResource(id = R.string.password),
            placeholder = stringResource(id = R.string.password),
            testTag = PASSWORD_TF,
            isError = passwordError != null,
            onValueChange = { onPasswordValueChange(it) }
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
            onValueChange = { onConfirmPasswordValueChange(it) }
        )

        if(confirmPasswordError != null) {
            ErrorTextFieldItem(
                errorMessage = confirmPasswordError,
                testTag = CONFIRM_PASSWORD_ERROR_TF
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        ChatButton(
            text = stringResource(id = R.string.save),
            testTag = EDIT_PASSWORD_SAVE_BUTTON,
            onClick = { onClick() }
        )
    }
}

@Preview
@Composable
fun EditPasswordSection() {
    ChatAppTheme() {
        EditPasswordSection(
            password = passwordCorrect,
            passwordError = null,
            confirmPassword = confirmPasswordCorrect,
            confirmPasswordError = null,
            onPasswordValueChange = {},
            onConfirmPasswordValueChange = {},
            onClick = {}
        )
    }
}