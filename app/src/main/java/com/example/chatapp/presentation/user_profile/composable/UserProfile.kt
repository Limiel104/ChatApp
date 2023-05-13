package com.example.chatapp.presentation.user_profile.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.chatapp.presentation.common.composable.ProfilePicture
import com.example.chatapp.ui.theme.ChatAppTheme
import com.example.chatapp.util.Constants.firstNameCorrect
import com.example.chatapp.util.Constants.lastNameCorrect

@Composable
fun UserProfile(
    profilePictureUrl: String,
    name: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfilePicture(
            size = 150,
            profilePictureUrl = profilePictureUrl,
            isClickable = true,
            onClick = { onClick() }
        )

        Text(
            text = name,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Preview
@Composable
fun UserProfilePreview() {
    ChatAppTheme() {
        UserProfile(
            profilePictureUrl = "url",
            name = "$firstNameCorrect $lastNameCorrect",
            onClick = {}
        )
    }
}