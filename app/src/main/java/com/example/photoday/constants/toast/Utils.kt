package com.example.photoday.constants.toast

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.photoday.R
import com.example.photoday.databinding.ActivityPhotoDayBinding
import com.example.photoday.databinding.ConstumToastBinding

object Utils {
    @SuppressLint("StaticFieldLeak")
    private var _binding: ConstumToastBinding? = null
    private val binding get() = _binding!!

    fun Fragment.toast(message: String, time: Int = Toast.LENGTH_SHORT) {
        when {
            message != "" -> {
                Toast(context).apply {
                    val layoutToast =
                        LayoutInflater.from(context).inflate(R.layout.constum_toast, null)
                    _binding = ConstumToastBinding.bind(layoutToast)
                    binding!!.tvText.text = message
                    duration = time
                    view = layoutToast
                }.show()
            }
        }
    }

    fun Activity.toast(message: String, time: Int = Toast.LENGTH_SHORT) {
        when {
            message != "" -> {
                Toast(this).apply {
                    val layoutToast = LayoutInflater.from(this@toast)
                        .inflate(R.layout.constum_toast, null)
                    _binding = ConstumToastBinding.bind(layoutToast)
                    binding!!.tvText.text = message
                    duration = time
                    view = layoutToast
                }.show()
            }
        }
    }
}