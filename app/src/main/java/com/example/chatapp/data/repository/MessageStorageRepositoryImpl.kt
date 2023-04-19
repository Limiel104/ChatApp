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
            val messageId = messagesRef.document().id
            val newMessage = Message(
                text = message.text,
                senderUID = message.senderUID,
                receiverUID = message.receiverUID,
                date = message.date,
                messageId = messageId
            )
            messagesRef.document(messageId).set(newMessage).await()
            Resource.Success(true)
        }
        catch (e: Exception) {
            Resource.Error(e.localizedMessage as String)
        }
    }

    override fun getMessages(
        currentUserUID: String,
        chatParticipantUserUID: String
    ) = callbackFlow {
        val snapshotListener = messagesRef
            .whereIn(SENDER_UID, listOf(currentUserUID,chatParticipantUserUID))
            .whereIn(RECEIVER_UID, listOf(currentUserUID,chatParticipantUserUID))
            .orderBy(DATE,Query.Direction.DESCENDING)
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

    override suspend fun deleteMessage(messageId: String): Resource<Boolean> {
         return try {
             messagesRef.document(messageId).delete().await()
             Resource.Success(true)
         }
         catch (e: Exception) {
             Resource.Error(e.localizedMessage as String)
         }
    }
}