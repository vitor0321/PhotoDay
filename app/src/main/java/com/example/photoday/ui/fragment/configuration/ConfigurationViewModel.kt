package com.example.photoday.ui.fragment.configuration

import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.photoday.R
import com.google.firebase.auth.FirebaseAuth
import de.hdodenhof.circleimageview.CircleImageView

class ConfigurationViewModel : ViewModel() {

    var auth = FirebaseAuth.getInstance()

    /*set name, email and photo of the user*/
    fun googleSingIn(
        userName: AppCompatTextView,
        userEmail: AppCompatTextView,
        image_user: CircleImageView,
        context: Context,
    ) {
        val user = auth.currentUser
        when {
            user != null -> {
                auth.let {
                    editUserName(user.displayName, userName)
                    userEmail.text = user.email

                        Glide.with(context)
                            .load(user.photoUrl)
                            .fitCenter()
                            .placeholder(R.drawable.com_facebook_profile_picture_blank_square)
                            .into(image_user)
                }
            }
        }
    }

    fun alertDialogNewUserName(
        context: Context?,
        layoutInflater: LayoutInflater,
        userName: AppCompatTextView
    ) {
        /*Alert Dialog Forgot the password*/
        val builder = context?.let { AlertDialog.Builder(it, R.style.MyDialogTheme) }
        builder?.setTitle("What's your name?")
        val view = layoutInflater.inflate(R.layout.dialog_fragment_user_name, null)
        val newUserName = view.findViewById<EditText>(R.id.edit_text_new_name)
        builder?.setView(view)
        builder?.setPositiveButton(context.getString(R.string.ok)) { _, _ ->
            editUserName(newUserName.toString(), userName)
        }
        builder?.setNegativeButton(context.getString(R.string.cancel)) { _, _ -> }
        builder?.show()
    }

    private fun editUserName(newUserName: String?, userName: AppCompatTextView) {
        userName.text = newUserName
    }
}