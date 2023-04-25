package com.example.chatapp.presentation.user_profile.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.chatapp.R
import com.example.chatapp.presentation.common.composable.ErrorTextFieldItem
import com.example.chatapp.util.Constants

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
        colors = ButtonDefaults.buttonColors(Color.Black),
        onClick = { onClick() }
    ) {
        Text(
            text = stringResource(id = R.string.save),
            color = Color.White,
            modifier = Modifier.padding(7.dp)
        )
    }
}