package com.example.chatapp.domain.use_case

data class ChatUseCases(
    val loginUseCase: LoginUseCase,
    val signupUseCase: SignupUseCase,
    val getCurrentUserUseCase: GetCurrentUserUseCase,
    val validateEmailUseCase: ValidateEmailUseCase,
    val validateLoginPasswordUseCase: ValidateLoginPasswordUseCase,
    val validateSignupPasswordUseCase: ValidateSignupPasswordUseCase,
    val validateConfirmPasswordUseCase: ValidateConfirmPasswordUseCase,
    val validateNameUseCase: ValidateNameUseCase
)