package com.example.photoday.ui.fragment.configuration

import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.photoday.R
import com.google.firebase.auth.FirebaseAuth
import de.hdodenhof.circleimageview.CircleImageView

class ConfigurationViewModel : ViewModel() {

    var auth = FirebaseAuth.getInstance()

    private val userNameMutable = MutableLiveData<String>()
    var userName: LiveData<String> = userNameMutable

    private fun editUserName(newUserName: String?) {
        val userFirebase = auth.currentUser?.displayName
        when (newUserName) {
            userFirebase -> {
                auth.let {
                    userNameMutable.postValue(userFirebase)
                }
            }
            else -> {
                userNameMutable.postValue(newUserName)
            }
        }
    }

    /*set name, email and photo of the user*/
    fun editUser(
        userName: AppCompatTextView,
        userEmail: AppCompatTextView,
        userImage: CircleImageView,
        configurationFragment: ConfigurationFragment
    ) {
        val userFirebase = auth.currentUser
        val userNameFirebase = userFirebase?.displayName.toString()
        val userEmailFirebase = userFirebase?.email.toString()
        when {
            userFirebase != null -> {
                auth.let {
                    userEmail.text = userEmailFirebase
                    userName.text = userNameFirebase

                    Glide.with(configurationFragment)
                        .load(userFirebase.photoUrl)
                        .fitCenter()
                        .placeholder(R.drawable.com_facebook_profile_picture_blank_square)
                        .into(userImage)
                }
            }
        }
    }

    fun alertDialogNewUserName(
        context: Context?,
        layoutInflater: LayoutInflater
    ) {
        /*Alert Dialog Forgot the password*/
        val builder = context?.let { AlertDialog.Builder(it, R.style.MyDialogTheme) }
        builder?.setTitle("What's your name?")
        val view = layoutInflater.inflate(R.layout.dialog_fragment_user_name, null)
        val newUserName = view.findViewById<EditText>(R.id.edit_text_new_name)
        builder?.setView(view)
        builder?.setPositiveButton(context.getString(R.string.ok)) { _, _ ->
            val userName = newUserName.toString()
            editUserName(userName)
        }
        builder?.setNegativeButton(context.getString(R.string.cancel)) { _, _ -> }
        builder?.show()
    }
}