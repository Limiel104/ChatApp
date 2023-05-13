package com.example.chatapp.presentation.chat.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chatapp.presentation.common.composable.ProfilePicture
import com.example.chatapp.ui.theme.ChatAppTheme
import com.example.chatapp.util.Constants.GoBack

@Composable
fun ChatTopBar(
    name: String,
    profilePictureUrl: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { onClick() }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = GoBack
            )
        }

        ProfilePicture(
            size = 40,
            profilePictureUrl = profilePictureUrl,
            isClickable = false,
            onClick = {}
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = name,
            fontWeight = FontWeight.SemiBold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Preview
@Composable
fun ChatTopBar() {
    ChatAppTheme() {
        ChatTopBar(
            name = "John Smith",
            profilePictureUrl = "url",
            onClick = {}
        )
    }
}