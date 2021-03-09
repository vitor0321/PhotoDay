package com.example.photoday.repository

import android.net.Uri
import com.example.photoday.adapter.modelAdapter.ItemPhoto
import com.example.photoday.repository.firebasePhotos.FirebasePhoto

class BaseRepositoryPhoto(private val repositoryPhoto: FirebasePhoto = FirebasePhoto) {

    fun baseRepositoryUploadImageToStorage(
        dateCalendar: String,
        curFile: Uri?,
        callbackMessage: (message: String) -> Unit,
    ) {
        try {
            repositoryPhoto.uploadImageToStorage(dateCalendar,
                curFile,
                callbackMessage = { message -> callbackMessage.invoke(message) })
        } catch (e: Exception) {
            e.message?.let { message -> callbackMessage.invoke(message) }
        }
    }

    fun baseRepositoryListFileDownload(
        callback: (imagesList: List<ItemPhoto>) -> Unit,
        callbackMessage: (messageError: String) -> Unit,
    ) {
        try {
            repositoryPhoto.listFileDownload(
                callback = { imagesList: List<ItemPhoto> -> callback.invoke(imagesList) },
                callbackError = { message: String -> callbackMessage(message) })

        } catch (e: Exception) {
            e.message?.let { message -> callbackMessage(message) }
        }
    }

    fun baseRepositoryDeleteImage(
        date: String,
        callbackMessage: (message: String) -> Unit,
    ) {
        try {
            repositoryPhoto.deleteImage(date,
                callbackMessage = { message: String -> callbackMessage(message) })
        } catch (e: Exception) {
            e.message?.let { message -> callbackMessage.invoke(message) }
        }
    }
}