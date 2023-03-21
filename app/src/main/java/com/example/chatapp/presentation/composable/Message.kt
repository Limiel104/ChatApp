package com.example.chatapp.presentation.composable

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
    color: Color,
    padding: PaddingValues,
    arrangement: Arrangement.Horizontal
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = arrangement
    ) {
        Card(
            modifier = Modifier
                .padding(5.dp,6.dp)
                .padding(padding),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(
                text = text,
                fontSize = 14.sp,
                modifier = Modifier
                    .background(color)
                    .padding(7.dp)
            )
        }
    }
}