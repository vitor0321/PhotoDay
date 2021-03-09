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
                toast(context, R.string.successfully_upload_image.toString())
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { message ->
                    toast(context, message)
                }
            }
        }

    suspend fun deleteImage(context: Context, dateCalendar: String) {
        try {
            imageRef.child("$IMAGES$dateCalendar").delete()
            withContext(Dispatchers.Main) {
                toast(context, R.string.successfully_delete_image.toString())
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.message?.let { message ->
                    toast(context, message)
                }
            }
        }
    }

    fun listFileDownload(
        callback: (imagesList: List<ItemPhoto>) -> Unit,
        callbackError: (messageError: String) -> Unit
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
                        val listReversed = imagesList.asReversed()
                        callback.invoke(listReversed)
                    }
                }
            }
        } catch (e: Exception) {
            e.message?.let { message -> callbackError.invoke(message) }
        }
    }
}