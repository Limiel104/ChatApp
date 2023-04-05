package com.example.chatapp.data.repository

import com.example.chatapp.domain.model.User
import com.example.chatapp.domain.repository.UserStorageRepository
import com.example.chatapp.util.Constants.USER_UID
import com.example.chatapp.util.Resource
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserStorageRepositoryImpl @Inject constructor(
    private val usersRef: CollectionReference
) : UserStorageRepository {

    override suspend fun addUser(
        userUID: String,
        firstName: String,
        lastName: String,
        avatarURL: String
    ): Resource<Boolean> {
        return try {
            val user = User(
                userUID = userUID,
                firstName = firstName,
                lastName = lastName,
                avatarURL = avatarURL
            )
            usersRef.document().set(user).await()
            Resource.Success(true)
        }
        catch (e: Exception) {
            Resource.Error(e.localizedMessage as String)
        }
    }

    override fun getUserList(currentUserUID: String) = callbackFlow {
        val snapshotListener = usersRef
            .whereNotEqualTo(USER_UID,currentUserUID)
            .addSnapshotListener { snapshot, e ->
                val response = if (snapshot != null) {
                    val users = snapshot.toObjects(User::class.java)
                    Resource.Success(users)
                } else {
                    Resource.Error(e!!.localizedMessage as String)
                }
                trySend(response)
            }

        awaitClose {
            snapshotListener.remove()
        }
    }
}