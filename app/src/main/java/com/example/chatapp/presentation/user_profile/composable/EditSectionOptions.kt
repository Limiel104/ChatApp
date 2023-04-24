package com.example.chatapp.presentation.user_profile.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.chatapp.R

@Composable
fun EditSectionOptions(
    onEditProfileInfoClick: () -> Unit,
    onEditEmailClick: () -> Unit,
) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(Color.Black),
        onClick = { onEditProfileInfoClick() }
    ) {
        Text(
            text = stringResource(id = R.string.edit_profile_info),
            color = Color.White,
            modifier = Modifier.padding(7.dp)
        )
    }

    Spacer(modifier = Modifier.height(15.dp))

    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(Color.Black),
        onClick = { onEditEmailClick() }
    ) {
        Text(
            text = stringResource(id = R.string.edit_email),
            color = Color.White,
            modifier = Modifier.padding(7.dp)
        )
    }
}