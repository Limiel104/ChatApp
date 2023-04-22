package com.example.chatapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.chatapp.presentation.chat.composable.ChatScreen
import com.example.chatapp.presentation.common.composable.TransparentSystemBar
import com.example.chatapp.presentation.login.composable.LoginScreen
import com.example.chatapp.presentation.signup.composable.SignupScreen
import com.example.chatapp.presentation.user_list.composable.UserListScreen
import com.example.chatapp.presentation.user_profile.composable.UserProfileScreen
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
                            route = Screen.ChatScreen.route + "userUID={userUID}",
                            arguments = listOf(
                                navArgument(
                                    name = "userUID"
                                ) {
                                    type = NavType.StringType
                                }
                            )
                        ) {
                            ChatScreen(navController = navController)
                        }
                        composable(
                            route = Screen.UserProfileScreen.route + "userUID={userUID}",
                            arguments = listOf(
                                navArgument(
                                    name = "userUID"
                                ) {
                                    type = NavType.StringType
                                }
                            )
                        ) {
                            UserProfileScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}