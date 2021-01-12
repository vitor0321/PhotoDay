package com.example.photoday.ui.fragment.configuration

import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.example.photoday.R
import com.example.photoday.constants.EMAIL_USER
import com.example.photoday.constants.IMAGE_USER
import com.example.photoday.constants.NAME_USER
import com.example.photoday.repository.dataStore.DataStoreUser
import com.example.photoday.repository.firebase.FirebaseLogout
import com.google.firebase.auth.FirebaseAuth
import de.hdodenhof.circleimageview.CircleImageView

class ConfigurationViewModel(
    private  val context: Context?,
    private val layoutInflater: LayoutInflater
) : ViewModel() {

    var auth = FirebaseAuth.getInstance()


    suspend fun getDataStoreUser(
        userName: AppCompatTextView,
        userEmail: AppCompatTextView,
        userImage: CircleImageView,
    ) {
        userName.text = context?.let { DataStoreUser(it).readData(NAME_USER) }
        userEmail.text= context?.let { DataStoreUser(it).readData(EMAIL_USER) }
        val image = context?.let { DataStoreUser(it).readData(IMAGE_USER) }

    }

    /*set name, email and photo of the user*/
    fun googleSingIn(
        userName: AppCompatTextView,
        userEmail: AppCompatTextView,
        image_user: CircleImageView,
    ) {
        val user = auth.currentUser
        when {
            user != null -> {
                auth.let {
                    editUserName(user.displayName, userName)
                    userEmail.text = user.email
                    context?.let { context ->
                        Glide.with(context)
                            .load(user.photoUrl)
                            .fitCenter()
                            .placeholder(R.drawable.com_facebook_profile_picture_blank_square)
                            .into(image_user)
                    }
                }
            }
        }
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
}