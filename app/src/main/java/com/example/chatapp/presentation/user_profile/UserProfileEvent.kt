package com.example.chatapp.presentation.user_profile

import android.net.Uri

sealed class UserProfileEvent {
    data class EnteredFirstName(val value: String): UserProfileEvent()
    data class EnteredLastName(val value: String): UserProfileEvent()
    data class EnteredEmail(val value: String): UserProfileEvent()
    data class EnteredPassword(val value: String): UserProfileEvent()
    data class EnteredConfirmPassword(val value: String): UserProfileEvent()
    data class SelectedProfilePicture(val value: Uri?): UserProfileEvent()
    object EditProfileInfoVisibilityChange: UserProfileEvent()
    object EditEmailVisibilityChange: UserProfileEvent()
    object EditPasswordVisibilityChange: UserProfileEvent()
    object Save: UserProfileEvent()
    object GoBack: UserProfileEvent()
}