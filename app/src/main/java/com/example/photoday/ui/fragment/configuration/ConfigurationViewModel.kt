package com.example.photoday.ui.fragment.configuration

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
        text_view_user_name: AppCompatTextView,
        text_view_user_email: AppCompatTextView,
        image_user: CircleImageView,
        context: ConfigurationFragment
    ) {
        val user = auth.currentUser
        when {
            user != null -> {
                auth?.let {
                    text_view_user_name.text = user?.displayName
                    text_view_user_email.text = user?.email

                        Glide.with(context)
                            .load(user?.photoUrl)
                            .fitCenter()
                            .placeholder(R.drawable.com_facebook_profile_picture_blank_square)
                            .into(image_user)
                }
            }
        }
    }
}