package com.example.chatapp.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun SignUpScreen() {

    val login = ""
    val password = ""
    val confirmPassword = ""
    val firstName = ""
    val lastName = ""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Create an account",
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold
        )

        Column(
        ) {
            OutlinedTextField(
                value = login,
                singleLine = true,
                label = { Text("Email") },
                placeholder = { Text("Email") },
                onValueChange = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = password,
                singleLine = true,
                label = { Text("Password") },
                placeholder = { Text("Password") },
                onValueChange = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = confirmPassword,
                singleLine = true,
                label = { Text("Confirm Password") },
                placeholder = { Text("Confirm Password") },
                onValueChange = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = firstName,
                singleLine = true,
                label = { Text("First Name") },
                placeholder = { Text("First Name") },
                onValueChange = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = lastName,
                singleLine = true,
                label = { Text("Last Name") },
                placeholder = { Text("Last Name") },
                onValueChange = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color.Black),
            onClick = { /*TODO*/ }
        ) {
            Text(
                text = "Sign up",
                color = Color.White,
                modifier = Modifier.padding(7.dp)
            )
        }
    }
}