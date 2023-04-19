package com.example.chatapp.domain.use_case

import com.example.chatapp.data.repository.FakeMessageStorageRepository
import com.example.chatapp.domain.model.Message
import com.example.chatapp.util.Constants.messageCorrect
import com.example.chatapp.util.Constants.user1UID
import com.example.chatapp.util.Constants.userUIDCorrect
import com.example.chatapp.util.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.util.*

class AddMessageUseCaseTest {

    private lateinit var addMessageUseCase: AddMessageUseCase

    @Before
    fun setUp() {
        val fakeMessageStorageRepository = FakeMessageStorageRepository()
        addMessageUseCase = AddMessageUseCase(fakeMessageStorageRepository)
    }

    @Test
    fun addMessageToMessageStorage() = runBlocking {
        val message = Message(
            text = messageCorrect,
            senderUID = userUIDCorrect,
            receiverUID = user1UID,
            date = Date()
        )

        val result = addMessageUseCase(message)
        assertThat(result).isEqualTo(Resource.Success(result = true))
    }
}