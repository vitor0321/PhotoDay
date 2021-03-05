package com.example.photoday.repository.firebasePhotos

import android.content.Context
import android.net.Uri
import com.example.photoday.R
import com.example.photoday.adapter.modelAdapter.ItemPhoto
import com.example.photoday.constants.IMAGES
import com.example.photoday.constants.Utils.toast
import com.google.android.gms.tasks.Task
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object FirebasePhoto {
    private val imageRef = Firebase.storage.reference

    suspend fun uploadImageToStorage(context: Context, dateCalendar: String, curFile: Uri?) =
        try {
            curFile?.let {
                imageRef.child("$IMAGES$dateCalendar").putFile(it)
                toast(context, R.string.successfully_upload_image)
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { message ->
                    toast(context, message.toInt())
                }
            }
        }

    suspend fun deleteImage(context: Context, dateCalendar: String) {
        try {
            imageRef.child("$IMAGES$dateCalendar").delete()
            withContext(Dispatchers.Main) {
                toast(context, R.string.successfully_delete_image)
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { message ->
                    toast(context, message.toInt())
                }
            }
        }
    }

    suspend fun listFileDownload(
        context: Context,
        callback: (imagesList: List<ItemPhoto>) -> Unit,
    ) {
        try {
            val storageRef = imageRef.child("$IMAGES")
            val imagesList: ArrayList<ItemPhoto> = ArrayList()
            val listAllTask: Task<ListResult> = storageRef.listAll()
            listAllTask.addOnCompleteListener { result ->
                val items: List<StorageReference> = result.result!!.items
                //add cycle for add image url to list
                items.forEachIndexed { _, item ->
                    item.downloadUrl.addOnSuccessListener { itemUri ->
                        item.name
                        imagesList.add(ItemPhoto(item.name, itemUri.toString()))
                    }.addOnCompleteListener {
                        imagesList.sortBy { it.dateCalendar }
                        callback.invoke(imagesList)
                    }
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { message ->
                    toast(context, message.toInt())
                }
            }
        }
    }
}