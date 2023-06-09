package com.example.chatapp.presentation.common.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chatapp.ui.theme.ChatAppTheme

@Composable
fun DialogButton(
    text: String,
    testTag: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = Modifier
            .testTag(testTag),
        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
        shape = RoundedCornerShape(35.dp),
        onClick = { onClick() }
    ) {
        Text(
            text = text,
            color = MaterialTheme.colors.onSecondary,
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
            testTag = "testTag",
            onClick = {}
        )
    }
}