package com.example.chatapp.presentation.common.composable

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun ErrorTextFieldItem(
    errorMessage: String,
    testTag: String
) {
    Text(
        text = errorMessage,
        fontSize = 13.sp,
        color = MaterialTheme.colors.error,
        textAlign = TextAlign.Start,
        modifier = Modifier
            .testTag(testTag)
    )
}