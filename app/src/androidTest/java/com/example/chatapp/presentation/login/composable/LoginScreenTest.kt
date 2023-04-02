package com.example.chatapp.presentation.login.composable

import androidx.activity.compose.setContent
import androidx.compose.ui.semantics.SemanticsProperties.EditableText
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.di.AppModule
import com.example.chatapp.presentation.MainActivity
import com.example.chatapp.ui.theme.ChatAppTheme
import com.example.chatapp.util.Constants
import com.example.chatapp.util.Constants.CIRCULAR_INDICATOR
import com.example.chatapp.util.Constants.EMAIL_ERROR_TF
import com.example.chatapp.util.Constants.EMAIL_TF
import com.example.chatapp.util.Constants.LOGIN_BUTTON
import com.example.chatapp.util.Constants.PASSWORD_ERROR_TF
import com.example.chatapp.util.Constants.PASSWORD_TF
import com.example.chatapp.util.Constants.emailCorrect
import com.example.chatapp.util.Constants.passwordCorrect
import com.example.chatapp.util.Screen
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class LoginScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            val navController = rememberNavController()
            ChatAppTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.LoginScreen.route
                ) {
                    composable(
                        route = Screen.LoginScreen.route
                    ) {
                        LoginScreen(
                            navController = navController
                        )
                    }
                }
            }
        }
    }

    @Test
    fun enteredEmail_textDisplayedCorrectly() {
        composeRule.onNodeWithTag(EMAIL_TF).performTextInput(emailCorrect)
        val emailNode = composeRule.onNodeWithTag(EMAIL_TF).fetchSemanticsNode()
        val textInput = emailNode.config.getOrNull(EditableText).toString()
        assertThat(textInput).isEqualTo(emailCorrect)
    }

    @Test
    fun enteredPassword_textDisplayedCorrectly() {
        composeRule.onNodeWithTag(PASSWORD_TF).performTextInput(passwordCorrect)
        val passwordNode = composeRule.onNodeWithTag(PASSWORD_TF).fetchSemanticsNode()
        val textInput = passwordNode.config.getOrNull(EditableText).toString()
        assertThat(textInput.length).isEqualTo(passwordCorrect.length)
    }

    @Test
    fun performClickOnButtonWileEmailTextFieldIsEmpty_errorDisplayedCorrectly() {
        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertDoesNotExist()

        composeRule.onNodeWithTag(PASSWORD_TF).performTextInput(passwordCorrect)

        composeRule.onNodeWithTag(LOGIN_BUTTON).assertExists()
        composeRule.onNodeWithTag(LOGIN_BUTTON).assertIsDisplayed()
        composeRule.onNodeWithTag(LOGIN_BUTTON).performClick()

        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertExists()
        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertIsDisplayed()
        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertTextEquals(Constants.emailEmptyError)

        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertDoesNotExist()
    }

    @Test
    fun performClickOnButtonWilePasswordTextFieldIsEmpty_errorDisplayedCorrectly() {
        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertDoesNotExist()

        composeRule.onNodeWithTag(EMAIL_TF).performTextInput(emailCorrect)

        composeRule.onNodeWithTag(LOGIN_BUTTON).assertExists()
        composeRule.onNodeWithTag(LOGIN_BUTTON).assertIsDisplayed()
        composeRule.onNodeWithTag(LOGIN_BUTTON).performClick()

        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertExists()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertIsDisplayed()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertTextEquals(Constants.passwordEmptyError)

        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertDoesNotExist()
    }

    @Test
    fun performClickOnButtonWileAllTextFieldsAreEmpty_errorsDisplayedCorrectly() {
        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertDoesNotExist()

        composeRule.onNodeWithTag(LOGIN_BUTTON).assertExists()
        composeRule.onNodeWithTag(LOGIN_BUTTON).assertIsDisplayed()
        composeRule.onNodeWithTag(LOGIN_BUTTON).performClick()

        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertExists()
        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertIsDisplayed()
        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertTextEquals(Constants.emailEmptyError)

        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertExists()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertIsDisplayed()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertTextEquals(Constants.passwordEmptyError)
    }

    @Test
    fun performClickOnButton_noErrors() {
        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertDoesNotExist()

        composeRule.onNodeWithTag(EMAIL_TF).performTextInput(emailCorrect)
        composeRule.onNodeWithTag(PASSWORD_TF).performTextInput(passwordCorrect)

        composeRule.onNodeWithTag(LOGIN_BUTTON).assertExists()
        composeRule.onNodeWithTag(LOGIN_BUTTON).assertIsDisplayed()
        composeRule.onNodeWithTag(LOGIN_BUTTON).performClick()

        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertDoesNotExist()
    }

    @Test
    fun isLoading_circularProgressIndicatorIsDisplayedCorrectly() {
        composeRule.onNodeWithTag(CIRCULAR_INDICATOR).assertDoesNotExist()

        composeRule.onNodeWithTag(EMAIL_TF).performTextInput(emailCorrect)
        composeRule.onNodeWithTag(PASSWORD_TF).performTextInput(passwordCorrect)
        composeRule.onNodeWithTag(LOGIN_BUTTON).performClick()

        composeRule.onNodeWithTag(CIRCULAR_INDICATOR).assertExists()
    }
}