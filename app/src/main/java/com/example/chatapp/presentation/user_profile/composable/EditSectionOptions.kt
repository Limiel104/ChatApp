package com.example.chatapp.presentation.user_profile.composable

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chatapp.R
import com.example.chatapp.presentation.common.composable.ChatButton
import com.example.chatapp.ui.theme.ChatAppTheme
import com.example.chatapp.util.Constants.EDIT_EMAIL_BUTTON
import com.example.chatapp.util.Constants.EDIT_PASSWORD_BUTTON
import com.example.chatapp.util.Constants.EDIT_PROFILE_INFO_BUTTON

@Composable
fun EditSectionOptions(
    onEditProfileInfoClick: () -> Unit,
    onEditEmailClick: () -> Unit,
    onEditPasswordClick: () -> Unit
) {
    Column() {
        ChatButton(
            text = stringResource(id = R.string.edit_profile_info),
            testTag = EDIT_PROFILE_INFO_BUTTON,
            onClick = { onEditProfileInfoClick() }
        )

        Spacer(modifier = Modifier.height(15.dp))

        ChatButton(
            text = stringResource(id = R.string.edit_email),
            testTag = EDIT_EMAIL_BUTTON,
            onClick = { onEditEmailClick() }
        )

        Spacer(modifier = Modifier.height(15.dp))

        ChatButton(
            text = stringResource(id = R.string.edit_password),
            testTag = EDIT_PASSWORD_BUTTON,
            onClick = { onEditPasswordClick() }
        )
    }
}

@Preview
@Composable
fun EditSectionOptionsPreview() {
    ChatAppTheme() {
        EditSectionOptions(
            onEditProfileInfoClick = {},
            onEditEmailClick = {},
            onEditPasswordClick = {}
        )
    }
}