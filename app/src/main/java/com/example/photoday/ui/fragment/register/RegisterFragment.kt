package com.example.photoday.ui.fragment.register

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.photoday.R
import com.example.photoday.constants.FALSE
import com.example.photoday.databinding.FragmentRegisterUserBinding
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.injector.ViewModelInjector
import com.example.photoday.ui.stateBarNavigation.Components

class RegisterFragment : BaseFragment() {

    private lateinit var binding: FragmentRegisterUserBinding
    private val viewModel by lazy {
        ViewModelInjector.providerRegisterViewModel(
                context,
                controlNavigation
        )
    }
    private val controlNavigation by lazy { findNavController() }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        statusBarNavigation()
        initButton()
    }

    private fun initButton() {
        binding.apply {
            buttonRegisterUser.setOnClickListener {
                /*here you will authenticate your email and password*/
                when {
                    editTextUserEmail.text.toString().isEmpty() -> {
                        editTextUserEmail.error =
                                context?.getString(R.string.please_enter_email_register)
                        editTextUserEmail.requestFocus()
                        return@setOnClickListener
                    }
                    !Patterns.EMAIL_ADDRESS.matcher(editTextUserEmail.text.toString())
                            .matches() -> {
                        editTextUserEmail.error =
                                context?.getString(R.string.please_enter_valid_email_register)
                        editTextUserEmail.requestFocus()
                        return@setOnClickListener
                    }
                    editTextUserPassword.text.toString().isEmpty() -> {
                        editTextUserPassword.error =
                                context?.getString(R.string.please_enter_password_register)
                        editTextUserPassword.requestFocus()
                        return@setOnClickListener
                    }
                    editTextUserConfirmPassword.text.toString().isEmpty() -> {
                        editTextUserConfirmPassword.error =
                                context?.getString(R.string.please_enter_password_confirm)
                        editTextUserConfirmPassword.requestFocus()
                        return@setOnClickListener
                    }
                    editTextUserConfirmPassword.text.toString() != editTextUserConfirmPassword.text.toString() -> {
                        editTextUserConfirmPassword.error =
                                context?.getString(R.string.password_are_not_the_same)
                        editTextUserConfirmPassword.requestFocus()
                        return@setOnClickListener
                    }
                }
                viewModel.signUpUser(
                        editTextUserEmail,
                        editTextUserPassword
                )
            }
        }
    }

    private fun statusBarNavigation() {
        statusAppBarNavigationBase(false, Components(FALSE, FALSE), R.color.white_status_bar)
    }

    override fun onDestroy() {
        binding
        super.onDestroy()
    }
}

