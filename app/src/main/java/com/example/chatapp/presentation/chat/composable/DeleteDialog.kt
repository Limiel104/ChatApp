package com.example.chatapp.presentation.chat.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatapp.R
import androidx.compose.ui.window.Dialog
import com.example.chatapp.presentation.common.composable.DialogButton
import com.example.chatapp.ui.theme.ChatAppTheme
import com.example.chatapp.util.Constants.DIALOG_DELETE_BUTTON
import com.example.chatapp.util.Constants.DIALOG_DISMISS_BUTTON

@Composable
fun DeleteDialog(
    onDelete: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.dialog_title),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(id = R.string.dialog_text),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light
                )

                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    DialogButton(
                        text = stringResource(id = R.string.delete),
                        testTag = DIALOG_DELETE_BUTTON,
                        onClick = { onDelete() }
                    )

                    DialogButton(
                        text = stringResource(id = R.string.dismiss),
                        testTag = DIALOG_DISMISS_BUTTON,
                        onClick = { onDismiss() }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun DeleteDialogPreview() {
    ChatAppTheme() {
        DeleteDialog(
            onDelete = {},
            onDismiss = {}
        )
    }
}