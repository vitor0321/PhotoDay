package com.example.photoday.repository

import android.content.Context
import android.net.Uri
import com.example.photoday.repository.firebasePhotos.FirebasePhoto.deleteImage
import com.example.photoday.repository.firebasePhotos.FirebasePhoto.listFileDownload
import com.example.photoday.repository.firebasePhotos.FirebasePhoto.uploadImageToStorage

object BaseRepositoryPhoto {
    fun baseRepositoryUploadImageToStorage(context: Context, date: String, curFile: Uri?) {
        uploadImageToStorage(context, date, curFile)
    }

    fun baseRepositoryDeleteImage(context: Context, date: String) {
        deleteImage(context, date)
    }

    fun baseRepositoryListFileDownload(context: Context) {
        listFileDownload(context)
    }
}