package com.example.photoday.repository.firebasePhotos

import ItemPhoto
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.photoday.constants.FALSE
<<<<<<< HEAD
import com.example.photoday.constants.IMAGES
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
import com.example.photoday.model.resource.ResourceItem
=======
import com.example.photoday.ui.model.resource.ResourceItem
import com.example.photoday.ui.model.adapter.ItemPhoto
>>>>>>> developing
=======
=======
=======
>>>>>>> developing
import com.example.photoday.constants.TRUE
>>>>>>> developing
import com.example.photoday.ui.model.item.ItemPhoto
import com.example.photoday.ui.model.resource.ResourceItem
>>>>>>> developing
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference

class FirebasePhoto(
    private val imageRef: FirebaseStorage,
    private val auth: FirebaseAuth
) {

    private val user = auth.currentUser

    fun listFileDownload(): LiveData<ResourceItem<List<ItemPhoto>?>> =
        MediatorLiveData<ResourceItem<List<ItemPhoto>?>>().apply {
            user?.email?.let {email->
                val storageRef = imageRef.reference.child("$email/")
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
                            imagesList.sortBy { it.date }
                            val listReversed = imagesList.asReversed()
                            value = ResourceItem(data = listReversed, message = null)
                        }
                    }
                }
            }
        }

    fun uploadImageToStorage(
        dateCalendar: String,
        curFile: Uri?,
    ): LiveData<ResourceItem<Void?>> = MutableLiveData<ResourceItem<Void?>>().apply {
        try {
            user?.email?.let { email ->
                curFile?.let {
                    imageRef.reference.child("$email/$dateCalendar").putFile(it)
                    value = ResourceItem(message = TRUE)
                }
            }
        } catch (e: Exception) {
            val liveDataKeep = value
            when (value) {
                null -> value =
                    ResourceItem(data = liveDataKeep?.data, message = FALSE)
            }
        }
    }

    fun deleteImage(
        dateCalendar: String,
    ): LiveData<ResourceItem<Void?>> = MutableLiveData<ResourceItem<Void?>>().apply {
        value = try {
            user?.email?.let { email ->
                imageRef.reference.child("$email/$dateCalendar").delete()
                ResourceItem(message = TRUE)
            }
        } catch (e: Exception) {
            ResourceItem(message = null)
        }
    }
}