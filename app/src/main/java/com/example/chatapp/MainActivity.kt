package com.example.chatapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.chatapp.ui.theme.ChatAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    UserListScreen()
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginScreen() {

    val login = ""
    val password = ""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Welcome back!",
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
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Don't have an account?"
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = "Sign up",
                color = Color.Blue,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .clickable { /*TODO*/ }
            )
        }
    }
}

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
                label = { Text("Password") },
                placeholder = { Text("Password") },
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

@Preview
@Composable
fun UserListItem(
    name: String = "John Smith"
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(5.dp, 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier
                .size(50.dp),
            shape = RoundedCornerShape(35.dp),
            backgroundColor = Color.LightGray
        ) {
            AsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(R.drawable.ic_person)
                    .build(),
                contentDescription = "User image",
                fallback = painterResource(R.drawable.ic_person),
                error = painterResource(R.drawable.ic_person),
            )
        }

        Column(
            modifier = Modifier
                .padding(start = 20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = name,
                    fontWeight = FontWeight.SemiBold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )

                Text(
                    text = "18 Apr",
                    fontWeight = FontWeight.Light,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    fontSize = 13.sp,
                    modifier = Modifier
                        .padding(end = 4.dp)
                )
            }

            Text(
                text = "Last message dsfuidsgifusgfuisdgiusgfuisdgfsidugfsiudgfisudgisugfisudgfsiugfis",
                fontWeight = FontWeight.Light,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                fontSize = 13.sp
            )
        }
    }
}

@Preview
@Composable
fun UserListScreen() {

    val userList = listOf(
        "John Smith",
        "Madeline Holding ",
        "Stanley Hall",
        "Hannah Porter",
        "Agnes Turner",
        "Veronica Hall",
        "Thomas White",
        "Ian Park",
        "Taylor Cole",
        "Blair Vinson",
        "Brook Glover",
        "Alex Mckay"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            itemsIndexed(userList) { index, string ->
                UserListItem(string)
            }
        }
    }
}