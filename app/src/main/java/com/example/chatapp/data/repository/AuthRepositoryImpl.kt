package com.example.chatapp.data.repository

import com.example.chatapp.domain.repository.AuthRepository
import com.example.chatapp.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): AuthRepository {
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun login(
        email: String,
        password: String
    ): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email,password).await()
            Resource.Success(result.user!!)
        }
        catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.localizedMessage!!)
        }
    }

    override suspend fun signup(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email,password).await()
            val name = "$firstName $lastName"
            result?.user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())
            Resource.Success(result.user!!)
        }
        catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.localizedMessage!!)
        }
    }

    override suspend fun updateEmail(email: String): Resource<Boolean> {
        return try {
            firebaseAuth.currentUser!!.updateEmail(email).await()
            Resource.Success(true)
        }
        catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.localizedMessage!!)
        }
    }

    override suspend fun updatePassword(password: String): Resource<Boolean> {
        return try {
            firebaseAuth.currentUser!!.updatePassword(password).await()
            Resource.Success(true)
        }
        catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.localizedMessage!!)
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}