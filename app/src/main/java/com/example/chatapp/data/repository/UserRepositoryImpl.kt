package com.example.chatapp.data.repository

import com.example.chatapp.domain.model.User
import com.example.chatapp.domain.repository.UserRepository
import com.example.chatapp.util.Resource
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val usersRef: CollectionReference
) : UserRepository {

    override fun getUserList() = callbackFlow {
        val snapshotListener = usersRef
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