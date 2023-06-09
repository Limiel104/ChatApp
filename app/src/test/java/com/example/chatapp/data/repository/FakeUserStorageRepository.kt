package com.example.chatapp.data.repository

import com.example.chatapp.domain.model.User
import com.example.chatapp.domain.repository.UserStorageRepository
import com.example.chatapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeUserStorageRepository: UserStorageRepository {

    private val userList = mutableListOf<User>()

    override suspend fun addUser(user: User): Resource<Boolean> {
        userList.add(user)
        return Resource.Success(true)
    }

    override fun getUser(userUID: String): Flow<Resource<List<User>>> {
        val user = userList.filter { user ->
            user.userUID == userUID
        }
        return flow{ emit(Resource.Success(user)) }
    }

    override fun getUsers(currentUserUID: String): Flow<Resource<List<User>>> {
        val usersWithoutCurrentUser = userList.filter { user ->
            user.userUID != currentUserUID
        }
        return flow{ emit(Resource.Success(usersWithoutCurrentUser)) }
    }
}