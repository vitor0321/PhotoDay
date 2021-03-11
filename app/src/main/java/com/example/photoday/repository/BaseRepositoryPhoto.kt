package com.example.photoday.repository

import android.content.Context
import android.net.Uri
import com.example.photoday.repository.firebasePhotos.FirebasePhoto

class BaseRepositoryPhoto(private val repositoryPhoto: FirebasePhoto = FirebasePhoto) {

    fun baseRepositoryListFileDownload() = repositoryPhoto.listFileDownload()

    fun baseRepositoryUploadImageToStorage(
        dateCalendar: String,
        curFile: Uri?,
        context: Context,
    ) = repositoryPhoto.uploadImageToStorage(dateCalendar, curFile, context)

    fun baseRepositoryDeleteImage(date: String, context: Context) =
        repositoryPhoto.deleteImage(date, context)


}