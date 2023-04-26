package com.example.chatapp.domain.use_case

import android.util.Log
import com.example.chatapp.domain.model.Message

class GetLastMessagesUseCase {
    operator fun invoke(allMessages: List<Message>, currentUserUID: String): Map<String, Message> {
        val allMessagesGroupedByUsersAndLimitedToOne = allMessages.groupBy { Pair(it.senderUID, it.receiverUID) }.mapValues { it.value.take(1) }

        val allMessagesGroupedByUsersAndLimitedToOneAsMessageList = mutableListOf<Message>()
        for (group in allMessagesGroupedByUsersAndLimitedToOne) {
            allMessagesGroupedByUsersAndLimitedToOneAsMessageList.add(group.value[0])
        }
        Log.i("TAG",allMessagesGroupedByUsersAndLimitedToOneAsMessageList.toString())

        val lastMessageByChatParticipant = mutableMapOf<String, Message>()
        for (message in allMessagesGroupedByUsersAndLimitedToOneAsMessageList) {
            if(message.receiverUID != currentUserUID) {
                if(lastMessageByChatParticipant[message.receiverUID] == null) {
                    lastMessageByChatParticipant[message.receiverUID] = message
                }
                else if(lastMessageByChatParticipant[message.receiverUID]!!.date < message.date) {
                    lastMessageByChatParticipant[message.receiverUID] = message
                }
            }
            else {
                if(lastMessageByChatParticipant[message.senderUID] == null) {
                    lastMessageByChatParticipant[message.senderUID] = message
                }
                else if(lastMessageByChatParticipant[message.senderUID]!!.date < message.date) {
                    lastMessageByChatParticipant[message.senderUID] = message
                }
            }
        }
        Log.i("TAG", lastMessageByChatParticipant.toString())

        return lastMessageByChatParticipant
    }
}