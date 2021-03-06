package com.example.photoday.ui.databinding.data

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.example.photoday.ui.model.user.UserFirebase

class UserFirebaseData(
    private var userFirebase: UserFirebase = UserFirebase(),
    var name: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = userFirebase.name
    },
    var email: MutableLiveData<String> = MutableLiveData<String>().also {
        it.value = userFirebase.email
    },
    var image: MutableLiveData<Uri> = MutableLiveData<Uri>().also {
        it.value = userFirebase.image },
) {

    fun setData(userFirebase: UserFirebase?) {
        userFirebase?.let { this.userFirebase = userFirebase }
        userFirebase?.name?.let { name.postValue(this.userFirebase.name) }
        userFirebase?.email?.let { email.postValue(this.userFirebase.email) }
        userFirebase?.image?.let { image.postValue(this.userFirebase.image) }
    }

    fun getData(): UserFirebase? {
        return this.userFirebase.copy(
            name = name.value?: return null,
            email = email.value?: return null,
            image = image.value?: return null
        )
    }
}