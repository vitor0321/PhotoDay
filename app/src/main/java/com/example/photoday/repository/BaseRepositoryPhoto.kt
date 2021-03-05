package com.example.photoday.repository

import android.content.Context
import android.net.Uri
import com.example.photoday.adapter.modelAdapter.ItemPhoto
import com.example.photoday.constants.Utils
import com.example.photoday.constants.Utils.toast
import com.example.photoday.repository.firebasePhotos.FirebasePhoto.deleteImage
import com.example.photoday.repository.firebasePhotos.FirebasePhoto.listFileDownload
import com.example.photoday.repository.firebasePhotos.FirebasePhoto.uploadImageToStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object BaseRepositoryPhoto {
    suspend fun baseRepositoryUploadImageToStorage(context: Context, dateCalendar: String, curFile: Uri?) {
        try {
            uploadImageToStorage(context, dateCalendar, curFile)
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { message -> toast(context, message.toInt()) }
            }
        }
    }

    suspend fun baseRepositoryListFileDownload(
        context: Context,
        callback: (imagesList: List<ItemPhoto>) -> Unit,
    ) {
        try {
            listFileDownload(context) { imagesList ->
                callback.invoke(imagesList)
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { message -> toast(context, message.toInt()) }
            }
        }
    }

    suspend fun baseRepositoryDeleteImage(context: Context, date: String) {
            try {
                deleteImage(context, date)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let { message -> toast(context, message.toInt()) }
                }
            }
    }
}