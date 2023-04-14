package com.example.chatapp.domain.use_case

import com.example.chatapp.data.repository.FakeUserStorageRepository
import com.example.chatapp.domain.model.User
import com.example.chatapp.util.Constants.firstNameCorrect
import com.example.chatapp.util.Constants.lastNameCorrect
import com.example.chatapp.util.Constants.userUidCorrect
import com.example.chatapp.util.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetUsersUseCaseTest {

    private lateinit var getUsersUseCase: GetUsersUseCase
    private lateinit var userList: List<User>

    @Before
    fun setUp() {
        val fakeUserStorageRepository = FakeUserStorageRepository()
        getUsersUseCase = GetUsersUseCase(fakeUserStorageRepository)

        val currentUser = User(
            userUID = userUidCorrect,
            firstName = firstNameCorrect,
            lastName = lastNameCorrect,
            avatarURL = "avatarUrl"
        )

        val user1 = User(
            userUID = "1234567321890",
            firstName = "Janet",
            lastName = "Hurt",
            avatarURL = "avatarUrl"
        )

        val user2 = User(
            userUID = "23846468267832",
            firstName = "Denis",
            lastName = "Turner",
            avatarURL = "avatarUrl"
        )

        val user3 = User(
            userUID = "3246783264782",
            firstName = "Conor",
            lastName = "Smith",
            avatarURL = "avatarUrl"
        )

        userList = listOf(user1,user2,user3)

        runBlocking {
            fakeUserStorageRepository.addUser(currentUser)
            fakeUserStorageRepository.addUser(user1)
            fakeUserStorageRepository.addUser(user2)
            fakeUserStorageRepository.addUser(user3)
        }
    }

    @Test
    fun getUsers_currentUserIsNotIncluded() = runBlocking {
        val currentUserUid = userUidCorrect
        val users = getUsersUseCase(currentUserUid).first()

        assertThat(users).isEqualTo(Resource.Success(result = userList))
    }
}