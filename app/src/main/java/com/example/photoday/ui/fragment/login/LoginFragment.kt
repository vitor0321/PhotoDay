package com.example.photoday.ui.fragment.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.photoday.R
import com.example.photoday.constants.DEFAULT_WEB_CLIENT_ID
import com.example.photoday.constants.FALSE
import com.example.photoday.constants.ON_START
import com.example.photoday.constants.RC_SIGN_IN
import com.example.photoday.databinding.FragmentLoginBinding
import com.example.photoday.injector.ViewModelInjector
import com.example.photoday.navigation.Navigation.navFragmentLoginToRegister
import com.example.photoday.repository.firebase.FirebaseLogout.firebaseAuthWithGoogle
import com.example.photoday.repository.firebase.FirebaseLogout.updateUI
import com.example.photoday.stateBarNavigation.Components
import com.example.photoday.ui.MainActivity
import com.example.photoday.ui.fragment.base.BaseFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : BaseFragment() {

    private val viewModel by lazy { ViewModelInjector.providerLoginViewModel() }
    private val controlNavigation by lazy { findNavController() }
    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        auth = FirebaseAuth.getInstance()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        init()
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        context?.let { context -> updateUI(context,currentUser, controlNavigation, ON_START) }
    }

    private fun init() {
        // Configure Google Sign In
        createRequestLoginGoogle()
        initButton()
        statusBarNavigation()
    }

    private fun initButton() {
        binding.run {
            //Button to login
            loginButtonLog.setOnClickListener {
                context?.let { context ->
                    viewModel.doLogin(
                        loginUserId,
                        loginPassword,
                        requireActivity(),
                        context,
                        controlNavigation
                    )
                }
            }

            //Button register
            loginButtonRegister.setOnClickListener { navFragmentLoginToRegister(controlNavigation) }

            //Button Login Google
            loginButtonGoogle.setOnClickListener { signIn() }

            //Button forgot Password
            loginButtonForgotPassword.setOnClickListener { alertDialogForgotPassword() }
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
                    context?.let { context ->
                        firebaseAuthWithGoogle(
                            context,
                            account.idToken!!,
                            controlNavigation
                        )
                    }
                } catch (e: ApiException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun createRequestLoginGoogle() {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(DEFAULT_WEB_CLIENT_ID)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun alertDialogForgotPassword() {
        viewModel.alertDialogForgotPassword(context,layoutInflater)
    }

    private fun statusBarNavigation() {
        /*Sending status AppBar and Bottom Navigation to the Activity*/
        val statusAppBarNavigation = Components(FALSE, FALSE)
        val mainActivity = requireActivity() as MainActivity
        mainActivity.statusAppBarNavigation(statusAppBarNavigation)

        /*change color statusBar*/
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
    }
}