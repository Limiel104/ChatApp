package com.example.chatapp.presentation.user_profile.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chatapp.R
import com.example.chatapp.ui.theme.ChatAppTheme

@Composable
fun EditSectionOptions(
    onEditProfileInfoClick: () -> Unit,
    onEditEmailClick: () -> Unit,
    onEditPasswordClick: () -> Unit
) {
    Column() {
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
            onClick = { onEditProfileInfoClick() }
        ) {
            Text(
                text = stringResource(id = R.string.edit_profile_info),
                color = MaterialTheme.colors.onSecondary,
                modifier = Modifier.padding(7.dp)
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
            onClick = { onEditEmailClick() }
        ) {
            Text(
                text = stringResource(id = R.string.edit_email),
                color = MaterialTheme.colors.onSecondary,
                modifier = Modifier.padding(7.dp)
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
            onClick = { onEditPasswordClick() }
        ) {
            Text(
                text = stringResource(id = R.string.edit_password),
                color = MaterialTheme.colors.onSecondary,
                modifier = Modifier.padding(7.dp)
            )
        }
    }
}

@Preview
@Composable
fun EditSectionOptionsPreview() {
    ChatAppTheme() {
        EditSectionOptions(
            onEditProfileInfoClick = {},
            onEditEmailClick = {},
            onEditPasswordClick = {}
        )
    }
}