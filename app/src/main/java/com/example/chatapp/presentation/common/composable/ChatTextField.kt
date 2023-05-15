package com.example.chatapp.presentation.common.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.chatapp.ui.theme.ChatAppTheme
import com.example.chatapp.util.Constants.emptyString

@Composable
fun ChatTextField(
    text: String,
    label: String,
    placeholder: String,
    testTag: String,
    isError: Boolean,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        value = text,
        singleLine = true,
        isError = isError,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        onValueChange = { onValueChange(it) },
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxWidth()
            .testTag(testTag)
    )
}

@Composable
@Preview
fun ChatTextFieldPreview() {
    ChatAppTheme {
        ChatTextField(
            text = emptyString,
            label = "Text",
            placeholder = "Placeholder",
            testTag = "testTag",
            isError = false,
            onValueChange = {}
        )
    }
}

@Composable
@Preview
fun ChatTextFieldWithErrorPreview() {
    ChatAppTheme {
        ChatTextField(
            text = emptyString,
            label = "Text",
            placeholder = "Placeholder",
            testTag = "testTag",
            isError = true,
            onValueChange = {}
        )
    }
}