package com.example.chatapp.presentation.user_list.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.example.chatapp.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.chatapp.util.Constants.Logout
import com.example.chatapp.util.Constants.Search
import com.example.chatapp.util.Constants.UserImage

@Composable
fun UserListTopBar(
    query: String,
    profilePictureUrl: String,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
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

        Card(
            modifier = Modifier
                .size(40.dp),
            shape = RoundedCornerShape(35.dp),
            backgroundColor = Color.LightGray
        ) {
            AsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(profilePictureUrl)
                    .crossfade(true)
                    .placeholder(R.drawable.ic_person)
                    .build(),
                contentDescription = UserImage,
                fallback = painterResource(R.drawable.ic_person),
                error = painterResource(R.drawable.ic_person)
            )
        }

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