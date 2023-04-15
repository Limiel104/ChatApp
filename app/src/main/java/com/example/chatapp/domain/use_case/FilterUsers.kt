package com.example.chatapp.domain.use_case

import com.example.chatapp.domain.model.User

class FilterUsers {
    operator fun invoke(query: String, userList: List<User>): List<User> {
        val filteredUsers = userList.filter { user ->
            user.firstName.contains(query,true) || user.lastName.contains(query,true)
        }
        return filteredUsers
    }
}