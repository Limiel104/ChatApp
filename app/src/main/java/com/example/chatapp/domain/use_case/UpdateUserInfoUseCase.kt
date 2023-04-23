package com.example.chatapp.domain.use_case

import android.net.Uri
import com.example.chatapp.domain.model.User
import com.example.chatapp.domain.repository.UserStorageRepository
import com.example.chatapp.util.Resource

class UpdateUserInfoUseCase(
    private var userStorageRepository: UserStorageRepository
) {
    suspend operator fun invoke(user: User, imageUri: Uri): Resource<Boolean> {
        return userStorageRepository.updateUser(user, imageUri)
    }
}