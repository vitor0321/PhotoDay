package com.example.photoday.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.example.photoday.constants.ADD_CAMERA
import com.example.photoday.constants.ADD_GALLERY
import com.example.photoday.constants.ADD_NOTE
import com.example.photoday.databinding.DialogFragmentAddItemPhotoBinding

class AddItemPhotoDialog : DialogFragment() {

    private var _viewDataBinding: DialogFragmentAddItemPhotoBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    var listener: AddItemListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewDataBinding = DialogFragmentAddItemPhotoBinding.inflate(inflater, container, false)
        return viewDataBinding.root
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
        this.viewDataBinding.apply {
            this.clickUploadGallery = View.OnClickListener  {
                listener?.onAccessSelected(ADD_GALLERY)
                dialog?.dismiss()
                onDestroy()
            }
            this.clickUploadCamera = View.OnClickListener {
                listener?.onAccessSelected(ADD_CAMERA)
                dialog?.dismiss()
                onDestroy()
            }
            this.clickUploadNote = View.OnClickListener {
                listener?.onAccessSelected(ADD_NOTE)
                dialog?.dismiss()
                onDestroy()
            }
        }
    }

    interface AddItemListener {
        fun onAccessSelected(accessSelected: Int)
    }

    override fun onDestroy() {
        this._viewDataBinding = null
        super.onDestroy()
    }

    companion object {
        fun newInstance() = AddItemPhotoDialog()
    }
}