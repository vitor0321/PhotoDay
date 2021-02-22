package com.example.photoday.repository.firebasePhotos

import android.content.Context
import android.net.Uri
import com.example.photoday.R
import com.example.photoday.constants.IMAGE
import com.example.photoday.constants.Utils.toast
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.*

object FirebasePhoto {
    private val imageRef = Firebase.storage.reference

    fun uploadImageToStorage(context: Context, date: String, curFile: Uri?) = CoroutineScope(Dispatchers.IO).launch {
        try {
            curFile?.let {
                imageRef.child("$IMAGE/$date").putFile(it)
                withContext(Dispatchers.Main) {
                    toast(context, R.string.successfully_upload_image)
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { toast(context, it.toInt()) }
            }
        }
    }

    fun deleteImage(context: Context, date: String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            imageRef.child("$IMAGE/$date").delete()
            withContext(Dispatchers.Main) {
                toast(context, R.string.successfully_delete_image)
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let {
                    toast(context, it.toInt())
                }
            }
        }
    }

    fun listFileDownload(context: Context) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val images = imageRef.child("$IMAGE/").listAll()

        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let {
                    toast(context, it.toInt())
                }
            }
        }
    }
}