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
import com.example.photoday.repository.dataStore.SaveDataStore.editName
import com.example.photoday.repository.dataStore.SaveDataStore.getUser
import com.example.photoday.repository.firebase.FirebaseLogout
import com.example.photoday.ui.dialog.AddPhotoDialog
import de.hdodenhof.circleimageview.CircleImageView


class ConfigurationViewModel(
    private val context: Context?,
    private val layoutInflater: LayoutInflater
) : ViewModel() {

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

    fun alertDialogNewUserName(lifecycleScope: LifecycleCoroutineScope) {
        /*Alert Dialog Forgot the password*/
        val builder = context?.let { AlertDialog.Builder(it, R.style.DialogTheme) }
        builder?.setTitle("What's your name?")
        val view = layoutInflater.inflate(R.layout.dialog_fragment_user_name, null)
        val newUserName = view.findViewById<EditText>(R.id.edit_text_new_name)
        builder?.setView(view)
        builder?.setPositiveButton(context?.getString(R.string.ok)) { _, _ ->
            context?.let { context -> editName(newUserName.toString(), lifecycleScope, context) }
        }
        builder?.setNegativeButton(context?.getString(R.string.cancel)) { _, _ -> }
        builder?.show()
    }

    fun logout() {
        context?.let { context -> FirebaseLogout.logoutFirebase(context) }
    }

    fun photoDialog(activity: FragmentActivity?) {
        activity?.let { activity ->
            AddPhotoDialog.newInstance()
                .show(activity.supportFragmentManager, ADD_PHOTO_DIALOG)
        }
    }
}