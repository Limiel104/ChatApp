package com.example.chatapp.domain.use_case

import com.example.chatapp.domain.model.User

class FilterUsersUseCase {
    operator fun invoke(query: String, userList: List<User>): List<User> {
        val filteredUsers = userList.filter { user ->
            val fullName = user.firstName + " " + user.lastName
            user.firstName.contains(query,true)
                    || user.lastName.contains(query,true)
                    || fullName.contains(query,true)
        }
        return filteredUsers
    }
}