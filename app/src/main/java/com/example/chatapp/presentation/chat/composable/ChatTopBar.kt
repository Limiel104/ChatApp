package com.example.chatapp.presentation.chat.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.chatapp.R

@Composable
fun ChatTopBar(
    name: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { /*TODO*/ }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Go back"
            )
        }

        Card(
            modifier = Modifier
                .size(40.dp),
            shape = RoundedCornerShape(35.dp),
            backgroundColor = Color.LightGray
        ) {
            AsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(R.drawable.ic_launcher_background)
                    .crossfade(true)
                    .placeholder(R.drawable.ic_person)
                    .build(),
                contentDescription = "User image",
                fallback = painterResource(R.drawable.ic_person),
                error = painterResource(R.drawable.ic_person)
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = name,
            fontWeight = FontWeight.SemiBold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}