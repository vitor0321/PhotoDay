package com.example.photoday.ui.toast

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.photoday.R
import com.example.photoday.constants.EMPTY
import com.example.photoday.databinding.ItemToastConstumBinding

object Toast {
    @SuppressLint("StaticFieldLeak")
    private var _viewDataBinding: ItemToastConstumBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    fun Fragment.toast(message: String, time: Int = Toast.LENGTH_SHORT) {
        when {
            message != EMPTY -> Toast(context).apply {
                val layoutToast =
                    LayoutInflater.from(context).inflate(R.layout.item_toast_constum, null)
                _viewDataBinding = ItemToastConstumBinding.bind(layoutToast)
                viewDataBinding.itemToast = ItemToast(message)
                duration = time
                view = layoutToast
            }.show()
        }
        _viewDataBinding = null
    }

    fun Activity.toast(message: String, time: Int = Toast.LENGTH_SHORT) {
        when {
            message != EMPTY -> Toast(this).apply {
                val layoutToast = LayoutInflater.from(this@toast)
                    .inflate(R.layout.item_toast_constum, null)
                _viewDataBinding = ItemToastConstumBinding.bind(layoutToast)
                viewDataBinding.itemToast= ItemToast(message)
                duration = time
                view = layoutToast
            }.show()
        }
        _viewDataBinding = null
    }
}