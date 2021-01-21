package com.example.photoday.repository.dataStore

import android.content.Context
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.photoday.constants.EMAIL_USER
import com.example.photoday.constants.NAME_USER
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

object DataStoreService {

    fun saveUser(
        lifecycleScope: LifecycleCoroutineScope,
        context: Context,
        currentUser: FirebaseUser
    ) {
        lifecycleScope.launch {
            val name = currentUser.displayName
            editName(name, lifecycleScope, context)
            val email = currentUser.email
            val saveData = DataStoreUser(context)
            saveData.saveData(email, EMAIL_USER)
        }
    }

    fun editName(
            newNameUser: String?,
            lifecycleScope: LifecycleCoroutineScope,
            context: Context
    ) {
        newNameUser.let {
            lifecycleScope.launch {
                DataStoreUser(context).saveData(newNameUser, NAME_USER)
            }
        }
    }
}