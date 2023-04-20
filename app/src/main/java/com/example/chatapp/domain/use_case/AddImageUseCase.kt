package com.example.chatapp.domain.use_case

import android.net.Uri
import com.example.chatapp.domain.repository.ImageStorageRepository
import com.example.chatapp.util.Resource

class AddImageUseCase(
    private val imageStorageRepository: ImageStorageRepository
) {
    suspend operator fun invoke(
        userUID: String,
        imageUri: Uri
    ): Resource<Uri> {
        return imageStorageRepository.addImage(userUID, imageUri)
    }
}