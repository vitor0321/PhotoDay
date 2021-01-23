package com.example.photoday.ui.fragment.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.photoday.R
import com.example.photoday.constants.*
import com.example.photoday.databinding.FragmentLoginBinding
import com.example.photoday.repository.firebase.FirebaseLog.updateUI
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.injector.ViewModelInjector
import com.example.photoday.ui.navigation.Navigation.navFragmentLoginToRegister
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : BaseFragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding get() = _binding!!
    private val controlNavigation by lazy { findNavController() }
    private val viewModel by lazy {
        ViewModelInjector.providerLoginViewModel(
                controlNavigation,
                context,
                requireActivity(),
                lifecycleScope
        )
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        context?.let { updateUI(controlNavigation, ON_START, it, lifecycleScope) }
    }

    private fun init() {
        // Configure Google Sign In
        createRequestLoginGoogle()
        initButton()
        statusBarNavigation()
    }

    private fun initButton() {
        binding.apply {
            //Button to login
            buttonLoginLog.setOnClickListener {
                /*here you will authenticate your email and password*/
                when {
                    editTextLoginUser.text.toString().isEmpty() -> {
                        editTextLoginUser.error = context?.getString(R.string.please_enter_email_login)
                        editTextLoginUser.requestFocus()
                        return@setOnClickListener
                    }
                    !Patterns.EMAIL_ADDRESS.matcher(editTextLoginUser.text.toString()).matches() -> {
                        editTextLoginUser.error = context?.getString(R.string.please_enter_valid_email_login)
                        editTextLoginUser.requestFocus()
                        return@setOnClickListener
                    }
                    editTextLoginPassword.text.toString().isEmpty() -> {
                        editTextLoginPassword.error = context?.getString(R.string.please_enter_password)
                        editTextLoginPassword.requestFocus()
                        return@setOnClickListener
                    }
                }
                viewModel.doLogin(editTextLoginUser, editTextLoginPassword)
            }

            //Button register
            buttonLoginRegister.setOnClickListener { navFragmentLoginToRegister(controlNavigation) }

            //Button Login Google
            buttonLoginGoogle.setOnClickListener { signIn() }

            //Button forgot Password
            buttonLoginForgotPassword.setOnClickListener { viewModel.forgotPassword(activity) }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent
        when (requestCode) {
            RC_SIGN_IN -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    viewModel.authWithGoogle(account)
                } catch (e: ApiException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun createRequestLoginGoogle() {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(DEFAULT_WEB_CLIENT_ID)
                .requestEmail()
                .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    private fun statusBarNavigation() {
        statusAppBarNavigationBase(FRAG_LOGIN)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}