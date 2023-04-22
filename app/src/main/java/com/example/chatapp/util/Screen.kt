package com.example.chatapp.util

sealed class Screen(val route: String) {
    object LoginScreen: Screen("login_screen")
    object SignupScreen: Screen("signup_screen")
    object UserListScreen: Screen("user_list_screen")
    object ChatScreen: Screen("chat_screen")
    object UserProfileScreen: Screen("user_profile_screen")
}