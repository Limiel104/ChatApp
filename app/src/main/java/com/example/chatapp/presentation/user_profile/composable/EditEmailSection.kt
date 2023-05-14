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
import com.example.chatapp.util.Constants.EDIT_EMAIL_SAVE_BUTTON
import com.example.chatapp.util.Constants.EMAIL_TF
import com.example.chatapp.util.Constants.FIRST_NAME_ERROR_TF
import com.example.chatapp.util.Constants.emailCorrect

@Composable
fun EditEmailSection(
    email: String,
    emailError: String?,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
    ) {
        ChatTextField(
            text = email,
            label = stringResource(id = R.string.email),
            placeholder = stringResource(id = R.string.email),
            testTag = EMAIL_TF,
            isError = emailError != null,
            onValueChange = { onValueChange(it) }
        )

        if(emailError != null) {
            ErrorTextFieldItem(
                errorMessage = emailError,
                testTag = FIRST_NAME_ERROR_TF
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        ChatButton(
            text = stringResource(id = R.string.save),
            testTag = EDIT_EMAIL_SAVE_BUTTON,
            onClick = { onClick() }
        )

    }
}

@Preview
@Composable
fun EditEmailSection() {
    ChatAppTheme() {
        EditEmailSection(
            email = emailCorrect,
            emailError = null,
            onValueChange = {},
            onClick = {}
        )
    }
}