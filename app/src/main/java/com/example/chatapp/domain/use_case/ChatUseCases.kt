package com.example.chatapp.domain.use_case

data class ChatUseCases(
    val loginUseCase: LoginUseCase,
    val signupUseCase: SignupUseCase,
    val logoutUseCase: LogoutUseCase,
    val getCurrentUserUseCase: GetCurrentUserUseCase,
    val addUserUseCase: AddUserUseCase,
    val getUserUseCase: GetUserUseCase,
    val getUsersUseCase: GetUsersUseCase,
    val filterUsersUseCase: FilterUsersUseCase,
    val addMessageUseCase: AddMessageUseCase,
    val getMessagesUseCase: GetMessagesUseCase,
    val getAllUserMessagesUseCase: GetAllUserMessagesUseCase,
    val getLastMessagesUseCase: GetLastMessagesUseCase,
    val deleteMessageUseCase: DeleteMessageUseCase,
    val addImageUseCase: AddImageUseCase,
    val updateUserInfoUseCase: UpdateUserInfoUseCase,
    val updateUserEmailUseCase: UpdateUserEmailUseCase,
    val updateUserPasswordUseCase: UpdateUserPasswordUseCase,
    val validateEmailUseCase: ValidateEmailUseCase,
    val validateLoginPasswordUseCase: ValidateLoginPasswordUseCase,
    val validateSignupPasswordUseCase: ValidateSignupPasswordUseCase,
    val validateConfirmPasswordUseCase: ValidateConfirmPasswordUseCase,
    val validateNameUseCase: ValidateNameUseCase
)