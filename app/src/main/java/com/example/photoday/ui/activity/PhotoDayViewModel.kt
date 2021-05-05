package com.example.photoday.ui.activity

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.photoday.repository.BaseRepositoryPhoto
import com.example.photoday.ui.stateBarNavigation.Components

class PhotoDayViewModel(
    private val repository: BaseRepositoryPhoto,
) : ViewModel() {

    val component: LiveData<Components> get() = _component

    private var _component: MutableLiveData<Components> =
        MutableLiveData<Components>().also {
            it.value = switchComponent
        }

    var switchComponent: Components = Components()
        set(value) {
            field = value
            _component.value = value
        }


    fun createPushPhoto(dateCalendar: String, curFile: Uri?) =
        repository.baseRepositoryUploadImageToStorage(dateCalendar, curFile)

}