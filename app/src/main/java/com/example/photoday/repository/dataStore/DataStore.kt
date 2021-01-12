package com.example.photoday.repository.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.first

class DataStoreUser(context: Context) {
    private val applicationContext = context.applicationContext
    private val dataStore: DataStore<Preferences>

    init {
        dataStore = applicationContext.createDataStore(name = "data_user")
    }

    suspend fun saveData(value: String, key: String) {
        val preferencesKey = preferencesKey<String>(key)
        dataStore.edit { setting ->
            setting[preferencesKey] = value
        }
    }

    suspend fun readData(key: String): String? {
        val preferencesKey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[preferencesKey]
    }
}
