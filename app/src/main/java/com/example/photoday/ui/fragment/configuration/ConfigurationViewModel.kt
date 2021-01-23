package com.example.photoday.ui.fragment.configuration

import android.content.Context
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.photoday.constants.EMAIL_USER
import com.example.photoday.constants.NAME_USER
import com.example.photoday.repository.dataStore.DataStoreUser
import com.example.photoday.repository.dataStore.UserDataStore
import com.example.photoday.repository.firebase.FirebaseLog
import kotlinx.coroutines.launch


class ConfigurationViewModel(private val context: Context?) : ViewModel() {

    val getUserLiveData: MutableLiveData<UserDataStore> = MutableLiveData()

    /*get name and email of the user*/
    fun getDataStoreUser(
            context: Context,
            lifecycleScope: LifecycleCoroutineScope
    ) {
        lifecycleScope.launch {
            val userDataStore = UserDataStore()
            userDataStore.name = DataStoreUser(context).readData(NAME_USER)
            userDataStore.email = DataStoreUser(context).readData(EMAIL_USER)

            getUserLiveData.value = userDataStore
        }
    }

    fun logout() {
        context?.let { FirebaseLog.logoutFirebase(context) }
    }
}