package com.example.chatapp.domain.use_case

data class ChatUseCases(
    val validateEmailUseCase: ValidateEmailUseCase,
    val validateLoginPasswordUseCase: ValidateLoginPasswordUseCase,
    val validateSignupPasswordUseCase: ValidateSignupPasswordUseCase,
    val validateConfirmPasswordUseCase: ValidateConfirmPasswordUseCase,
    val validateNameUseCase: ValidateNameUseCase
)