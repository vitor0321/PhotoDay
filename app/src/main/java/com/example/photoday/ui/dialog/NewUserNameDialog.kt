package com.example.photoday.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.example.photoday.R
import com.example.photoday.databinding.DialogFragmentUserNameBinding

class NewUserNameDialog : DialogFragment() {

    private var _binding: DialogFragmentUserNameBinding? = null
    private val binding: DialogFragmentUserNameBinding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFragmentUserNameBinding.inflate(inflater, container, false)
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
                /*here you will authenticate your email and password*/
                when {
                    editTextNewName.text.toString().isEmpty() -> {
                        editTextNewName.error = getString(R.string.enter_valid_name)
                        editTextNewName.requestFocus()
                        return@setOnClickListener
                    }
                }
                dialog?.dismiss()
            }
            buttonCancel.setOnClickListener {
                dialog?.dismiss()
            }
        }
    }

    companion object {
        fun newInstance() = NewUserNameDialog()
    }
}