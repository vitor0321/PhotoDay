package com.example.photoday.ui.fragment.gallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.photoday.R
import com.example.photoday.data.modelAdapter.GalleryItemPhotos

class GalleryViewModel : ViewModel() {

    val photosLiveData: MutableLiveData<List<GalleryItemPhotos>> = MutableLiveData()

    fun getPhotos(){
        photosLiveData.value = fakeCreateGetPhotos()
    }

    //isso é profissório para teste, pois vamos pegar essa lista do nosso BD
    private fun fakeCreateGetPhotos(): List<GalleryItemPhotos>{
        return listOf(
            GalleryItemPhotos(R.drawable.ic_item_photo),
            GalleryItemPhotos(R.drawable.ic_item_photo),
            GalleryItemPhotos(R.drawable.ic_item_photo),
            GalleryItemPhotos(R.drawable.ic_item_photo),
            GalleryItemPhotos(R.drawable.ic_item_photo),
            GalleryItemPhotos(R.drawable.ic_item_photo),
            GalleryItemPhotos(R.drawable.ic_item_photo)
        )
    }
}