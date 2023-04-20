package com.example.chatapp.domain.use_case

import android.net.Uri
import com.example.chatapp.domain.model.User
import com.example.chatapp.domain.repository.UserStorageRepository
import com.example.chatapp.util.Resource

class AddUserUseCase(
    private val userStorageRepository: UserStorageRepository
) {
    suspend operator fun invoke(user: User, imageUri: Uri): Resource<Boolean> {
        return userStorageRepository.addUser(user, imageUri)
    }
}