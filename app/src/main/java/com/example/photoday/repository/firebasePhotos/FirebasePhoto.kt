package com.example.photoday.repository.firebasePhotos

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoday.R
import com.example.photoday.adapter.GalleryAdapter
import com.example.photoday.adapter.modelAdapter.ItemPhoto
import com.example.photoday.constants.IMAGES
import com.example.photoday.constants.Utils.toast
import com.example.photoday.databinding.FragmentGalleryBinding
import com.example.photoday.databinding.FragmentTimelineBinding
import com.example.photoday.ui.injector.ViewModelInjector
import com.google.android.gms.tasks.Task
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


object FirebasePhoto {
    private val imageRef = Firebase.storage.reference
    private val viewModelGallery by lazy { ViewModelInjector.providerGalleryViewModel() }

    fun uploadImageToStorage(context: Context, dateCalendar: String, curFile: Uri?) =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                curFile?.let {
                    imageRef.child("$IMAGES$dateCalendar").putFile(it)
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

    fun deleteImage(context: Context, dateCalendar: String) =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                imageRef.child("$IMAGES$dateCalendar").delete()
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

    fun listFileDownload(
        bindingGallery: FragmentGalleryBinding?,
        context: Context,
        bindingTimeline: FragmentTimelineBinding?
    ) {
        val storageRef = imageRef.child("$IMAGES")
        val imagesList: ArrayList<ItemPhoto> = ArrayList()

        val listAllTask: Task<ListResult> = storageRef.listAll()
        listAllTask.addOnCompleteListener { result ->
            val items: List<StorageReference> = result.result!!.items
            //add cycle for add image url to list
            items.forEachIndexed { index, item ->
                item.downloadUrl.addOnSuccessListener { itemUri ->
                    Log.d("item", "$itemUri")
                    val dateCalendar = item.name
                    imagesList.add(ItemPhoto(dateCalendar, itemUri.toString()))
                }.addOnCompleteListener {
                    when {
                        bindingGallery != null -> {
                            bindingGallery.run {
                                recycleViewListGallery.adapter =
                                    GalleryAdapter(imagesList) { itemPhoto ->
                                        /**
                                         * quando clicar na photo, vai fazer o que ?
                                         */
                                    }
                                recycleViewListGallery.layoutManager = LinearLayoutManager(context)
                                progressBar.visibility = View.GONE
                            }
                        }
                        bindingTimeline != null -> {
                            bindingTimeline.run {
                                recycleViewListTimeline.adapter =
                                    GalleryAdapter(imagesList) { itemPhoto ->
                                        /**
                                         * quando clicar na photo, vai fazer o que ?
                                         */
                                    }
                                recycleViewListTimeline.layoutManager = LinearLayoutManager(context)
                                progressBar.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        }
    }
}