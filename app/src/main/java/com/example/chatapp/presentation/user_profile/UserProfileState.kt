package com.example.chatapp.presentation.user_profile

import android.net.Uri

data class UserProfileState(
    val isEditSectionVisible: Boolean = false,
    val name: String = "",
    val firstName: String = "",
    val firstNameError: String? = null,
    val lastName: String = "",
    val lastNameError: String? = null,
    val profilePictureUrl: String = "",
    val profilePictureUri: Uri = Uri.EMPTY,
    val wasProfilePictureChanged: Boolean = false
)