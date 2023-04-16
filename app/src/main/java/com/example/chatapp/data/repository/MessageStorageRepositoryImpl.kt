package com.example.chatapp.data.repository

import com.example.chatapp.domain.model.Message
import com.example.chatapp.domain.repository.MessageStorageRepository
import com.example.chatapp.util.Constants.DATE
import com.example.chatapp.util.Constants.RECEIVER_UID
import com.example.chatapp.util.Constants.SENDER_UID
import com.example.chatapp.util.Resource
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MessageStorageRepositoryImpl @Inject constructor(
    private val messagesRef: CollectionReference
): MessageStorageRepository {

    override suspend fun addMessage(message: Message): Resource<Boolean> {
        return try {
            messagesRef.document().set(message).await()
            Resource.Success(true)
        }
        catch (e: Exception) {
            Resource.Error(e.localizedMessage as String)
        }
    }

    override fun getMessages(
        senderUID: String,
        receiverUID: String
    ) = callbackFlow {
        val snapshotListener = messagesRef
            .whereIn(SENDER_UID, listOf(senderUID,receiverUID))
            .whereIn(RECEIVER_UID, listOf(senderUID,receiverUID))
            .orderBy(DATE,Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, e ->
                val response = if (snapshot != null) {
                    val messages = snapshot.toObjects(Message::class.java)
                    Resource.Success(messages)
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