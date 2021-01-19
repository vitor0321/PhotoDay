package com.example.photoday.ui.fragment.configuration

import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import com.example.photoday.R
import com.example.photoday.constants.ADD_PHOTO_DIALOG
import com.example.photoday.constants.FORGOT_PASSWORD
import com.example.photoday.constants.NEW_USER_NAME
import com.example.photoday.repository.dataStore.SaveDataStore.editName
import com.example.photoday.repository.dataStore.SaveDataStore.getUser
import com.example.photoday.repository.firebase.FirebaseLogout
import com.example.photoday.ui.dialog.AddPhotoDialog
import com.example.photoday.ui.dialog.ForgotPasswordDialog
import com.example.photoday.ui.dialog.NewUserNameDialog
import de.hdodenhof.circleimageview.CircleImageView


class ConfigurationViewModel(private val context: Context?) : ViewModel() {

    /*set name, email and photo of the user*/
    fun getDataStoreUser(
        userName: AppCompatTextView,
        userEmail: AppCompatTextView,
        userImage: CircleImageView,
        context: Context?,
        lifecycleScope: LifecycleCoroutineScope
    ) {
        getUser(
            userName,
            userEmail,
            userImage,
            context,
            lifecycleScope
        )
    }

    fun logout() {
        context?.let { FirebaseLogout.logoutFirebase(context) }
    }

    fun photoDialog(activity: FragmentActivity?) {
        /*open AddPhotoDialog*/
        activity?.let {
            AddPhotoDialog.newInstance()
                .show(activity.supportFragmentManager, ADD_PHOTO_DIALOG)
        }
    }

    fun newUserName(activity: FragmentActivity?) {
        /*open NewUserNameDialog*/
        activity?.let {
            NewUserNameDialog.newInstance()
                .show(activity.supportFragmentManager, NEW_USER_NAME)
        }
    }
}