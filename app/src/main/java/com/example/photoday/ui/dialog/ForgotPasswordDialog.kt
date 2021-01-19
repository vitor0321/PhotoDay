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
import com.example.photoday.databinding.DialogForgotPasswordBinding
import com.example.photoday.repository.firebase.FirebaseLogout


class ForgotPasswordDialog : DialogFragment() {

    private var _binding: DialogForgotPasswordBinding? = null
    private val binding: DialogForgotPasswordBinding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
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
        binding.apply {
            val userEmail = editTextEmailConfirm
            buttonOk.setOnClickListener {

                /*here you will authenticate your email and password*/
                when {
                    userEmail.text.toString().isEmpty() -> {
                        userEmail.error = context?.getString(R.string.please_enter_email_dialog)
                        userEmail.requestFocus()
                        return@setOnClickListener
                    }
                    !Patterns.EMAIL_ADDRESS.matcher(userEmail.text.toString()).matches() -> {
                        userEmail.error =
                            context?.getString(R.string.please_enter_valid_email_dialog)
                        userEmail.requestFocus()
                        return@setOnClickListener
                    }
                }
                context?.let { context -> FirebaseLogout.forgotPassword(context, userEmail) }
                dialog?.dismiss()
            }
            buttonCancel.setOnClickListener {
                dialog?.dismiss()
            }
        }
    }

    companion object {
        fun newInstance() = ForgotPasswordDialog()
    }
}