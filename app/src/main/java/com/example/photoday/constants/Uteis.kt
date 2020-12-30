package com.example.photoday.constants

import android.content.Context
import android.widget.Toast

object Uteis {
    fun showToast(context: Context?, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}