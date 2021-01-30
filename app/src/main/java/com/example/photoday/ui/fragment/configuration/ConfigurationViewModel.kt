package com.example.photoday.ui.fragment.configuration

import android.content.Context
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.photoday.repository.firebase.CheckUserFirebase.getCurrentUser
import com.example.photoday.repository.firebase.LogFirebase
import com.google.firebase.auth.FirebaseAuth
import de.hdodenhof.circleimageview.CircleImageView


class ConfigurationViewModel(private val context: Context?) : ViewModel() {

    val getUserLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        getUserLiveData()
    }

    private fun getUserLiveData() {
        val auth = FirebaseAuth.getInstance().currentUser
        getUserLiveData.value = auth?.displayName
    }

    /*get name and email of the user*/
    fun getDataStoreUser(
            context: Context,
            textViewUserName: AppCompatTextView,
            textViewUserEmail: AppCompatTextView,
            imageUser: CircleImageView
    ) {
        getCurrentUser(context, textViewUserName, textViewUserEmail, imageUser)
    }

    fun logout() {
        context?.let { LogFirebase.logoutFirebase(context) }
    }
}