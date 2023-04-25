package com.example.chatapp.presentation.user_profile.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.chatapp.R
import com.example.chatapp.presentation.common.composable.ErrorTextFieldItem
import com.example.chatapp.util.Constants

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