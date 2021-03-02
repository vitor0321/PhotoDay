package com.example.photoday.repository

import android.content.Context
import android.net.Uri
import com.example.photoday.adapter.modelAdapter.ItemPhoto
import com.example.photoday.constants.Utils
import com.example.photoday.repository.firebasePhotos.FirebasePhoto.deleteImage
import com.example.photoday.repository.firebasePhotos.FirebasePhoto.listFileDownload
import com.example.photoday.repository.firebasePhotos.FirebasePhoto.uploadImageToStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object BaseRepositoryPhoto {
    fun baseRepositoryUploadImageToStorage(context: Context, dateCalendar: String, curFile: Uri?) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                uploadImageToStorage(context, dateCalendar, curFile)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let { Utils.toast(context, it.toInt()) }
                }
            }
        }
    }

    fun baseRepositoryListFileDownload(context: Context, callback: (imagesList:List<ItemPhoto>) -> Unit){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                listFileDownload(context){ imagesList ->
                    imagesList
                    callback.invoke(imagesList)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let { Utils.toast(context, it.toInt()) }
                }
            }
        }
    }

    fun baseRepositoryDeleteImage(context: Context, date: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                deleteImage(context, date)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let { Utils.toast(context, it.toInt()) }
                }
            }
        }
    }

}