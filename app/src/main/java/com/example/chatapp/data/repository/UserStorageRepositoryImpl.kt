package com.example.chatapp.data.repository

import android.net.Uri
import com.example.chatapp.domain.model.User
import com.example.chatapp.domain.repository.UserStorageRepository
import com.example.chatapp.util.Constants.FIRST_NAME
import com.example.chatapp.util.Constants.LAST_NAME
import com.example.chatapp.util.Constants.PROFILE_PICTURE_URL
import com.example.chatapp.util.Constants.USER_UID
import com.example.chatapp.util.Resource
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserStorageRepositoryImpl @Inject constructor(
    private val usersRef: CollectionReference
): UserStorageRepository {

    override suspend fun addUser(user: User, imageUri: Uri): Resource<Boolean> {
        return try {
            usersRef.document().set(
                mapOf(
                    USER_UID to user.userUID,
                    FIRST_NAME to user.firstName,
                    LAST_NAME to user.lastName,
                    PROFILE_PICTURE_URL to imageUri
                )
            ).await()
            Resource.Success(true)
        }
        catch (e: Exception) {
            Resource.Error(e.localizedMessage as String)
        }
    }

    override fun getUser(userUID: String) = callbackFlow {
        val snapshotListener = usersRef
            .whereEqualTo(USER_UID,userUID)
            .addSnapshotListener { snapshot, e ->
                val response = if (snapshot != null) {
                    val user = snapshot.toObjects(User::class.java)
                    Resource.Success(user)
                }
                else {
                    Resource.Error(e!!.localizedMessage as String)
                }
                trySend(response)
            }

        awaitClose {
            snapshotListener.remove()
        }
    }

    override fun getUsers(currentUserUID: String) = callbackFlow {
        val snapshotListener = usersRef
            .whereNotEqualTo(USER_UID,currentUserUID)
            .addSnapshotListener { snapshot, e ->
                val response = if (snapshot != null) {
                    val users = snapshot.toObjects(User::class.java)
                    Resource.Success(users)
                }
                else {
                    Resource.Error(e!!.localizedMessage as String)
                }
                trySend(response)
            }

        awaitClose {
            snapshotListener.remove()
        }
    }
}