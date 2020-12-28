package com.example.photoday.ui.fragment.register

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.photoday.R
import com.example.photoday.constants.FALSE
import com.example.photoday.injector.ViewModelInjector
import com.example.photoday.stateAppBarBottonNavigation.Components
import com.example.photoday.ui.MainActivity
import com.example.photoday.ui.fragment.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_register_user.*

class Register : BaseFragment() {

    private val viewModel by lazy { ViewModelInjector.providerRegisterViewModel() }
    private val controlNavigation by lazy { findNavController() }
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        return inflater.inflate(R.layout.fragment_register_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButton()
        init()
    }

    private fun init() {
        /*Sending status AppBar and Bottom Navigation to the Activity*/
        val statusAppBarNavigation = Components(FALSE, FALSE)
        val mainActivity = requireActivity() as MainActivity
        mainActivity.statusAppBarNavigation(statusAppBarNavigation)
    }

    private fun initButton() {
        register_user_button.setOnClickListener {
            signUpUser()
        }
    }

    private fun signUpUser() {
        /*here you will authenticate your email and password*/
        val registerUser = register_user_id
        val registerUserPassword = register_user_password
        val registerConfirmPassword = register_user_confirm_password
        when {
            registerUser.text.toString().isEmpty() -> {
                registerUser.error = getString(R.string.please_enter_email)
                registerUser.requestFocus()
                return
            }
            !Patterns.EMAIL_ADDRESS.matcher(register_user_id.text.toString()).matches() -> {
                registerUser.error = getString(R.string.please_enter_valid_email)
                registerUser.requestFocus()
                return
            }
            registerUserPassword.text.toString().isEmpty() -> {
                registerUserPassword.error = getString(R.string.please_enter_password)
                registerUserPassword.requestFocus()
                return
            }
            registerConfirmPassword.text.toString().isEmpty() -> {
                registerUserPassword.error = getString(R.string.please_enter_password_confirm)
                registerUserPassword.requestFocus()
                return
            }
            registerConfirmPassword.text.toString() != registerUserPassword.text.toString() -> {
                registerUserPassword.error = getString(R.string.password_are_not_the_same)
                registerUserPassword.requestFocus()
                return
            }
        }
        viewModel.createUserWithEmailAndPassword(
            auth,
            registerUser,
            registerUserPassword,
            context,
            controlNavigation
        )
    }
}

