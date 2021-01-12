package com.example.photoday.ui.fragment.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.photoday.R
import com.example.photoday.constants.DEFAULT_WEB_CLIENT_ID
import com.example.photoday.constants.FALSE
import com.example.photoday.constants.ON_START
import com.example.photoday.constants.RC_SIGN_IN
import com.example.photoday.databinding.FragmentLoginBinding
import com.example.photoday.injector.ViewModelInjector
import com.example.photoday.repository.firebase.FirebaseLogout.updateUI
import com.example.photoday.ui.MainActivity
import com.example.photoday.ui.navigation.Navigation.navFragmentLoginToRegister
import com.example.photoday.ui.stateBarNavigation.Components
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding get() = _binding!!
    private val controlNavigation by lazy { findNavController() }
    private val viewModel by lazy {
        ViewModelInjector.providerLoginViewModel(
            controlNavigation,
            context,
            requireActivity()
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
        val currentUser = auth.currentUser
        context?.let { context -> updateUI(currentUser, controlNavigation, ON_START, context) }
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
                viewModel.doLogin(editTextLoginUserId, editTextLoginPassword)
            }

            //Button register
            buttonLoginRegister.setOnClickListener { navFragmentLoginToRegister(controlNavigation) }

            //Button Login Google
            buttonLoginGoogle.setOnClickListener { signIn() }

            //Button forgot Password
            buttonLoginForgotPassword.setOnClickListener { alertDialogForgotPassword() }
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

    private fun alertDialogForgotPassword() {
        viewModel.alertDialogForgotPassword(layoutInflater)
    }

    private fun statusBarNavigation() {
        /*Sending status AppBar and Bottom Navigation to the Activity*/
        val statusAppBarNavigation = Components(FALSE, FALSE)
        val mainActivity = requireActivity() as MainActivity
        mainActivity.statusAppBarNavigation(statusAppBarNavigation)

        /*change color statusBar*/
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}