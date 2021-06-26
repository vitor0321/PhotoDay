package com.example.photoday.repository.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.photoday.constants.KEY_SWITCH_GALLERY
import com.example.photoday.constants.PREFERENCES_SWITCH
import com.example.photoday.constants.TRUE

class SwitchConfigurationPreference(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCES_SWITCH, Context.MODE_PRIVATE)

    val switchValuePreferencesGallery = preferences.getBoolean(KEY_SWITCH_GALLERY, TRUE)

    fun switchPreferencesGallery(bottomSwitch: String, check: Boolean) {
        preferences.edit {
            putBoolean(bottomSwitch, check).commit()
        }
    }
}