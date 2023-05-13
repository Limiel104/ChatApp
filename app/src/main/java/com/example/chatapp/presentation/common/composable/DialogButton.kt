package com.example.chatapp.presentation.common.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chatapp.ui.theme.ChatAppTheme

@Composable
fun DialogButton(
    text: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        colors = ButtonDefaults.buttonColors(Color.White),
        border = BorderStroke(1.dp, Color.Black),
        shape = RoundedCornerShape(35.dp),
        onClick = onClick
    ) {
        Text(
            text = text,
            color = Color.Black,
            modifier = Modifier
                .padding(7.dp)
        )
    }
}

@Preview
@Composable
fun DialogButtonPreview() {
    ChatAppTheme() {
        DialogButton(
            text = "Text",
            onClick = {}
        )
    }
}