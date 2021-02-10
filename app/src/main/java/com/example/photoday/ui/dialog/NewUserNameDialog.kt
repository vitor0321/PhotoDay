package com.example.photoday.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.example.photoday.R
import com.example.photoday.constants.Utils.toast
import com.example.photoday.databinding.DialogFragmentUserNameBinding
import com.example.photoday.repository.firebase.ChangeUserFirebase.changeNameUser

class NewUserNameDialog : DialogFragment() {

    private lateinit var binding: DialogFragmentUserNameBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentUserNameBinding.inflate(inflater, container, false)
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
            buttonOk.setOnClickListener {
                try {
                    /*here you will authenticate your email and password*/
                    when {
                        editTextNewName.text.toString().isEmpty() -> {
                            editTextNewName.error = getString(R.string.enter_valid_name)
                            editTextNewName.requestFocus()
                            return@setOnClickListener
                        }
                    }
                    val name = editTextNewName.text.toString()
                    context?.let { context -> changeNameUser(context, name) }
                    dialog?.dismiss()
                } catch (e: Exception) {
                    e.message?.let { context?.let { it1 -> toast(it1, it.toInt()) } }
                }
            }
            buttonCancel.setOnClickListener {
                dialog?.dismiss()
            }
        }
    }

    override fun onDestroy() {
        binding
        super.onDestroy()
    }

    companion object {
        fun newInstance() = NewUserNameDialog()
    }
}