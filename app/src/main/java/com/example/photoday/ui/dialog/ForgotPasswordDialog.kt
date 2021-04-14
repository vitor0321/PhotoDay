package com.example.photoday.ui.dialog

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.example.photoday.R
import com.example.photoday.constants.toast.Toast.toast
import com.example.photoday.databinding.DialogForgotPasswordBinding
import com.example.photoday.repository.BaseRepositoryUser

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
                    when {
                        userEmail.text.toString().isEmpty() -> {
                            messageToast(context?.getString(R.string.please_enter_email))
                        }
                        !Patterns.EMAIL_ADDRESS.matcher(userEmail.text.toString()).matches() -> {
                            messageToast(context?.getString(R.string.please_enter_valid_email))
                        }
                        else -> {
                            repository.baseRepositoryForgotPassword(userEmail)
                                .observe(viewLifecycleOwner, { resourceMessage ->
                                    when {
                                        resourceMessage.error != null -> {
                                            messageToast(resourceMessage.error)
                                        }
                                        resourceMessage.message != null -> {
                                            messageToast(context?.getString(resourceMessage.message))
                                        }
                                    }
                                })
                            dialog?.dismiss()
                        }
                    }
                } catch (e: Exception) {
                    messageToast(e.message)
                }
            }
            cancelButton = View.OnClickListener {
                dialog?.dismiss()
            }
        }
    }

    private fun messageToast(message: String?) {
        message?.let { message -> toast(message) }
    }

    override fun onDestroy() {
        _viewDataBinding = null
        super.onDestroy()
    }

    companion object {
        fun newInstance() = ForgotPasswordDialog
    }
}