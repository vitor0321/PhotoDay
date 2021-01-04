package com.example.photoday.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.photoday.R
import kotlinx.android.synthetic.main.dialog_fragment_add_photo.*

class AddPhotoDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.dialog_fragment_add_photo, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButton()
    }

    private fun initButton() {
        image_upload_camera.setOnClickListener {

        }
        image_upload_gallery.setOnClickListener {

        }
    }

    companion object {
        fun newInstance() = AddPhotoDialog()
    }
}