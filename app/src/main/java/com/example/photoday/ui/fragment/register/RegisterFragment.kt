package com.example.photoday.ui.fragment.register

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.photoday.R
import com.example.photoday.constants.FALSE
import com.example.photoday.constants.FALSE_MENU
import com.example.photoday.constants.TRUE
import com.example.photoday.databinding.FragmentRegisterUserBinding
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.model.item.Components
import com.example.photoday.ui.model.user.UserLogin
import com.example.photoday.ui.toast.Toast.toast
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RegisterFragment : BaseFragment() {

    private var _viewDataBinding: FragmentRegisterUserBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    private val viewModel: RegisterViewModel by viewModel {
        parametersOf(findNavController())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        this._viewDataBinding = FragmentRegisterUserBinding.inflate(inflater, container, false)
        init()
        return this.viewDataBinding.root
    }

    private fun init() {
        statusBarNavigation()
        initButton()
    }

    private fun initButton() {
        this.viewDataBinding.apply {
            registerButton = View.OnClickListener {
                /*here you will authenticate your email and password*/
                cleanAllItem()

                val email = editTextUserEmail.text.toString()
                val password = editTextUserPassword.text.toString()
                val confirmPassword = editTextUserConfirmPassword.text.toString()

                val userLogin = UserLogin(email, password, confirmPassword)

                when (confirmItem(userLogin)) {
                    true -> registerUser(userLogin)
                }
            }
        }
    }

    private fun cleanAllItem() {
        this.viewDataBinding.apply {
            editTextUserEmail.error = null
            editTextUserPassword.error = null
            editTextUserConfirmPassword.error = null
        }
    }

    private fun confirmItem(userLogin: UserLogin): Boolean {
        this.viewDataBinding.apply {
            var validation = true
            when {
                userLogin.email.isBlank() -> {
                    editTextUserEmail.error =
                        context?.getString(R.string.please_enter_email_register)
                    validation = false
                }
                !Patterns.EMAIL_ADDRESS.matcher(userLogin.email).matches() -> {
                    editTextUserEmail.error =
                        context?.getString(R.string.please_enter_valid_email_register)
                    validation = false
                }
                userLogin.password.isBlank() -> {
                    editTextUserPassword.error =
                        context?.getString(R.string.please_enter_password_register)
                    validation = false
                }
                userLogin.confirmPassword?.isBlank() == true -> {
                    editTextUserConfirmPassword.error =
                        context?.getString(R.string.please_enter_password_confirm)
                    validation = false
                }
                userLogin.password != userLogin.confirmPassword -> {
                    editTextUserConfirmPassword.error =
                        context?.getString(R.string.password_are_not_the_same)
                    validation = false
                }
            }
            return validation
        }
    }

    private fun registerUser(userLogin: UserLogin){
        viewModel.signUpUser(userLogin)
            .observe(viewLifecycleOwner, { resourceMessage ->

                resourceMessage.message?.let {
                    messageToast(R.string.check_your_email_and_confirm)
                }?: run{messageToast(R.string.authentication_failed_try_again)}

                resourceMessage.navigation?.let {
                    viewModel.navigationRegister()
                    onDestroy()
                }
            })
    }

    private fun statusBarNavigation() {
        statusAppBarNavigationBase(
            menu = FALSE_MENU,
            components = Components(
                appBar = FALSE,
                bottomNavigation = FALSE,
                floatingActionButton = FALSE,
                actionBar = FALSE
            ),
            barColor = R.color.white_status_bar
        )
    }

    private fun messageToast(message: Int?) {
        message?.let { messageInt ->
            val messageToast = this.getString(messageInt)
            toast(messageToast)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this._viewDataBinding = null
    }
}

