package com.example.chatapp.presentation.user_profile

import android.net.Uri
import com.example.chatapp.util.Resource

data class UserProfileState(
    val name: String = "",
    val firstName: String = "",
    val firstNameError: String? = null,
    val lastName: String = "",
    val lastNameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val confirmPassword: String = "",
    val confirmPasswordError: String? = null,
    val profilePictureUrl: String = "",
    val profilePictureUri: Uri = Uri.EMPTY,
    val wasProfilePictureChanged: Boolean = false,
    val updateUserResponse: Resource<Boolean>? = null,
    val currentUserUID: String = "",
    val updateProfilePictureResponse: Resource<Uri>? = null,
    val isEditProfileInfoVisible: Boolean = false,
    val isEditEmailVisible: Boolean = false,
    val isEditPasswordVisible: Boolean = false
)