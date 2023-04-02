package com.example.chatapp.presentation

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.di.AppModule
import com.example.chatapp.presentation.login.composable.LoginScreen
import com.example.chatapp.presentation.signup.composable.SignupScreen
import com.example.chatapp.ui.theme.ChatAppTheme
import com.example.chatapp.util.Constants.CONFIRM_PASSWORD_TF
import com.example.chatapp.util.Constants.EMAIL_TF
import com.example.chatapp.util.Constants.FIRST_NAME_TF
import com.example.chatapp.util.Constants.LAST_NAME_TF
import com.example.chatapp.util.Constants.PASSWORD_TF
import com.example.chatapp.util.Constants.SIGNUP_TF
import com.example.chatapp.util.Screen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class GoToSignupScreenEndToEndTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            ChatAppTheme {
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
                }
            }
        }
    }

    @Test
    fun goToSignUpScreen() {
        composeRule.onNodeWithTag(SIGNUP_TF).assertExists()
        composeRule.onNodeWithTag(SIGNUP_TF).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_TF).performClick()

        composeRule.onNodeWithTag(EMAIL_TF).assertExists()
        composeRule.onNodeWithTag(EMAIL_TF).assertIsDisplayed()

        composeRule.onNodeWithTag(PASSWORD_TF).assertExists()
        composeRule.onNodeWithTag(PASSWORD_TF).assertIsDisplayed()

        composeRule.onNodeWithTag(CONFIRM_PASSWORD_TF).assertExists()
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_TF).assertIsDisplayed()

        composeRule.onNodeWithTag(FIRST_NAME_TF).assertExists()
        composeRule.onNodeWithTag(FIRST_NAME_TF).assertIsDisplayed()

        composeRule.onNodeWithTag(LAST_NAME_TF).assertExists()
        composeRule.onNodeWithTag(LAST_NAME_TF).assertIsDisplayed()
    }
}