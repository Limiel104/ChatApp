package com.example.chatapp.presentation.user_profile

import android.net.Uri
import com.example.chatapp.util.Resource

data class UserProfileState(
    val isEditSectionVisible: Boolean = false,
    val name: String = "",
    val firstName: String = "",
    val firstNameError: String? = null,
    val lastName: String = "",
    val lastNameError: String? = null,
    val profilePictureUrl: String = "",
    val profilePictureUri: Uri = Uri.EMPTY,
    val wasProfilePictureChanged: Boolean = false,
    val updateUserInfoResponse: Resource<Boolean>? = null,
    val currentUserUID: String = "",
    val updateProfilePictureResponse: Resource<Uri>? = null,
)