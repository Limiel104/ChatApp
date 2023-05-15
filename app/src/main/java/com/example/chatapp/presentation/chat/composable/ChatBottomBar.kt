package com.example.chatapp.presentation.chat.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.chatapp.R
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chatapp.ui.theme.ChatAppTheme
import com.example.chatapp.util.Constants.emptyString

@Composable
fun ChatBottomBar(
    message: String,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = message,
            shape = RoundedCornerShape(30.dp),
            trailingIcon = {
                IconButton(
                    onClick = { onClick() },
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send message"
                    )
                }
            },
            placeholder = { Text(stringResource(id = R.string.message_placeholder)) },
            onValueChange = { onValueChange(it) }
        )
    }
}

@Preview
@Composable
fun ChatBottomBarPreview() {
    ChatAppTheme() {
        ChatBottomBar(
            message = emptyString,
            onValueChange = {},
            onClick = {}
        )
    }
}