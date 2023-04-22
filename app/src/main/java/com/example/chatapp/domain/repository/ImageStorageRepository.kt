package com.example.chatapp.domain.repository

import android.net.Uri
import com.example.chatapp.util.Resource

interface ImageStorageRepository {

    suspend fun addImage(userUID: String, imageUri: Uri): Resource<Uri>
}