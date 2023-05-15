package com.example.chatapp.presentation.user_profile.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.chatapp.R
import com.example.chatapp.presentation.common.composable.ChatButton
import com.example.chatapp.ui.theme.ChatAppTheme
import com.example.chatapp.util.Constants.EDIT_PROFILE_PICTURE_SAVE_BUTTON

@Composable
fun EditProfilePictureSection(
    onClick: () -> Unit
) {
    ChatButton(
        text = stringResource(id = R.string.save),
        testTag = EDIT_PROFILE_PICTURE_SAVE_BUTTON,
        onClick = { onClick() }
    )
}

@Preview
@Composable
fun EditProfilePictureSectionPreview() {
    ChatAppTheme() {
        EditProfilePictureSection(
            onClick = {}
        )
    }
}