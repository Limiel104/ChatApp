package com.example.chatapp.domain.use_case

import com.example.chatapp.data.repository.FakeUserStorageRepository
import com.example.chatapp.domain.model.User
import com.example.chatapp.util.Constants.avatarURLCorrect
import com.example.chatapp.util.Constants.firstNameCorrect
import com.example.chatapp.util.Constants.lastNameCorrect
import com.example.chatapp.util.Constants.userUIDCorrect
import com.example.chatapp.util.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddUserUseCaseTest {

    private lateinit var addUserUseCase: AddUserUseCase

    @Before
    fun setUp() {
        val fakeUserStorageRepository = FakeUserStorageRepository()
        addUserUseCase = AddUserUseCase(fakeUserStorageRepository)
    }

    @Test
    fun addUserToUserStorage() = runBlocking {
        val user = User(
            userUID = userUIDCorrect,
            firstName = firstNameCorrect,
            lastName = lastNameCorrect,
            avatarURL = avatarURLCorrect
        )

        val result = addUserUseCase(user)
        assertThat(result).isEqualTo(Resource.Success(result = true))
    }
}