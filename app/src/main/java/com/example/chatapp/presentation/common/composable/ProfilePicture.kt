package com.example.chatapp.presentation.common.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.chatapp.R
import com.example.chatapp.ui.theme.ChatAppTheme
import com.example.chatapp.util.Constants

@Composable
fun ProfilePicture(
    size: Int,
    profilePictureUrl: String,
    isClickable: Boolean,
    onClick: () -> Unit
) {
    if(isClickable) {
        Card(
            modifier = Modifier
                .size(size.dp)
                .clip(shape = CircleShape)
                .clickable { onClick() },
            shape = CircleShape,
            backgroundColor = MaterialTheme.colors.onPrimary
        ) {
            AsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(profilePictureUrl)
                    .crossfade(true)
                    .placeholder(R.drawable.ic_person)
                    .build(),
                contentDescription = Constants.UserImage,
                fallback = painterResource(R.drawable.ic_person),
                error = painterResource(R.drawable.ic_person)
            )
        }
    }
    else {
        Card(
            modifier = Modifier
                .size(size.dp),
            shape = RoundedCornerShape(size.dp),
            backgroundColor = MaterialTheme.colors.onPrimary
        ) {
            AsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(profilePictureUrl)
                    .crossfade(true)
                    .placeholder(R.drawable.ic_person)
                    .build(),
                contentDescription = Constants.UserImage,
                fallback = painterResource(R.drawable.ic_person),
                error = painterResource(R.drawable.ic_person)
            )
        }
    }
}

@Preview
@Composable
fun ProfilePicturePreview() {
    ChatAppTheme() {
        ProfilePicture(
            size = 40,
            profilePictureUrl = "url",
            isClickable = true,
            onClick = {}
        )
    }
}