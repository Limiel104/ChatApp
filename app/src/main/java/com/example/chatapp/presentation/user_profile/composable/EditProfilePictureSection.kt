package com.example.chatapp.presentation.user_profile.composable

import androidx.compose.foundation.layout.fillMaxWidth
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
fun EditProfilePictureSection(
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(Color.Black),
        onClick = { onClick() }
    ) {
        Text(
            text = stringResource(id = R.string.save),
            color = Color.White,
            modifier = Modifier.padding(7.dp)
        )
    }
}