package com.example.photoday.ui.fragment.configuration

import android.content.Context
import android.net.Uri
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModel
import com.example.photoday.repository.firebase.ChangeUserFirebase.changeImageUser
import com.example.photoday.repository.firebase.CheckUserFirebase.getCurrentUserFirebase
import com.example.photoday.repository.firebase.LogFirebase.logoutFirebase
import de.hdodenhof.circleimageview.CircleImageView


class ConfigurationViewModel(private val context: Context?) : ViewModel() {

    /*get name and email of the user*/
    fun getUserFirebase(
            context: Context,
            textViewUserName: AppCompatTextView,
            textViewUserEmail: AppCompatTextView,
            imageUser: CircleImageView
    ) {
        getCurrentUserFirebase(context, textViewUserName, textViewUserEmail, imageUser)
    }

    fun imageUser(context: Context, image: Uri) {
        changeImageUser(context, image)
    }

    fun logout() {
        context?.let { logoutFirebase(context) }
    }
}