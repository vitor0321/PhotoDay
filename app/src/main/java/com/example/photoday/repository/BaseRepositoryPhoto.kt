package com.example.photoday.repository

import android.content.Context
import android.net.Uri
import com.example.photoday.adapter.modelAdapter.ItemPhoto
import com.example.photoday.constants.Utils.toast
import com.example.photoday.repository.firebasePhotos.FirebasePhoto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BaseRepositoryPhoto(private val repositoryPhoto: FirebasePhoto = FirebasePhoto) {

    suspend fun baseRepositoryUploadImageToStorage(
        context: Context,
        dateCalendar: String,
        curFile: Uri?,
    ) {
        try {
            repositoryPhoto.uploadImageToStorage(context, dateCalendar, curFile)
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { message -> toast(context, message) }
            }
        }
    }

    suspend fun baseRepositoryListFileDownload(
        context: Context,
        callback: (imagesList: List<ItemPhoto>) -> Unit,
        callbackError: (messageError: String) -> Unit,
    ) {
        try {
            repositoryPhoto.listFileDownload(
                callback = { imagesList: List<ItemPhoto> -> callback.invoke(imagesList) },
                callbackError = { messageError: String -> callbackError(messageError) })

        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { message -> toast(context, message) }
            }
        }
    }

    suspend fun baseRepositoryDeleteImage(context: Context, date: String) {
            try {
                repositoryPhoto.deleteImage(context, date)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let { message -> toast(context, message) }
                }
            }
    }
}