package com.example.photoday.constants

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.example.photoday.R
import com.google.android.gms.common.api.ApiException


object Utils {
    fun toast(context: Context, message: Int) {
        Toast(context).apply {
            val layoutToast = LayoutInflater.from(context).inflate(R.layout.constum_toast, null)
            val text = layoutToast.findViewById(R.id.tv_text) as TextView
            text.text = context.getString(message)
            duration = Toast.LENGTH_SHORT
            view = layoutToast
        }.show()
    }
}