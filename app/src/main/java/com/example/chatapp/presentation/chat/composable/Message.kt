package com.example.chatapp.presentation.chat.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatapp.ui.theme.ChatAppTheme

@Composable
fun Message(
    text: String,
    isSendByCurrentUser: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = if (isSendByCurrentUser) Arrangement.End else Arrangement.Start
    ) {
        Card(
            modifier = Modifier
                .padding(5.dp, 6.dp)
                .clickable {
                    if(isSendByCurrentUser) {
                        onClick()
                    }
                }
                .padding(
                    if (isSendByCurrentUser) PaddingValues(start = 40.dp) else PaddingValues(end = 40.dp)
                ),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(
                text = text,
                fontSize = 14.sp,
                color = MaterialTheme.colors.secondaryVariant,
                modifier = Modifier
                    .background(if (isSendByCurrentUser) MaterialTheme.colors.primary else MaterialTheme.colors.onPrimary)
                    .padding(7.dp)
            )
        }
    }
}

@Preview
@Composable
fun MessageFromCurrentUserPreview() {
    ChatAppTheme() {
        Message(
            text = "new message",
            isSendByCurrentUser = true,
            onClick = {}
        )
    }
}

@Preview
@Composable
fun MessageToCurrentUserPreview() {
    ChatAppTheme() {
        Message(
            text = "new message",
            isSendByCurrentUser = false,
            onClick = {}
        )
    }
}