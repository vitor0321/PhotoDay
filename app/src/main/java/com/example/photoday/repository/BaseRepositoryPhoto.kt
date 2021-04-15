package com.example.photoday.repository

import android.content.Context
import android.net.Uri
import com.example.photoday.repository.firebasePhotos.FirebasePhoto

class BaseRepositoryPhoto(private val repositoryPhoto: FirebasePhoto) {

    fun baseRepositoryListFileDownload() = repositoryPhoto.listFileDownload()

    fun baseRepositoryUploadImageToStorage(
        dateCalendar: String,
        curFile: Uri?,
    ) = repositoryPhoto.uploadImageToStorage(dateCalendar, curFile)

    fun baseRepositoryDeleteImage(date: String, context: Context) =
        repositoryPhoto.deleteImage(date)


}