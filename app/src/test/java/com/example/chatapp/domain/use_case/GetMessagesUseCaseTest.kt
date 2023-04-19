package com.example.chatapp.domain.use_case

import com.example.chatapp.data.repository.FakeMessageStorageRepository
import com.example.chatapp.domain.model.Message
import com.example.chatapp.util.Constants.messageCorrect
import com.example.chatapp.util.Constants.user1UID
import com.example.chatapp.util.Constants.user2UID
import com.example.chatapp.util.Constants.user3UID
import com.example.chatapp.util.Constants.user4UID
import com.example.chatapp.util.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.util.*

class GetMessagesUseCaseTest {

    private lateinit var getMessagesUseCase: GetMessagesUseCase
    private lateinit var messageList: List<Message>

    @Before
    fun setUp() {
        val fakeMessageStorageRepository = FakeMessageStorageRepository()
        getMessagesUseCase = GetMessagesUseCase(fakeMessageStorageRepository)

        val message1 = Message(
            text = messageCorrect,
            senderUID = user1UID,
            receiverUID = user2UID,
            date = Date()
        )

        val message2 = Message(
            text = messageCorrect,
            senderUID = user4UID,
            receiverUID = user2UID,
            date = Date()
        )

        val message3 = Message(
            text = messageCorrect,
            senderUID = user1UID,
            receiverUID = user3UID,
            date = Date()
        )

        val message4= Message(
            text = messageCorrect,
            senderUID = user2UID,
            receiverUID = user1UID,
            date = Date()
        )

        val message5 = Message(
            text = messageCorrect,
            senderUID = user1UID,
            receiverUID = user2UID,
            date = Date()
        )

        val message6 = Message(
            text = messageCorrect,
            senderUID = user3UID,
            receiverUID = user2UID,
            date = Date()
        )

        val message7 = Message(
            text = messageCorrect,
            senderUID = user4UID,
            receiverUID = user3UID,
            date = Date()
        )

        messageList = listOf(message1,message4,message5)

        runBlocking {
            fakeMessageStorageRepository.addMessage(message1)
            fakeMessageStorageRepository.addMessage(message2)
            fakeMessageStorageRepository.addMessage(message3)
            fakeMessageStorageRepository.addMessage(message4)
            fakeMessageStorageRepository.addMessage(message5)
            fakeMessageStorageRepository.addMessage(message6)
            fakeMessageStorageRepository.addMessage(message7)
        }
    }

    @Test
    fun getMessages_messagesOnlyFromSpecifiedUsers() = runBlocking {
        val messages = getMessagesUseCase(user1UID, user2UID).first()

        assertThat(messages).isEqualTo(Resource.Success(result = messageList))
    }
}