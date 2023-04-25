package com.example.chatapp.presentation.user_profile.composable

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.example.chatapp.presentation.common.composable.ProfilePicture

@Composable
fun UserProfile(
    profilePictureUrl: String,
    name: String,
    onClick: () -> Unit
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