package com.example.photoday.ui.fragment.configuration

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.photoday.repository.firebase.ChangeUserFirebase.changeImageUser
import com.example.photoday.repository.firebase.CheckUserFirebase.getCurrentUserFirebase
import com.example.photoday.repository.firebase.LogFirebase.logoutFirebase
import com.example.photoday.repository.user.UserFirebase


class ConfigurationViewModel(private val context: Context?) : ViewModel() {

    private val mutableLiveData = MutableLiveData<UserFirebase>()
    val userFirebase: LiveData<UserFirebase> = mutableLiveData

    init {
        getUserFirebase()
    }

    /*get name and email of the user*/
    private fun getUserFirebase() {
        val userFirebase = getCurrentUserFirebase()
        mutableLiveData.value = userFirebase
    }

    fun imageUser(context: Context, image: Uri) {
        changeImageUser(context, image)
    }

    fun logout() {
        context?.let { logoutFirebase(context) }
    }
}