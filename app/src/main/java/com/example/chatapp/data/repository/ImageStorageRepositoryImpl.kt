package com.example.chatapp.data.repository

import android.net.Uri
import com.example.chatapp.domain.repository.ImageStorageRepository
import com.example.chatapp.util.Constants.IMAGES
import com.example.chatapp.util.Resource
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ImageStorageRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage
): ImageStorageRepository {
    override suspend fun addImage(
        userUID: String,
        imageUri: Uri
    ): Resource<Uri> {
        return try {
            val fileName = "$userUID.jpg"
            val imageUrl = storage.reference
                .child(IMAGES)
                .child(fileName)
                .putFile(imageUri).await()
                .storage.downloadUrl.await()
            Resource.Success(imageUrl)
        }
        catch (e: Exception) {
            Resource.Error(e.localizedMessage as String)
        }
    }
}