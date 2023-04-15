package com.example.chatapp.domain.use_case

data class ChatUseCases(
    val loginUseCase: LoginUseCase,
    val signupUseCase: SignupUseCase,
    val logoutUseCase: LogoutUseCase,
    val getCurrentUserUseCase: GetCurrentUserUseCase,
    val addUserUseCase: AddUserUseCase,
    val getUsersUseCase: GetUsersUseCase,
    val filterUsersUseCase: FilterUsersUseCase,
    val validateEmailUseCase: ValidateEmailUseCase,
    val validateLoginPasswordUseCase: ValidateLoginPasswordUseCase,
    val validateSignupPasswordUseCase: ValidateSignupPasswordUseCase,
    val validateConfirmPasswordUseCase: ValidateConfirmPasswordUseCase,
    val validateNameUseCase: ValidateNameUseCase
)