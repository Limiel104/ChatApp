package com.example.chatapp.presentation.signup.composable

import androidx.activity.compose.setContent
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.di.AppModule
import com.example.chatapp.presentation.MainActivity
import com.example.chatapp.ui.theme.ChatAppTheme
import com.example.chatapp.util.Constants.CIRCULAR_INDICATOR
import com.example.chatapp.util.Constants.CONFIRM_PASSWORD_ERROR_TF
import com.example.chatapp.util.Constants.CONFIRM_PASSWORD_TF
import com.example.chatapp.util.Constants.EMAIL_ERROR_TF
import com.example.chatapp.util.Constants.EMAIL_TF
import com.example.chatapp.util.Constants.FIRST_NAME_ERROR_TF
import com.example.chatapp.util.Constants.FIRST_NAME_TF
import com.example.chatapp.util.Constants.LAST_NAME_ERROR_TF
import com.example.chatapp.util.Constants.LAST_NAME_TF
import com.example.chatapp.util.Constants.PASSWORD_ERROR_TF
import com.example.chatapp.util.Constants.PASSWORD_TF
import com.example.chatapp.util.Constants.SIGNUP_BUTTON
import com.example.chatapp.util.Constants.confirmPasswordCorrect
import com.example.chatapp.util.Constants.confirmPasswordError
import com.example.chatapp.util.Constants.containsAtLeastOneCapitalLetterError
import com.example.chatapp.util.Constants.containsAtLeastOneDigitError
import com.example.chatapp.util.Constants.containsAtLeastOneSpecialCharError
import com.example.chatapp.util.Constants.digitsInNameError
import com.example.chatapp.util.Constants.emailCorrect
import com.example.chatapp.util.Constants.emailEmptyError
import com.example.chatapp.util.Constants.fieldEmptyError
import com.example.chatapp.util.Constants.firstNameCorrect
import com.example.chatapp.util.Constants.firstNameHasDigit
import com.example.chatapp.util.Constants.firstNameHasSpecialChar
import com.example.chatapp.util.Constants.lastNameCorrect
import com.example.chatapp.util.Constants.lastNameHasDigit
import com.example.chatapp.util.Constants.lastNameHasSpecialChar
import com.example.chatapp.util.Constants.passwordCorrect
import com.example.chatapp.util.Constants.passwordEmptyError
import com.example.chatapp.util.Constants.passwordTooShort
import com.example.chatapp.util.Constants.passwordWithoutCapitalLetter
import com.example.chatapp.util.Constants.passwordWithoutDigit
import com.example.chatapp.util.Constants.passwordWithoutSpecialChar
import com.example.chatapp.util.Constants.shortPasswordError
import com.example.chatapp.util.Constants.specialCharsInNameError
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
class SignupScreenTest {

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
                    startDestination = Screen.SignupScreen.route
                ) {
                    composable(
                        route = Screen.SignupScreen.route
                    ) {
                        SignupScreen(
                            navController = navController
                        )
                    }
                }
            }
        }
    }

    @Test
    fun enteredEmail_textDisplayedCorrectly() {
        composeRule.onNodeWithTag(EMAIL_TF).performTextInput("test@test.com")
        val emailNode = composeRule.onNodeWithTag(EMAIL_TF).fetchSemanticsNode()
        val textInput = emailNode.config.getOrNull(SemanticsProperties.EditableText).toString()
        assertThat(textInput).isEqualTo("test@test.com")
    }

    @Test
    fun enteredPassword_textDisplayedCorrectly() {
        composeRule.onNodeWithTag(PASSWORD_TF).performTextInput(passwordCorrect)
        val passwordNode = composeRule.onNodeWithTag(PASSWORD_TF).fetchSemanticsNode()
        val textInput = passwordNode.config.getOrNull(SemanticsProperties.EditableText).toString()
        assertThat(textInput.length).isEqualTo(passwordCorrect.length)
    }

    @Test
    fun enteredConfirmPassword_textDisplayedCorrectly() {
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_TF).performTextInput(passwordCorrect)
        val confirmPasswordNode = composeRule.onNodeWithTag(CONFIRM_PASSWORD_TF).fetchSemanticsNode()
        val textInput = confirmPasswordNode.config.getOrNull(SemanticsProperties.EditableText).toString()
        assertThat(textInput.length).isEqualTo(passwordCorrect.length)
    }

    @Test
    fun enteredFirstName_textDisplayedCorrectly() {
        composeRule.onNodeWithTag(FIRST_NAME_TF).performTextInput("John")
        val firstNameNode = composeRule.onNodeWithTag(FIRST_NAME_TF).fetchSemanticsNode()
        val textInput = firstNameNode.config.getOrNull(SemanticsProperties.EditableText).toString()
        assertThat(textInput).isEqualTo("John")
    }

    @Test
    fun enteredLastName_textDisplayedCorrectly() {
        composeRule.onNodeWithTag(LAST_NAME_TF).performTextInput("Smith")
        val lastNameNode = composeRule.onNodeWithTag(LAST_NAME_TF).fetchSemanticsNode()
        val textInput = lastNameNode.config.getOrNull(SemanticsProperties.EditableText).toString()
        assertThat(textInput).isEqualTo("Smith")
    }

    @Test
    fun performClickOnButtonWileEmailTextFieldIsEmpty_errorDisplayedCorrectly() {
        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(FIRST_NAME_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(LAST_NAME_ERROR_TF).assertDoesNotExist()

        composeRule.onNodeWithTag(PASSWORD_TF).performTextInput(passwordCorrect)
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_TF).performTextInput(confirmPasswordCorrect)
        composeRule.onNodeWithTag(FIRST_NAME_TF).performTextInput(firstNameCorrect)
        composeRule.onNodeWithTag(LAST_NAME_TF).performTextInput(lastNameCorrect)

        composeRule.onNodeWithTag(SIGNUP_BUTTON).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BUTTON).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BUTTON).performClick()

        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertExists()
        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertTextEquals(emailEmptyError)

        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(FIRST_NAME_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(LAST_NAME_ERROR_TF).assertDoesNotExist()
    }

    @Test
    fun performClickOnButtonWilePasswordTextFieldIsEmpty_errorDisplayedCorrectly() {
        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(FIRST_NAME_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(LAST_NAME_ERROR_TF).assertDoesNotExist()

        composeRule.onNodeWithTag(EMAIL_TF).performTextInput(emailCorrect)
        composeRule.onNodeWithTag(FIRST_NAME_TF).performTextInput(firstNameCorrect)
        composeRule.onNodeWithTag(LAST_NAME_TF).performTextInput(lastNameCorrect)

        composeRule.onNodeWithTag(SIGNUP_BUTTON).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BUTTON).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BUTTON).performClick()

        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertExists()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertIsDisplayed()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertTextEquals(passwordEmptyError)

        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(FIRST_NAME_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(LAST_NAME_ERROR_TF).assertDoesNotExist()
    }

    @Test
    fun performClickOnButtonWileConfirmPasswordTextFieldIsEmpty_errorDisplayedCorrectly() {
        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(FIRST_NAME_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(LAST_NAME_ERROR_TF).assertDoesNotExist()

        composeRule.onNodeWithTag(EMAIL_TF).performTextInput(emailCorrect)
        composeRule.onNodeWithTag(PASSWORD_TF).performTextInput(passwordCorrect)
        composeRule.onNodeWithTag(FIRST_NAME_TF).performTextInput(firstNameCorrect)
        composeRule.onNodeWithTag(LAST_NAME_TF).performTextInput(lastNameCorrect)

        composeRule.onNodeWithTag(SIGNUP_BUTTON).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BUTTON).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BUTTON).performClick()

        composeRule.onNodeWithTag(CONFIRM_PASSWORD_ERROR_TF).assertExists()
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_ERROR_TF).assertIsDisplayed()
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_ERROR_TF).assertTextEquals(confirmPasswordError)

        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(FIRST_NAME_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(LAST_NAME_ERROR_TF).assertDoesNotExist()
    }

    @Test
    fun performClickOnButtonWilePasswordIsTooShort_errorDisplayedCorrectly() {
        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(FIRST_NAME_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(LAST_NAME_ERROR_TF).assertDoesNotExist()

        composeRule.onNodeWithTag(EMAIL_TF).performTextInput(emailCorrect)
        composeRule.onNodeWithTag(PASSWORD_TF).performTextInput(passwordTooShort)
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_TF).performTextInput(passwordTooShort)
        composeRule.onNodeWithTag(FIRST_NAME_TF).performTextInput(firstNameCorrect)
        composeRule.onNodeWithTag(LAST_NAME_TF).performTextInput(lastNameCorrect)

        composeRule.onNodeWithTag(SIGNUP_BUTTON).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BUTTON).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BUTTON).performClick()

        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertExists()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertIsDisplayed()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertTextEquals(shortPasswordError)

        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(FIRST_NAME_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(LAST_NAME_ERROR_TF).assertDoesNotExist()
    }

    @Test
    fun performClickOnButtonWilePasswordDoesNotHaveOneDigit_errorDisplayedCorrectly() {
        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(FIRST_NAME_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(LAST_NAME_ERROR_TF).assertDoesNotExist()

        composeRule.onNodeWithTag(EMAIL_TF).performTextInput(emailCorrect)
        composeRule.onNodeWithTag(PASSWORD_TF).performTextInput(passwordWithoutDigit)
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_TF).performTextInput(passwordWithoutDigit)
        composeRule.onNodeWithTag(FIRST_NAME_TF).performTextInput(firstNameCorrect)
        composeRule.onNodeWithTag(LAST_NAME_TF).performTextInput(lastNameCorrect)

        composeRule.onNodeWithTag(SIGNUP_BUTTON).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BUTTON).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BUTTON).performClick()

        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertExists()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertIsDisplayed()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertTextEquals(containsAtLeastOneDigitError)

        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(FIRST_NAME_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(LAST_NAME_ERROR_TF).assertDoesNotExist()
    }

    @Test
    fun performClickOnButtonWilePasswordDoesNotHaveOneCapitalLetter_errorDisplayedCorrectly() {
        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(FIRST_NAME_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(LAST_NAME_ERROR_TF).assertDoesNotExist()

        composeRule.onNodeWithTag(EMAIL_TF).performTextInput(emailCorrect)
        composeRule.onNodeWithTag(PASSWORD_TF).performTextInput(passwordWithoutCapitalLetter)
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_TF).performTextInput(passwordWithoutCapitalLetter)
        composeRule.onNodeWithTag(FIRST_NAME_TF).performTextInput(firstNameCorrect)
        composeRule.onNodeWithTag(LAST_NAME_TF).performTextInput(lastNameCorrect)

        composeRule.onNodeWithTag(SIGNUP_BUTTON).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BUTTON).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BUTTON).performClick()

        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertExists()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertIsDisplayed()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertTextEquals(
            containsAtLeastOneCapitalLetterError)

        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(FIRST_NAME_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(LAST_NAME_ERROR_TF).assertDoesNotExist()
    }

    @Test
    fun performClickOnButtonWilePasswordDoesNotHaveOneSpecialChar_errorDisplayedCorrectly() {
        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(FIRST_NAME_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(LAST_NAME_ERROR_TF).assertDoesNotExist()

        composeRule.onNodeWithTag(EMAIL_TF).performTextInput(emailCorrect)
        composeRule.onNodeWithTag(PASSWORD_TF).performTextInput(passwordWithoutSpecialChar)
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_TF).performTextInput(passwordWithoutSpecialChar)
        composeRule.onNodeWithTag(FIRST_NAME_TF).performTextInput(firstNameCorrect)
        composeRule.onNodeWithTag(LAST_NAME_TF).performTextInput(lastNameCorrect)

        composeRule.onNodeWithTag(SIGNUP_BUTTON).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BUTTON).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BUTTON).performClick()

        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertExists()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertIsDisplayed()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertTextEquals(
            containsAtLeastOneSpecialCharError)

        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(FIRST_NAME_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(LAST_NAME_ERROR_TF).assertDoesNotExist()
    }

    @Test
    fun performClickOnButtonWileFirstNameTextFieldIsEmpty_errorDisplayedCorrectly() {
        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(FIRST_NAME_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(LAST_NAME_ERROR_TF).assertDoesNotExist()

        composeRule.onNodeWithTag(EMAIL_TF).performTextInput(emailCorrect)
        composeRule.onNodeWithTag(PASSWORD_TF).performTextInput(passwordCorrect)
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_TF).performTextInput(confirmPasswordCorrect)
        composeRule.onNodeWithTag(LAST_NAME_TF).performTextInput(lastNameCorrect)

        composeRule.onNodeWithTag(SIGNUP_BUTTON).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BUTTON).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BUTTON).performClick()

        composeRule.onNodeWithTag(FIRST_NAME_ERROR_TF).assertExists()
        composeRule.onNodeWithTag(FIRST_NAME_ERROR_TF).assertIsDisplayed()
        composeRule.onNodeWithTag(FIRST_NAME_ERROR_TF).assertTextEquals(fieldEmptyError)

        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(LAST_NAME_ERROR_TF).assertDoesNotExist()
    }

    @Test
    fun performClickOnButtonWileFirstNameHasDigit_errorDisplayedCorrectly() {
        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(FIRST_NAME_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(LAST_NAME_ERROR_TF).assertDoesNotExist()

        composeRule.onNodeWithTag(EMAIL_TF).performTextInput(emailCorrect)
        composeRule.onNodeWithTag(PASSWORD_TF).performTextInput(passwordCorrect)
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_TF).performTextInput(confirmPasswordCorrect)
        composeRule.onNodeWithTag(FIRST_NAME_TF).performTextInput(firstNameHasDigit)
        composeRule.onNodeWithTag(LAST_NAME_TF).performTextInput(lastNameCorrect)

        composeRule.onNodeWithTag(SIGNUP_BUTTON).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BUTTON).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BUTTON).performClick()

        composeRule.onNodeWithTag(FIRST_NAME_ERROR_TF).assertExists()
        composeRule.onNodeWithTag(FIRST_NAME_ERROR_TF).assertIsDisplayed()
        composeRule.onNodeWithTag(FIRST_NAME_ERROR_TF).assertTextEquals(digitsInNameError)

        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(LAST_NAME_ERROR_TF).assertDoesNotExist()
    }

    @Test
    fun performClickOnButtonWileFirstNameHasSpecialChar_errorDisplayedCorrectly() {
        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(FIRST_NAME_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(LAST_NAME_ERROR_TF).assertDoesNotExist()

        composeRule.onNodeWithTag(EMAIL_TF).performTextInput(emailCorrect)
        composeRule.onNodeWithTag(PASSWORD_TF).performTextInput(passwordCorrect)
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_TF).performTextInput(confirmPasswordCorrect)
        composeRule.onNodeWithTag(FIRST_NAME_TF).performTextInput(firstNameHasSpecialChar)
        composeRule.onNodeWithTag(LAST_NAME_TF).performTextInput(lastNameCorrect)

        composeRule.onNodeWithTag(SIGNUP_BUTTON).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BUTTON).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BUTTON).performClick()

        composeRule.onNodeWithTag(FIRST_NAME_ERROR_TF).assertExists()
        composeRule.onNodeWithTag(FIRST_NAME_ERROR_TF).assertIsDisplayed()
        composeRule.onNodeWithTag(FIRST_NAME_ERROR_TF).assertTextEquals(specialCharsInNameError)

        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(LAST_NAME_ERROR_TF).assertDoesNotExist()
    }

    @Test
    fun performClickOnButtonWileLastNameTextFieldIsEmpty_errorDisplayedCorrectly() {
        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(FIRST_NAME_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(LAST_NAME_ERROR_TF).assertDoesNotExist()

        composeRule.onNodeWithTag(EMAIL_TF).performTextInput(emailCorrect)
        composeRule.onNodeWithTag(PASSWORD_TF).performTextInput(passwordCorrect)
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_TF).performTextInput(confirmPasswordCorrect)
        composeRule.onNodeWithTag(FIRST_NAME_TF).performTextInput(firstNameCorrect)

        composeRule.onNodeWithTag(SIGNUP_BUTTON).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BUTTON).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BUTTON).performClick()

        composeRule.onNodeWithTag(LAST_NAME_ERROR_TF).assertExists()
        composeRule.onNodeWithTag(LAST_NAME_ERROR_TF).assertIsDisplayed()
        composeRule.onNodeWithTag(LAST_NAME_ERROR_TF).assertTextEquals(fieldEmptyError)

        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(FIRST_NAME_ERROR_TF).assertDoesNotExist()
    }

    @Test
    fun performClickOnButtonWileLastNameHasDigit_errorDisplayedCorrectly() {
        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(FIRST_NAME_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(LAST_NAME_ERROR_TF).assertDoesNotExist()

        composeRule.onNodeWithTag(EMAIL_TF).performTextInput(emailCorrect)
        composeRule.onNodeWithTag(PASSWORD_TF).performTextInput(passwordCorrect)
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_TF).performTextInput(confirmPasswordCorrect)
        composeRule.onNodeWithTag(FIRST_NAME_TF).performTextInput(lastNameHasDigit)
        composeRule.onNodeWithTag(LAST_NAME_TF).performTextInput(lastNameCorrect)

        composeRule.onNodeWithTag(SIGNUP_BUTTON).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BUTTON).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BUTTON).performClick()

        composeRule.onNodeWithTag(FIRST_NAME_ERROR_TF).assertExists()
        composeRule.onNodeWithTag(FIRST_NAME_ERROR_TF).assertIsDisplayed()
        composeRule.onNodeWithTag(FIRST_NAME_ERROR_TF).assertTextEquals(digitsInNameError)

        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(LAST_NAME_ERROR_TF).assertDoesNotExist()
    }

    @Test
    fun performClickOnButtonWileLastNameHasSpecialChar_errorDisplayedCorrectly() {
        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(FIRST_NAME_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(LAST_NAME_ERROR_TF).assertDoesNotExist()

        composeRule.onNodeWithTag(EMAIL_TF).performTextInput(emailCorrect)
        composeRule.onNodeWithTag(PASSWORD_TF).performTextInput(passwordCorrect)
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_TF).performTextInput(confirmPasswordCorrect)
        composeRule.onNodeWithTag(FIRST_NAME_TF).performTextInput(lastNameHasSpecialChar)
        composeRule.onNodeWithTag(LAST_NAME_TF).performTextInput(lastNameCorrect)

        composeRule.onNodeWithTag(SIGNUP_BUTTON).assertExists()
        composeRule.onNodeWithTag(SIGNUP_BUTTON).assertIsDisplayed()
        composeRule.onNodeWithTag(SIGNUP_BUTTON).performClick()

        composeRule.onNodeWithTag(FIRST_NAME_ERROR_TF).assertExists()
        composeRule.onNodeWithTag(FIRST_NAME_ERROR_TF).assertIsDisplayed()
        composeRule.onNodeWithTag(FIRST_NAME_ERROR_TF).assertTextEquals(specialCharsInNameError)

        composeRule.onNodeWithTag(EMAIL_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_ERROR_TF).assertDoesNotExist()
        composeRule.onNodeWithTag(LAST_NAME_ERROR_TF).assertDoesNotExist()
    }

    @Test
    fun isLoading_circularProgressIndicatorIsDisplayedCorrectly() {
        composeRule.onNodeWithTag(CIRCULAR_INDICATOR).assertDoesNotExist()

        composeRule.onNodeWithTag(EMAIL_TF).performTextInput(emailCorrect)
        composeRule.onNodeWithTag(PASSWORD_TF).performTextInput(passwordCorrect)
        composeRule.onNodeWithTag(CONFIRM_PASSWORD_TF).performTextInput(confirmPasswordCorrect)
        composeRule.onNodeWithTag(FIRST_NAME_TF).performTextInput(firstNameCorrect)
        composeRule.onNodeWithTag(LAST_NAME_TF).performTextInput(lastNameCorrect)
        composeRule.onNodeWithTag(SIGNUP_BUTTON).performClick()

        composeRule.onNodeWithTag(CIRCULAR_INDICATOR).assertExists()
    }
}