package com.example.chatapp.presentation.user_profile

import android.net.Uri

sealed class UserProfileEvent {
    data class EnteredFirstName(val value: String): UserProfileEvent()
    data class EnteredLastName(val value: String): UserProfileEvent()
    data class SelectedProfilePicture(val value: Uri?): UserProfileEvent()
    object EditSectionVisibilityChange: UserProfileEvent()
    object SaveNewProfilePicture: UserProfileEvent()
    object Save: UserProfileEvent()
}