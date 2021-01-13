package com.example.photoday.ui.fragment.configuration

import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.photoday.R
import com.example.photoday.constants.ADD_PHOTO_DIALOG
import com.example.photoday.constants.EMAIL_USER
import com.example.photoday.constants.IMAGE_USER
import com.example.photoday.constants.NAME_USER
import com.example.photoday.repository.dataStore.DataStoreUser
import com.example.photoday.repository.firebase.FirebaseLogout
import com.example.photoday.ui.dialog.AddPhotoDialog
import com.google.firebase.auth.FirebaseAuth
import de.hdodenhof.circleimageview.CircleImageView

class ConfigurationViewModel(
    private  val context: Context?,
    private val layoutInflater: LayoutInflater
) : ViewModel() {

    private var auth = FirebaseAuth.getInstance()
    private val saveData = context?.let { DataStoreUser(it) }

    /*set name, email and photo of the user*/
    suspend fun getDataStoreUser(
        userName: AppCompatTextView,
        userEmail: AppCompatTextView,
        userImage: CircleImageView,
    ) {
        val name = saveData?.readData(NAME_USER)
        val email = saveData?.readData(EMAIL_USER)
        val image = saveData?.readData(IMAGE_USER)
        userName.text = name
        userEmail.text = email

        /*val user = auth.currentUser
        when {
            user != null -> {
                auth.let {
                    context?.let { context ->
                        Glide.with(context)
                            .load(user.photoUrl)
                            .fitCenter()
                            .placeholder(R.drawable.com_facebook_profile_picture_blank_square)
                        //.into(image)
                    }
                }
            }
        }*/
    }

    fun alertDialogNewUserName(userName: AppCompatTextView) {
        /*Alert Dialog Forgot the password*/
        val builder = context?.let { AlertDialog.Builder(it, R.style.DialogTheme) }
        builder?.setTitle("What's your name?")
        val view = layoutInflater.inflate(R.layout.dialog_fragment_user_name, null)
        val newUserName =  view.findViewById<EditText>(R.id.edit_text_new_name)
        builder?.setView(view)
        builder?.setPositiveButton(context?.getString(R.string.ok)) { _, _ ->
            editUserName(newUserName.toString(), userName)
        }
        builder?.setNegativeButton(context?.getString(R.string.cancel)) { _, _ -> }
        builder?.show()
    }

    private fun editUserName(newUserName: String?, userName: AppCompatTextView) {
        userName.text = newUserName
    }

    fun logout() {
        context?.let { FirebaseLogout.logoutFirebase(it) }
    }

    fun photoDialog(activity: FragmentActivity?) {
        activity?.let { activity ->
            AddPhotoDialog.newInstance()
                .show(activity.supportFragmentManager, ADD_PHOTO_DIALOG)
        }
    }
}