package com.example.chatapp.presentation.user_profile.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chatapp.R
import com.example.chatapp.presentation.common.composable.ErrorTextFieldItem
import com.example.chatapp.ui.theme.ChatAppTheme
import com.example.chatapp.util.Constants
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
        OutlinedTextField(
            value = password,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            isError = passwordError != null,
            label = { Text(stringResource(id = R.string.password)) },
            placeholder = { Text(stringResource(id = R.string.password)) },
            onValueChange = { onPasswordValueChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(Constants.PASSWORD_TF)
        )

        if(passwordError != null) {
            ErrorTextFieldItem(
                errorMessage = passwordError,
                testTag = Constants.PASSWORD_ERROR_TF
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
            onValueChange = { onConfirmPasswordValueChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(Constants.CONFIRM_PASSWORD_TF)
        )

        if(confirmPasswordError != null) {
            ErrorTextFieldItem(
                errorMessage = confirmPasswordError,
                testTag = Constants.CONFIRM_PASSWORD_ERROR_TF
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
            onClick = { onClick() }
        ) {
            Text(
                text = stringResource(id = R.string.save),
                color = MaterialTheme.colors.onSecondary,
                modifier = Modifier.padding(7.dp)
            )
        }
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