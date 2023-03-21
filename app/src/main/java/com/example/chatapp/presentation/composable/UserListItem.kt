package com.example.chatapp.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
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
import com.example.chatapp.R

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
                    .data(R.drawable.ic_launcher_background)
                    .crossfade(true)
                    .placeholder(R.drawable.ic_person)
                    .build(),
                contentDescription = "User image",
                fallback = painterResource(R.drawable.ic_person),
                error = painterResource(R.drawable.ic_person)
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