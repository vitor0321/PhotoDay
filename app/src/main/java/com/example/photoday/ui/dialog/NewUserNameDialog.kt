package com.example.photoday.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.example.photoday.constants.toast.Utils.toast
import com.example.photoday.databinding.DialogFragmentUserNameBinding
import com.example.photoday.repository.BaseRepositoryUser
import com.example.photoday.ui.databinding.data.ComponentsData
import com.example.photoday.ui.databinding.data.UserFirebaseData

class NewUserNameDialog(
    private val baseRepositoryUser: BaseRepositoryUser = BaseRepositoryUser(),
) : DialogFragment() {

    private var _binding: DialogFragmentUserNameBinding? = null
    private val binding get() = _binding!!
    private val userFirebaseData by lazy { UserFirebaseData() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
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
            okButton = View.OnClickListener {
                try {
                    context?.let { context ->
                        baseRepositoryUser.baseRepositoryChangeNameUser(editTextNewName, context)
                            .observe(viewLifecycleOwner, { resourceResult ->
                                resourceResult.error?.let { message -> toast(message) }
                            })
                    }
                    dialog?.dismiss()
                } catch (e: Exception) {
                    e.message?.let { message -> toast(message) }
                }
            }
            buttonCancel.setOnClickListener {
                dialog?.dismiss()
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        fun newInstance() = NewUserNameDialog()
    }
}