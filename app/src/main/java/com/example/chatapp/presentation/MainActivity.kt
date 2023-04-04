package com.example.chatapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.presentation.composable.*
import com.example.chatapp.presentation.login.composable.LoginScreen
import com.example.chatapp.presentation.signup.composable.SignupScreen
import com.example.chatapp.presentation.user_list.composable.UserListScreen
import com.example.chatapp.ui.theme.ChatAppTheme
import com.example.chatapp.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTheme {
                TransparentSystemBar()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.LoginScreen.route
                    ) {
                        composable(
                            route = Screen.LoginScreen.route
                        ) {
                            LoginScreen(navController = navController)
                        }
                        composable(
                            route = Screen.SignupScreen.route
                        ) {
                            SignupScreen(navController = navController)
                        }
                        composable(
                            route = Screen.UserListScreen.route
                        ) {
                            UserListScreen(navController = navController)
                        }
                        composable(
                            route = Screen.ChatScreen.route
                        ) {
                            ChatScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}