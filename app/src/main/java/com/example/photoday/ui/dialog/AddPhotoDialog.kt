package com.example.photoday.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.example.photoday.R
import com.example.photoday.databinding.DialogFragmentAddPhotoBinding
import com.example.photoday.databinding.ItemGalleryFragmentBinding
import com.example.photoday.ui.adapter.GalleryListAdapter

class AddPhotoDialog : DialogFragment() {

    private var _binding: DialogFragmentAddPhotoBinding? = null
    private val binding: DialogFragmentAddPhotoBinding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = DialogFragmentAddPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButton()
    }


    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = LinearLayout.LayoutParams.MATCH_PARENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }

    private fun initButton() {
        binding.apply {
            imageUploadCamera.setOnClickListener {

            }

            imageUploadGallery.setOnClickListener {

            }
        }
    }

    companion object {
        fun newInstance() = AddPhotoDialog()
    }
}