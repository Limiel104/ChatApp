package com.example.chatapp.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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