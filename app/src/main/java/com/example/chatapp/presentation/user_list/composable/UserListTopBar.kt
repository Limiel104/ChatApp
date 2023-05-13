package com.example.chatapp.presentation.user_list.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.chatapp.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatapp.presentation.common.composable.ProfilePicture
import com.example.chatapp.ui.theme.ChatAppTheme
import com.example.chatapp.util.Constants.Logout
import com.example.chatapp.util.Constants.Search

@Composable
fun UserListTopBar(
    query: String,
    profilePictureUrl: String,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit,
    onImageClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = { onValueChange(it) },
            textStyle = TextStyle(fontSize = 13.sp),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search),
                    fontSize = 13.sp
                )
            },
            maxLines = 1,
            singleLine = true,
            shape = RoundedCornerShape(30.dp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = Search
                )
            },
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
        )

        Spacer(modifier = Modifier.width(10.dp))

        ProfilePicture(
            size = 40,
            profilePictureUrl = profilePictureUrl,
            isClickable = true,
            onClick = { onImageClick() }
        )

        Spacer(modifier = Modifier.width(10.dp))

        IconButton(
            onClick = { onClick() }
        ) {
            Icon(
                imageVector = Icons.Default.Logout,
                contentDescription = Logout
            )
        }
    }
}

@Preview
@Composable
fun UserListTopBarPreview() {
    ChatAppTheme() {
        UserListTopBar(
            query = "",
            profilePictureUrl = "",
            onValueChange = {},
            onClick = {},
            onImageClick = {}
        )
    }
}