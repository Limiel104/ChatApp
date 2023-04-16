package com.example.chatapp.presentation.chat.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Message(
    text: String,
    isSendByCurrentUser: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = if (isSendByCurrentUser) Arrangement.End else Arrangement.Start
    ) {
        Card(
            modifier = Modifier
                .padding(5.dp, 6.dp)
                .padding(if (isSendByCurrentUser) PaddingValues(start = 40.dp) else PaddingValues(end = 40.dp)),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(
                text = text,
                fontSize = 14.sp,
                modifier = Modifier
                    .background(if (isSendByCurrentUser) Color.Yellow else Color.LightGray)
                    .padding(7.dp)
            )
        }
    }
}