package com.example.photoday.repository.firebasePhotos

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.photoday.R
import com.example.photoday.constants.IMAGES
import com.example.photoday.ui.model.item.ItemPhoto
import com.example.photoday.ui.model.resource.ResourceItem
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference

class FirebasePhoto(
    private val imageRef : FirebaseStorage
) {

    fun listFileDownload(): LiveData<ResourceItem<List<ItemPhoto>?>> =
        MediatorLiveData<ResourceItem<List<ItemPhoto>?>>().apply {
            try {
                val storageRef = imageRef.reference.child(IMAGES)
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
                            value = ResourceItem(data = listReversed)
                        }
                    }
                }
            } catch (e: Exception) {
                val mediatorKeep = value
                when (value) {
                    null -> value =
                        ResourceItem(data = mediatorKeep?.data, message = R.string.error_api)
                }
            }
        }

    fun uploadImageToStorage(
        dateCalendar: String,
        curFile: Uri?,
    ): LiveData<ResourceItem<Void?>> = MutableLiveData<ResourceItem<Void?>>().apply {
        try {
            curFile?.let {
                imageRef.reference.child("$IMAGES$dateCalendar").putFile(it)
                value = ResourceItem(message = R.string.successfully_upload_image)
            }
        } catch (e: Exception) {
            val liveDataKeep = value
            when (value) {
                null -> value =
                    ResourceItem(data = liveDataKeep?.data, message = R.string.error_api)
            }
        }
    }


    fun deleteImage(
        dateCalendar: String,
    ): LiveData<ResourceItem<Void?>> = MutableLiveData<ResourceItem<Void?>>().apply {
        value = try {
            imageRef.reference.child("$IMAGES$dateCalendar").delete()
            ResourceItem(message = R.string.successfully_delete_image)
        } catch (e: Exception) {
            ResourceItem(message = R.string.error_api)
        }
    }
}