package com.example.photoday.repository.dataStore

import android.content.Context
import android.net.Uri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import com.example.photoday.constants.DATA_USER
import kotlinx.coroutines.flow.first

class DataStoreUser(context: Context) {
    private val applicationContext = context.applicationContext
    private val dataStore: DataStore<Preferences>

    init {
        dataStore = applicationContext.createDataStore(name = DATA_USER)
    }

    suspend fun saveData(value: String?, key: String) {
        when{
            value !=null ->{
                val preferencesKey = stringPreferencesKey(key)
                dataStore.edit { setting ->
                    setting[preferencesKey] = value
                }
            }
        }
    }

    suspend fun readData(key: String): String? {
        val preferencesKey = stringPreferencesKey(key)
        val preferences = dataStore.data.first()
        return preferences[preferencesKey]
    }
}                  
                   
                   
                   

