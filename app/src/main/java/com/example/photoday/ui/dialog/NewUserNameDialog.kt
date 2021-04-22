package com.example.photoday.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.example.photoday.R
import com.example.photoday.constants.toast.Toast.toast
import com.example.photoday.databinding.DialogFragmentUserNewNameBinding
import com.example.photoday.model.user.UserFirebase
import com.example.photoday.repository.BaseRepositoryUser
import com.example.photoday.ui.databinding.data.UserFirebaseData

class NewUserNameDialog(
    private val baseRepositoryUser: BaseRepositoryUser,
    private val userFirebaseData: UserFirebaseData
) : DialogFragment() {

    private var _viewDataBinding: DialogFragmentUserNewNameBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _viewDataBinding = DialogFragmentUserNewNameBinding.inflate(inflater, container, false)
        this.viewDataBinding.userFirebase = userFirebaseData
        this.viewDataBinding.lifecycleOwner = this
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
                try {
                    val newName = editTextNewName.text.toString()
                    when {
                        newName.isBlank() -> {
                            messageToast(context?.getString(R.string.enter_valid_name))
                            editTextNewName.requestFocus()
                        }
                    }
                    baseRepositoryUser.baseRepositoryChangeNameUser(newName)
                        .observe(viewLifecycleOwner, { resourceResult ->
                            when {
                                resourceResult.message != null -> messageToast(resourceResult.message?.let { message ->
                                    context?.getString(message)
                                })
                                resourceResult.data != null -> setNameData(resourceResult.data)

                            }
                        })
                    dialog?.dismiss()
                    onDestroy()
                } catch (e: Exception) {
                    messageToast(e.message)
                }
            }
            cancelButton = View.OnClickListener {
                dialog?.dismiss()
                onDestroy()
            }
        }
    }

    private fun setNameData(newName: String) {
        val userName = UserFirebase(
            name = newName,
            email = null,
            image = null
        )
        this.userFirebaseData.setData(userName)
    }

    private fun messageToast(message: String?) {
        message?.let { message -> toast(message) }
    }

    override fun onDestroy() {
        this._viewDataBinding = null
        super.onDestroy()
    }

    companion object {
        fun newInstance(
            baseRepositoryUser: BaseRepositoryUser,
            userFirebaseData: UserFirebaseData
        ) = NewUserNameDialog(baseRepositoryUser, userFirebaseData)
    }
}