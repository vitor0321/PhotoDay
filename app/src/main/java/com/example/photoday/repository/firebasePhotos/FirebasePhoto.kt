package com.example.photoday.repository.firebasePhotos

import ItemPhoto
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.photoday.R
import com.example.photoday.constants.IMAGES
<<<<<<< HEAD
import com.example.photoday.model.resource.ResourceItem
=======
import com.example.photoday.ui.model.resource.ResourceItem
import com.example.photoday.ui.model.adapter.ItemPhoto
>>>>>>> developing
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference

class FirebasePhoto(
    private val imageRef : FirebaseStorage
) {

    private val mediator = MediatorLiveData<ResourceItem<List<ItemPhoto>?>>()
    fun listFileDownload(): LiveData<ResourceItem<List<ItemPhoto>?>> {
        try {
            val storageRef = imageRef.reference.child("$IMAGES")
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
                        mediator.value = ResourceItem(data = listReversed)
                    }
                }
            }
        } catch (e: Exception) {
            val mediatorKeep = mediator.value
            when (mediator.value) {
                null -> mediator.value = ResourceItem(data = mediatorKeep?.data, error = e.message)
            }
        }
        return mediator
    }

    fun uploadImageToStorage(
        dateCalendar: String,
        curFile: Uri?,
    ): LiveData<ResourceItem<Void?>> {
        val liveData = MutableLiveData<ResourceItem<Void?>>()
        try {
            curFile?.let {
                imageRef.reference.child("$IMAGES$dateCalendar").putFile(it)
                liveData.value =
                    ResourceItem(message = R.string.successfully_upload_image)
            }
        } catch (e: Exception) {
            val liveDataKeep = liveData.value
            when(liveData.value){
                null-> liveData.value = ResourceItem(data= liveDataKeep?.data,error = e.message)
            }
        }
        return liveData
    }

    fun deleteImage(
        dateCalendar: String,
    ): LiveData<ResourceItem<Void?>> {
        val liveData = MutableLiveData<ResourceItem<Void?>>()
        try {
            imageRef.reference.child("$IMAGES$dateCalendar").delete()
            liveData.value =
                ResourceItem(message = R.string.successfully_delete_image)
        } catch (e: Exception) {
            e.message?.let { message ->
                liveData.value = ResourceItem(error = message)
            }
        }
        return liveData
    }
}