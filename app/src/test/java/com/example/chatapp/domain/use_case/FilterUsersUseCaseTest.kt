package com.example.chatapp.domain.use_case

import com.example.chatapp.domain.model.User
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
import com.example.chatapp.util.Constants.user4AvatarURL
import com.example.chatapp.util.Constants.user4FirstName
import com.example.chatapp.util.Constants.user4LastName
import com.example.chatapp.util.Constants.user4UID
import com.example.chatapp.util.Constants.user5AvatarURL
import com.example.chatapp.util.Constants.user5FirstName
import com.example.chatapp.util.Constants.user5LastName
import com.example.chatapp.util.Constants.user5UID
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class FilterUsersUseCaseTest {

    private lateinit var filterUsersUseCase: FilterUsersUseCase
    private lateinit var userList: MutableList<User>

    @Before
    fun setUp() {
        filterUsersUseCase = FilterUsersUseCase()

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

        val user4 = User(
            userUID = user4UID,
            firstName = user4FirstName,
            lastName = user4LastName,
            avatarURL = user4AvatarURL
        )

        val user5 = User(
            userUID = user5UID,
            firstName = user5FirstName,
            lastName = user5LastName,
            avatarURL = user5AvatarURL
        )

        userList = mutableListOf(user1, user2, user3, user4, user5)
    }

    @Test
    fun filterUserList_queryUpperCase() {
        val query = "HU"
        val filteredUsers = filterUsersUseCase(query, userList)
        val resultUser = User(
            userUID = user1UID,
            firstName = user1FirstName,
            lastName = user1LastName,
            avatarURL = user1AvatarURL
        )

        assertThat(filteredUsers).isNotEmpty()
        assertThat(filteredUsers.size).isEqualTo(1)
        assertThat(filteredUsers).contains(resultUser)
    }

    @Test
    fun filterUserList_queryLowerCase() {
        val query = "hU"
        val filteredUsers = filterUsersUseCase(query, userList)
        val resultUser = User(
            userUID = user1UID,
            firstName = user1FirstName,
            lastName = user1LastName,
            avatarURL = user1AvatarURL
        )

        assertThat(filteredUsers).isNotEmpty()
        assertThat(filteredUsers.size).isEqualTo(1)
        assertThat(filteredUsers).contains(resultUser)
    }

    @Test
    fun filterUserList_queryMixed() {
        val query = "hU"
        val filteredUsers = filterUsersUseCase(query, userList)
        val resultUser = User(
            userUID = user1UID,
            firstName = user1FirstName,
            lastName = user1LastName,
            avatarURL = user1AvatarURL
        )

        assertThat(filteredUsers).isNotEmpty()
        assertThat(filteredUsers.size).isEqualTo(1)
        assertThat(filteredUsers).contains(resultUser)
    }

    @Test
    fun filterUserList_returnTwoUsers_queryUpperCase() {
        val query = "MI"
        val filteredUsers = filterUsersUseCase(query, userList)
        val resultUserList =
            listOf(
                User(
                    userUID = user1UID,
                    firstName = user1FirstName,
                    lastName = user1LastName,
                    avatarURL = user1AvatarURL
                ),
                User(
                    userUID = user3UID,
                    firstName = user3FirstName,
                    lastName = user3LastName,
                    avatarURL = user3AvatarURL
                )
            )

        assertThat(filteredUsers).isNotEmpty()
        assertThat(filteredUsers.size).isEqualTo(2)
        assertThat(filteredUsers).containsExactlyElementsIn(resultUserList)
    }

    @Test
    fun filterUserList_returnTwoUsers_queryLowerCase() {
        val query = "mi"
        val filteredUsers = filterUsersUseCase(query, userList)
        val resultUserList =
            listOf(
                User(
                    userUID = user1UID,
                    firstName = user1FirstName,
                    lastName = user1LastName,
                    avatarURL = user1AvatarURL
                ),
                User(
                    userUID = user3UID,
                    firstName = user3FirstName,
                    lastName = user3LastName,
                    avatarURL = user3AvatarURL
                )
            )

        assertThat(filteredUsers).isNotEmpty()
        assertThat(filteredUsers.size).isEqualTo(2)
        assertThat(filteredUsers).containsExactlyElementsIn(resultUserList)
    }

    @Test
    fun filterUserList_returnTwoUsers_queryMixed() {
        val query = "mI"
        val filteredUsers = filterUsersUseCase(query, userList)
        val resultUserList =
            listOf(
                User(
                    userUID = user1UID,
                    firstName = user1FirstName,
                    lastName = user1LastName,
                    avatarURL = user1AvatarURL
                ),
                User(
                    userUID = user3UID,
                    firstName = user3FirstName,
                    lastName = user3LastName,
                    avatarURL = user3AvatarURL
                )
            )

        assertThat(filteredUsers).isNotEmpty()
        assertThat(filteredUsers.size).isEqualTo(2)
        assertThat(filteredUsers).containsExactlyElementsIn(resultUserList)
    }


    @Test
    fun filterUserList_returnEmptyList() {
        val query = "John"
        val filteredUsers = filterUsersUseCase(query, userList)

        assertThat(filteredUsers).isEmpty()
    }

    @Test
    fun filterUserListByFullName_returnEmptyList() {
        val query = "John Smith"
        val filteredUsers = filterUsersUseCase(query, userList)

        assertThat(filteredUsers).isEmpty()
    }

    @Test
    fun filterUserListByFullName_queryUpperCase_returnUser() {
        val query = "CONOR SMITH"
        val filteredUsers = filterUsersUseCase(query, userList)
        val resultUser = User(
            userUID = user3UID,
            firstName = user3FirstName,
            lastName = user3LastName,
            avatarURL = user3AvatarURL
        )

        assertThat(filteredUsers).isNotEmpty()
        assertThat(filteredUsers.size).isEqualTo(1)
        assertThat(filteredUsers).contains(resultUser)
    }

    @Test
    fun filterUserListByFullName_queryLowerCase_returnUser() {
        val query = "Conor Smith"
        val filteredUsers = filterUsersUseCase(query, userList)
        val resultUser = User(
            userUID = user3UID,
            firstName = user3FirstName,
            lastName = user3LastName,
            avatarURL = user3AvatarURL
        )

        assertThat(filteredUsers).isNotEmpty()
        assertThat(filteredUsers.size).isEqualTo(1)
        assertThat(filteredUsers).contains(resultUser)
    }

    @Test
    fun filterUserListByFullName_queryMixed_returnUser() {
        val query = "Conor Smith"
        val filteredUsers = filterUsersUseCase(query, userList)
        val resultUser = User(
            userUID = user3UID,
            firstName = user3FirstName,
            lastName = user3LastName,
            avatarURL = user3AvatarURL
        )

        assertThat(filteredUsers).isNotEmpty()
        assertThat(filteredUsers.size).isEqualTo(1)
        assertThat(filteredUsers).contains(resultUser)
    }

    @Test
    fun filterUserListByFirstName_returnTwoUserWithSameName() {
        val query = "Julia"
        val filteredUsers = filterUsersUseCase(query, userList)
        val resultUserList =
            listOf(
                User(
                    userUID = user4UID,
                    firstName = user4FirstName,
                    lastName = user4LastName,
                    avatarURL = user4AvatarURL
                ),
                User(
                    userUID = user5UID,
                    firstName = user5FirstName,
                    lastName = user5LastName,
                    avatarURL = user5AvatarURL
                )
            )

        assertThat(filteredUsers).isNotEmpty()
        assertThat(filteredUsers.size).isEqualTo(2)
        assertThat(filteredUsers).containsExactlyElementsIn(resultUserList)
    }

    @Test
    fun filterUserListByLastName_returnTwoUserWithSameName() {
        val query = "Porter"
        val filteredUsers = filterUsersUseCase(query, userList)
        val resultUserList =
            listOf(
                User(
                    userUID = user4UID,
                    firstName = user4FirstName,
                    lastName = user4LastName,
                    avatarURL = user4AvatarURL
                ),
                User(
                    userUID = user5UID,
                    firstName = user5FirstName,
                    lastName = user5LastName,
                    avatarURL = user5AvatarURL
                )
            )

        assertThat(filteredUsers).isNotEmpty()
        assertThat(filteredUsers.size).isEqualTo(2)
        assertThat(filteredUsers).containsExactlyElementsIn(resultUserList)
    }

    @Test
    fun filterUserListByFullName_returnTwoUserWithSameName() {
        val query = "Julia Porter"
        val filteredUsers = filterUsersUseCase(query, userList)
        val resultUserList =
            listOf(
                User(
                    userUID = user4UID,
                    firstName = user4FirstName,
                    lastName = user4LastName,
                    avatarURL = user4AvatarURL
                ),
                User(
                    userUID = user5UID,
                    firstName = user5FirstName,
                    lastName = user5LastName,
                    avatarURL = user5AvatarURL
                )
            )

        assertThat(filteredUsers).isNotEmpty()
        assertThat(filteredUsers.size).isEqualTo(2)
        assertThat(filteredUsers).containsExactlyElementsIn(resultUserList)
    }
}