package com.example.chatapp.di

import com.example.chatapp.data.repository.AuthRepositoryImpl
import com.example.chatapp.data.repository.UserRepositoryImpl
import com.example.chatapp.domain.repository.AuthRepository
import com.example.chatapp.domain.repository.UserRepository
import com.example.chatapp.util.Constants.USERS_COLLECTION
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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
    fun provideUserRef(): CollectionReference {
        return Firebase.firestore.collection(USERS_COLLECTION)
    }

    @Provides
    @Singleton
    fun provideUserRepository(usersRef: CollectionReference): UserRepository {
        return UserRepositoryImpl(usersRef)
    }
}