package com.example.photoday.constants

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import com.example.photoday.R
import com.example.photoday.databinding.ConstumToastBinding

object Utils {
    @SuppressLint("StaticFieldLeak")
    private var _binding: ConstumToastBinding? = null
    private val binding get() = _binding
    fun toast(context: Context, message: String) {
        when{
            message!="" ->{
                Toast(context).apply {
                    val layoutToast = LayoutInflater.from(context).inflate(R.layout.constum_toast, null)
                    _binding = ConstumToastBinding.bind(layoutToast)
                    binding!!.tvText.text = message
                    duration = Toast.LENGTH_SHORT
                    view = layoutToast
                }.show()
            }
        }
    }
}