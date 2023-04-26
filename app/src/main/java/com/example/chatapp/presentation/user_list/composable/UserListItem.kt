package com.example.chatapp.presentation.user_list.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatapp.domain.model.User
import com.example.chatapp.presentation.common.composable.ProfilePicture
import com.example.chatapp.ui.theme.ChatAppTheme
import com.example.chatapp.util.Constants.emptyString
import com.example.chatapp.util.Constants.firstNameCorrect
import com.example.chatapp.util.Constants.lastNameCorrect
import com.example.chatapp.util.Constants.userUIDCorrect

@Composable
fun UserListItem(
    user: User,
    message: String,
    date: String,
    onItemClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable { onItemClick() }
            .padding(5.dp, 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfilePicture(
            size = 40,
            profilePictureUrl = user.profilePictureUrl,
            isClickable = false,
            onClick = {}
        )

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
                    text = user.firstName + " " + user.lastName,
                    fontWeight = FontWeight.SemiBold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )

                Text(
                    text = date,
                    fontWeight = FontWeight.Light,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    fontSize = 13.sp,
                    modifier = Modifier
                        .padding(end = 4.dp)
                )
            }

            Text(
                text = message,
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
fun UserListItemPreview() {
    ChatAppTheme() {

        val user = User(
            userUID = userUIDCorrect,
            firstName = firstNameCorrect,
            lastName = lastNameCorrect,
            profilePictureUrl = emptyString
        )

        val message = "Last message that was send in this chat"
        val date = "25 April"

        UserListItem(
            user = user,
            message = message,
            date = date,
            onItemClick = {}
        )
    }
}