package com.example.photoday.repository

import com.example.photoday.repository.preferences.SwitchConfigurationPreference

class BaseRepositoryPreferences(private val preferences: SwitchConfigurationPreference) {
    fun baseGetValuePreferencesGallery() =
        preferences.switchValuePreferencesGallery

    fun switchPreferencesGallery(bottomSwitch: String, check: Boolean) =
        preferences.switchPreferencesGallery(bottomSwitch, check)
}