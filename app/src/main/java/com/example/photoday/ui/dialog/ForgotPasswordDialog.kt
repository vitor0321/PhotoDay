package com.example.photoday.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.example.photoday.constants.toast.Toast.toast
import com.example.photoday.databinding.DialogForgotPasswordBinding
import com.example.photoday.repository.BaseRepositoryUser
import com.example.photoday.ui.databinding.data.UserFirebaseData

class ForgotPasswordDialog(private val repository: BaseRepositoryUser) : DialogFragment() {

    private var _viewDataBinding: DialogForgotPasswordBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _viewDataBinding = DialogForgotPasswordBinding.inflate(inflater, container, false)
        return viewDataBinding.root
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
        viewDataBinding.apply {
            val userEmail = editTextEmailConfirm
            okButton = View.OnClickListener {
                try {
                    context?.let { context ->
                        repository.baseRepositoryForgotPassword(userEmail, context)
                            .observe(viewLifecycleOwner, { resourceMessage ->
                                resourceMessage.error?.let { message -> toast(message) }
                            })
                    }
                    dialog?.dismiss()
                } catch (e: Exception) {
                    e.message?.let { message -> toast( message) }
                }
            }
            cancelButton = View.OnClickListener {
                dialog?.dismiss()
            }
        }
    }

    override fun onDestroy() {
        _viewDataBinding = null
        super.onDestroy()
    }

    companion object {
        private val baseRepositoryUser: BaseRepositoryUser = BaseRepositoryUser()
        fun newInstance() = ForgotPasswordDialog(baseRepositoryUser)
    }
}