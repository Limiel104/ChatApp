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
import com.example.chatapp.presentation.common.composable.ErrorTextFieldItem
import com.example.chatapp.ui.theme.ChatAppTheme
import com.example.chatapp.util.Constants
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
        OutlinedTextField(
            value = email,
            singleLine = true,
            isError = emailError != null,
            label = { Text(stringResource(id = R.string.email)) },
            placeholder = { Text(stringResource(id = R.string.email)) },
            onValueChange = { onValueChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(Constants.FIRST_NAME_TF)
        )

        if(emailError != null) {
            ErrorTextFieldItem(
                errorMessage = emailError,
                testTag = Constants.FIRST_NAME_ERROR_TF
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