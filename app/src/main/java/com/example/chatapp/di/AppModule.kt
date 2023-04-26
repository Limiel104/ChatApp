package com.example.chatapp.di

import com.example.chatapp.data.repository.AuthRepositoryImpl
import com.example.chatapp.data.repository.ImageStorageRepositoryImpl
import com.example.chatapp.data.repository.MessageStorageRepositoryImpl
import com.example.chatapp.data.repository.UserStorageRepositoryImpl
import com.example.chatapp.domain.repository.AuthRepository
import com.example.chatapp.domain.repository.ImageStorageRepository
import com.example.chatapp.domain.repository.MessageStorageRepository
import com.example.chatapp.domain.repository.UserStorageRepository
import com.example.chatapp.domain.use_case.*
import com.example.chatapp.util.Constants.MESSAGES_COLLECTION
import com.example.chatapp.util.Constants.USERS_COLLECTION
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideUserStorageRepository(): UserStorageRepository {
        val usersRef = Firebase.firestore.collection(USERS_COLLECTION)
        return UserStorageRepositoryImpl(usersRef)
    }

    @Provides
    @Singleton
    fun provideMessageStorageRepository(): MessageStorageRepository {
        val messagesRef = Firebase.firestore.collection(MESSAGES_COLLECTION)
        return MessageStorageRepositoryImpl(messagesRef)
    }

    @Provides
    @Singleton
    fun provideImageStorageRepository(): ImageStorageRepository {
        val storage = Firebase.storage
        return ImageStorageRepositoryImpl(storage)
    }

    @Provides
    @Singleton
    fun provideChatUseCases(
        authRepository: AuthRepository,
        userStorageRepository: UserStorageRepository,
        messageStorageRepository: MessageStorageRepository,
        imageStorageRepository: ImageStorageRepository
    ): ChatUseCases {
        return ChatUseCases(
            loginUseCase = LoginUseCase(authRepository),
            signupUseCase = SignupUseCase(authRepository),
            logoutUseCase = LogoutUseCase(authRepository),
            getCurrentUserUseCase = GetCurrentUserUseCase(authRepository),
            addUserUseCase = AddUserUseCase(userStorageRepository),
            getUserUseCase = GetUserUseCase(userStorageRepository),
            getUsersUseCase = GetUsersUseCase(userStorageRepository),
            filterUsersUseCase = FilterUsersUseCase(),
            addMessageUseCase = AddMessageUseCase(messageStorageRepository),
            getMessagesUseCase = GetMessagesUseCase(messageStorageRepository),
            getAllUserMessagesUseCase = GetAllUserMessagesUseCase(messageStorageRepository),
            getLastMessagesUseCase = GetLastMessagesUseCase(),
            deleteMessageUseCase = DeleteMessageUseCase(messageStorageRepository),
            addImageUseCase = AddImageUseCase(imageStorageRepository),
            updateUserInfoUseCase = UpdateUserInfoUseCase(userStorageRepository),
            updateUserEmailUseCase = UpdateUserEmailUseCase(authRepository),
            updateUserPasswordUseCase = UpdateUserPasswordUseCase(authRepository),
            validateEmailUseCase = ValidateEmailUseCase(),
            validateLoginPasswordUseCase = ValidateLoginPasswordUseCase(),
            validateSignupPasswordUseCase = ValidateSignupPasswordUseCase(),
            validateConfirmPasswordUseCase = ValidateConfirmPasswordUseCase(),
            validateNameUseCase = ValidateNameUseCase()
        )
    }
}