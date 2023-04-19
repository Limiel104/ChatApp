package com.example.chatapp.domain.use_case

import com.example.chatapp.data.repository.FakeMessageStorageRepository
import com.example.chatapp.domain.model.Message
import com.example.chatapp.util.Constants
import com.example.chatapp.util.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.util.*

class DeleteMessageUseCaseTest {

    private lateinit var deleteMessageUseCase: DeleteMessageUseCase

    @Before
    fun setUp() {
        val fakeMessageStorageRepository = FakeMessageStorageRepository()
        deleteMessageUseCase = DeleteMessageUseCase(fakeMessageStorageRepository)

        val message1 = Message(
            text = Constants.messageCorrect,
            senderUID = Constants.user1UID,
            receiverUID = Constants.user2UID,
            date = Date(),
            messageId = "qwuieyqwviqv21"
        )

        runBlocking {
            fakeMessageStorageRepository.addMessage(message1)
        }
    }

    @Test
    fun deleteMessageFromMessageStorage() = runBlocking {
        val messageId = "qwuieyqwviqv21"
        val result = deleteMessageUseCase(messageId)
        assertThat(result).isEqualTo(Resource.Success(result = true))
    }
}