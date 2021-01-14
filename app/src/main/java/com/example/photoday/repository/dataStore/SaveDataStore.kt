package com.example.photoday.repository.dataStore

import android.content.Context
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.LifecycleCoroutineScope
import com.bumptech.glide.Glide
import com.example.photoday.R
import com.example.photoday.constants.EMAIL_USER
import com.example.photoday.constants.IMAGE_USER
import com.example.photoday.constants.NAME_USER
import com.google.firebase.auth.FirebaseUser
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.launch

object SaveDataStore {

    fun saveUser(
        lifecycleScope: LifecycleCoroutineScope,
        context: Context,
        currentUser: FirebaseUser
    ) {
        lifecycleScope.launch {
            val name = currentUser.displayName
            editName(name, lifecycleScope, context)
            val email = currentUser.email
            val image = currentUser.photoUrl
            val saveData = DataStoreUser(context)
            saveData.saveData(email, EMAIL_USER)
            saveData.saveData(image.toString(), IMAGE_USER)
        }
    }

    fun editName(
        newNameUser: String?,
        lifecycleScope: LifecycleCoroutineScope,
        context: Context
    ) {
        when{
            newNameUser != null->{
                lifecycleScope.launch {
                    val saveData = DataStoreUser(context)
                    val name = newNameUser.toString()
                    saveData.saveData(name, NAME_USER)
                }
            }
        }
    }

    /*set name, email and photo of the user*/
    fun getUser(
        userName: AppCompatTextView,
        userEmail: AppCompatTextView,
        userImage: CircleImageView,
        context: Context?,
        lifecycleScope: LifecycleCoroutineScope
    ) {
        lifecycleScope.launch {
            val saveData = context?.let { DataStoreUser(it) }
            val name = saveData?.readData(NAME_USER)
            val email = saveData?.readData(EMAIL_USER)
            val image = saveData?.readData(IMAGE_USER)
            userEmail.text = email
            userName.text = name
                context?.let { context ->
                    Glide.with(context)
                        .load(image)
                        .fitCenter()
                        .placeholder(R.drawable.ic_profile_picture_user)
                        .into(userImage)
                }
        }
    }
}