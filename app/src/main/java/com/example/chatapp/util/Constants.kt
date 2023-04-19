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
    const val userUIDCorrect: String = "h456uv456OV456n4565q1456Q7E2"
    const val avatarURLCorrect: String = "avatarUrl"
    const val messageCorrect: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam ac mollis erat. Morbi sit amet ligula quis mauris scelerisque volutpat ut at mi. Maecenas et dolor luctus lacus condimentum pharetra."

    const val user1UID: String = "1234567321890"
    const val user1FirstName: String = "Mike"
    const val user1LastName: String = "Hurt"
    const val user1AvatarURL:String = "avatarUrl"
    const val user2UID: String = "23846468267832"
    const val user2FirstName: String = "Denis"
    const val user2LastName: String = "Turner"
    const val user2AvatarURL:String = "avatarUrl"
    const val user3UID: String = "3246783264782"
    const val user3FirstName: String = "Conor"
    const val user3LastName: String = "Smith"
    const val user3AvatarURL:String = "avatarUrl"
    const val user4UID: String = "4242343265672"
    const val user4FirstName: String = "Julia"
    const val user4LastName: String = "Porter"
    const val user4AvatarURL:String = "avatarUrl"
    const val user5UID: String = "6759373228679"
    const val user5FirstName: String = "Julia"
    const val user5LastName: String = "Porter"
    const val user5AvatarURL:String = "avatarUrl"

    const val USERS_COLLECTION: String = "users"
    const val USER_UID: String = "userUID"

    const val MESSAGES_COLLECTION: String = "messages"
    const val SENDER_UID: String = "senderUID"
    const val RECEIVER_UID: String = "receiverUID"
    const val DATE: String = "date"
}