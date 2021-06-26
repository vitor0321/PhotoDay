package com.example.photoday.ui.activity

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.photoday.repository.BaseRepositoryNote
import com.example.photoday.repository.BaseRepositoryPhoto
import com.example.photoday.repository.BaseRepositoryPreferences
import com.example.photoday.ui.model.item.Components
import com.example.photoday.ui.model.item.ItemNote

class PhotoDayViewModel(
    private val photoRepository: BaseRepositoryPhoto,
    private val notaRepository: BaseRepositoryNote,
    private val preferences: BaseRepositoryPreferences
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

    fun getStatusSwitchPreferences()=
        preferences.baseGetValuePreferencesGallery()


    fun createPushPhoto(dateCalendar: String, curFile: Uri?) =
        photoRepository.baseRepositoryUploadImageToStorage(dateCalendar, curFile)

    fun salveNota(nota: ItemNote) =
        notaRepository.saveFirebase(nota)
}