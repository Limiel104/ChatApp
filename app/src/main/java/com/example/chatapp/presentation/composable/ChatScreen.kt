package com.example.chatapp.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun ChatScreen() {
    Scaffold(
        topBar = { ChatTopBar("John Smith") },
        bottomBar = { ChatBottomBar() },
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Message(
                text = "Hello Amber",
                color = Color.LightGray,
                padding = PaddingValues(end = 40.dp),
                arrangement = Arrangement.Start
            )

            Message(
                text = "How are you?",
                color = Color.LightGray,
                padding = PaddingValues(end = 40.dp),
                arrangement = Arrangement.Start
            )

            Message(
                text = "Hello John",
                color = Color.Yellow,
                padding = PaddingValues(start = 40.dp),

                arrangement = Arrangement.End
            )

            Message(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut eros lorem, maximus ac aliquam at, eleifend in dui. Sed convallis hendrerit tincidunt. Suspendisse felis massa, tempus non venenatis quis, commodo eget sem. Praesent bibendum erat in ipsum sodales vulputate. Phasellus et diam aliquet, porta lacus vitae, placerat dui. Donec tempus orci semper lacus consectetur ultricies. Morbi consectetur feugiat nisi, at blandit ipsum lacinia eu. Donec eu lorem eu lorem rhoncus iaculis et vitae massa.",
                color = Color.Yellow,
                padding = PaddingValues(start = 40.dp),
                arrangement = Arrangement.End
            )

            Message(
                text = "Ok",
                color = Color.LightGray,
                padding = PaddingValues(end = 40.dp),
                arrangement = Arrangement.Start
            )

            Message(
                text = "Quisque eget mattis enim. Aenean ultrices leo nec metus tincidunt, eget aliquam nulla ullamcorper.",
                color = Color.Yellow,
                padding = PaddingValues(start = 40.dp),
                arrangement = Arrangement.End
            )

            Message(
                text = "Bye bye! Hope to see you soon!",
                color = Color.Yellow,
                padding = PaddingValues(start = 40.dp),
                arrangement = Arrangement.End
            )
        }
    }
}