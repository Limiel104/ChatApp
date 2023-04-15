package com.example.chatapp.domain.use_case

import com.example.chatapp.data.repository.FakeUserStorageRepository
import com.example.chatapp.domain.model.User
import com.example.chatapp.util.Constants.avatarURLCorrect
import com.example.chatapp.util.Constants.firstNameCorrect
import com.example.chatapp.util.Constants.lastNameCorrect
import com.example.chatapp.util.Constants.user1AvatarURL
import com.example.chatapp.util.Constants.user1FirstName
import com.example.chatapp.util.Constants.user1LastName
import com.example.chatapp.util.Constants.user1UID
import com.example.chatapp.util.Constants.user2AvatarURL
import com.example.chatapp.util.Constants.user2FirstName
import com.example.chatapp.util.Constants.user2LastName
import com.example.chatapp.util.Constants.user2UID
import com.example.chatapp.util.Constants.user3AvatarURL
import com.example.chatapp.util.Constants.user3FirstName
import com.example.chatapp.util.Constants.user3LastName
import com.example.chatapp.util.Constants.user3UID
import com.example.chatapp.util.Constants.userUIDCorrect
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
            userUID = userUIDCorrect,
            firstName = firstNameCorrect,
            lastName = lastNameCorrect,
            avatarURL = avatarURLCorrect
        )

        val user1 = User(
            userUID = user1UID,
            firstName = user1FirstName,
            lastName = user1LastName,
            avatarURL = user1AvatarURL
        )

        val user2 = User(
            userUID = user2UID,
            firstName = user2FirstName,
            lastName = user2LastName,
            avatarURL = user2AvatarURL
        )

        val user3 = User(
            userUID = user3UID,
            firstName = user3FirstName,
            lastName = user3LastName,
            avatarURL = user3AvatarURL
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
        val currentUserUid = userUIDCorrect
        val users = getUsersUseCase(currentUserUid).first()

        assertThat(users).isEqualTo(Resource.Success(result = userList))
    }
}