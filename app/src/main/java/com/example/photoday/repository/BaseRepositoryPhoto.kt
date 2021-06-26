package com.example.photoday.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.photoday.repository.firebasePhotos.FirebasePhoto
import com.example.photoday.ui.model.item.ItemPhoto
import com.example.photoday.ui.model.resource.ResourceItem
import kotlinx.coroutines.Dispatchers

class BaseRepositoryPhoto(private val repositoryPhoto: FirebasePhoto) {

    fun baseRepositoryListFileDownload()=repositoryPhoto.listFileDownload()

    fun baseRepositoryUploadImageToStorage(dateCalendar: String, curFile: Uri?) =
        repositoryPhoto.uploadImageToStorage(dateCalendar, curFile)

    fun baseRepositoryDeleteImage(date: String) =
        repositoryPhoto.deleteImage(date)
}