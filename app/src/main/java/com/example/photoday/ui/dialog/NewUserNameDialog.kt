package com.example.photoday.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.example.photoday.constants.toast.Toast.toast
import com.example.photoday.databinding.DialogFragmentUserNewNameBinding
import com.example.photoday.repository.BaseRepositoryUser
import com.example.photoday.repository.firebaseUser.user.UserFirebase
import com.example.photoday.ui.databinding.data.UserFirebaseData

class NewUserNameDialog(
    private val baseRepositoryUser: BaseRepositoryUser = BaseRepositoryUser(),
    private val userFirebaseData: UserFirebaseData = UserFirebaseData()
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
                    context?.let { context ->
                        baseRepositoryUser.baseRepositoryChangeNameUser(editTextNewName, context)
                            .observe(viewLifecycleOwner, { resourceResult ->
                                resourceResult.data?.let { newName -> setNameData(newName) }
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

    private fun setNameData(newName: String) {
        val userName = UserFirebase(
            name = newName,
            email = null,
            image = null
        )
        this.userFirebaseData.setData(userName)
    }

    override fun onDestroy() {
        this._viewDataBinding = null
        super.onDestroy()
    }

    companion object {
        fun newInstance() = NewUserNameDialog()
    }
}