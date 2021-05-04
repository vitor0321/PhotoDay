package com.example.photoday.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.example.photoday.R
import com.example.photoday.databinding.DialogFragmentUserNewNameBinding
import com.example.photoday.ui.model.user.UserFirebase

class NewUserNameDialog : DialogFragment() {

    private var _viewDataBinding: DialogFragmentUserNewNameBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    var listener: NewUserNameListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _viewDataBinding = DialogFragmentUserNewNameBinding.inflate(inflater, container, false)
        return this.viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = LinearLayout.LayoutParams.MATCH_PARENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }

    private fun init() {
        this.viewDataBinding.apply {
            okButton = View.OnClickListener {
                val newName = editTextNewName.text.toString()
                when {
                    newName.isBlank() -> {
                        listener?.onNewNameSelected(null, R.string.enter_valid_name)
                        editTextNewName.requestFocus()
                    }
                    else -> {
                        val userName = UserFirebase(
                            name = newName,
                            email = null,
                            image = null
                        )
                        listener?.onNewNameSelected(userName, null)
                    }
                }
                dialog?.dismiss()
                onDestroy()
            }
            cancelButton = View.OnClickListener {
                dialog?.dismiss()
                onDestroy()
            }
        }
    }

    interface NewUserNameListener {
        fun onNewNameSelected(userFirebase: UserFirebase?, message: Int?)
    }

    override fun onDestroy() {
        this._viewDataBinding = null
        super.onDestroy()
    }

    companion object {
        fun newInstance() = NewUserNameDialog()
    }
}