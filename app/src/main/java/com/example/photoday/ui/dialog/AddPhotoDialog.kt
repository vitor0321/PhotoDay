package com.example.photoday.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.example.photoday.constants.toast.Toast.toast
import com.example.photoday.databinding.DialogFragmentAddPhotoBinding
import com.example.photoday.permission.CheckVersionPermission.dispatchTakePermission
import com.example.photoday.permission.CheckVersionPermission.galleryPermission
import com.example.photoday.ui.activity.PhotoDayActivity

class AddPhotoDialog(private val valueDate: String?) : DialogFragment() {

    private var _viewDataBinding: DialogFragmentAddPhotoBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _viewDataBinding = DialogFragmentAddPhotoBinding.inflate(inflater, container, false)
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
            val activity = requireActivity() as PhotoDayActivity
            this.clickUploadGallery = View.OnClickListener  {
                when (valueDate) {
                    null -> context?.let { context ->
                        galleryPermission(activity, null, context,
                            callbackMessage = { message -> toast(message) })
                    }
                    else -> context?.let { context ->
                        galleryPermission(activity, valueDate, context,
                            callbackMessage = { message -> toast(message) })
                    }
                }
                dialog?.dismiss()
            }
            this.clickUploadCamera = View.OnClickListener  {
                when (valueDate) {
                    null -> context?.let { context ->
                        dispatchTakePermission(activity, null, context,
                            callbackMessage = { message -> toast(message) })
                    }
                    else -> context?.let { context ->
                        dispatchTakePermission(activity, valueDate, context,
                            callbackMessage = { message -> toast(message)})
                    }
                }
                dialog?.dismiss()
            }
        }
    }

    override fun onDestroy() {
        this._viewDataBinding = null
        super.onDestroy()
    }

    companion object {
        fun newInstance(valueDate: String?) = AddPhotoDialog(valueDate)
    }
}