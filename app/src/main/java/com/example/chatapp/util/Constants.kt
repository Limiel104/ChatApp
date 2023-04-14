package com.example.chatapp.util

object Constants {
    const val emailEmptyError: String = "Email can't be empty"
    const val specialChars = "!@#$%^&*(){}[]:;\"'<,>.?/~`'\\|_=+"
    const val passwordEmptyError: String = "Password can't be empty"
    const val shortPasswordError: String = "Password is too short"
    const val containsAtLeastOneDigitError: String = "Password should have at least one digit"
    const val containsAtLeastOneCapitalLetterError: String = "Password should have at least one capital letter"
    const val containsAtLeastOneSpecialCharError: String = "Password should have at least one special character"
    const val confirmPasswordError: String = "Passwords don't mach"
    const val fieldEmptyError: String = "Field can't be empty"
    const val digitsInNameError: String = "No digits allowed for this field"
    const val specialCharsInNameError: String = "Special characters not allowed for this field"

    const val EMAIL_TF: String = "EMAIL_TF"
    const val PASSWORD_TF: String = "PASSWORD_TF"
    const val CONFIRM_PASSWORD_TF: String = "CONFIRM_PASSWORD_TF"
    const val FIRST_NAME_TF: String = "FIRST_NAME_TF"
    const val LAST_NAME_TF: String = "LAST_NAME_TF"

    const val EMAIL_ERROR_TF: String = "EMAIL_ERROR_TF"
    const val PASSWORD_ERROR_TF: String = "PASSWORD_ERROR_TF"
    const val CONFIRM_PASSWORD_ERROR_TF: String = "CONFIRM_PASSWORD_ERROR_TF"
    const val FIRST_NAME_ERROR_TF: String = "FIRST_NAME_ERROR_TF"
    const val LAST_NAME_ERROR_TF: String = "LAST_NAME_ERROR_TF"

    const val LOGIN_BUTTON: String = "LOGIN_BUTTON"
    const val SIGNUP_BUTTON: String = "SIGNUP_BUTTON"

    const val SIGNUP_TF: String = "SIGNUP_TF"
    const val CIRCULAR_INDICATOR: String = "CIRCULAR_INDICATOR"

    const val emptyString: String = ""
    const val emailCorrect: String = "test@test.com"
    const val passwordIncorrect: String = "qwerty"
    const val passwordCorrect: String = "Qwerty1+"
    const val passwordTooShort: String = "Qwe"
    const val passwordWithoutDigit: String = "Qwerty++"
    const val passwordWithoutCapitalLetter: String = "qwerty1+"
    const val passwordWithoutSpecialChar: String = "Qwerty11"
    const val confirmPasswordCorrect: String = "Qwerty1+"
    const val confirmPasswordIncorrect: String = "Qwerty1++++"
    const val firstNameCorrect: String = "John"
    const val firstNameHasDigit: String = "John 3rd"
    const val firstNameHasSpecialChar: String = "John's"
    const val lastNameCorrect: String = "Smith"
    const val lastNameHasDigit: String = "Smith 3rd"
    const val lastNameHasSpecialChar: String = "Smith's"
    const val userUidCorrect: String = "h456uv456OV456n4565q1456Q7E2"

    const val USERS_COLLECTION: String = "users"
    const val USER_UID: String = "userUID"
}