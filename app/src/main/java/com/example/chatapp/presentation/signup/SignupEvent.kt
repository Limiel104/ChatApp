package com.example.chatapp.presentation.signup

import android.net.Uri

sealed class SignupEvent {
    data class EnteredEmail(val value: String): SignupEvent()
    data class EnteredPassword(val value: String): SignupEvent()
    data class EnteredConfirmPassword(val value: String): SignupEvent()
    data class EnteredFirstName(val value: String): SignupEvent()
    data class EnteredLastName(val value: String): SignupEvent()
    data class SelectedProfilePicture(val value: Uri?): SignupEvent()
    object Signup: SignupEvent()
}