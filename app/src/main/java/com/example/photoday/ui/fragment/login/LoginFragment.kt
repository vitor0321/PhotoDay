package com.example.photoday.ui.fragment.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.photoday.R
import com.example.photoday.constants.*
import com.example.photoday.constants.Utils.toast
import com.example.photoday.databinding.FragmentLoginBinding
import com.example.photoday.navigation.Navigation.navFragmentLoginToRegister
import com.example.photoday.repository.BaseRepositoryUser
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.injector.ViewModelInjector
import com.example.photoday.ui.stateBarNavigation.Components
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val controlNavigation by lazy { findNavController() }

    private val viewModel by lazy {
        val baseRepositoryUser = BaseRepositoryUser()
        ViewModelInjector.providerLoginViewModel(controlNavigation, baseRepositoryUser)
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        init()
        return binding.root
    }

    private fun init() {
        createRequestLoginGoogle()
        initButton()
        statusBarNavigation()
        initObserver()
    }

    private fun initObserver() {
        // Check if user is signed in (non-null) and update UI accordingly.
        viewModel.updateUI(controlNavigation, ON_START, context)
            ?.observe(viewLifecycleOwner, { resourceUser ->
                context?.let { context ->
                    resourceUser.error?.let { message ->
                        toast(context, message)
                    }
                }
            })

        viewModel.uiStateFlowMessage.asLiveData().observe(viewLifecycleOwner) { message ->
            context?.let { context -> toast(context, message) }
        }
    }

    private fun initButton() {
        binding.apply {
            //Button to login
            buttonLoginLog.setOnClickListener {
                try {
                    /*here you will authenticate your email and password*/
                    when {
                        editTextLoginUser.text.toString().isEmpty() -> {
                            editTextLoginUser.error = context?.getString(R.string.please_enter_email_login)
                            editTextLoginUser.requestFocus()
                            return@setOnClickListener
                        }
                        !Patterns.EMAIL_ADDRESS.matcher(editTextLoginUser.text.toString()).matches() -> {
                            editTextLoginUser.error =
                                context?.getString(R.string.please_enter_valid_email_login)
                            editTextLoginUser.requestFocus()
                            return@setOnClickListener
                        }
                        editTextLoginPassword.text.toString().isEmpty() -> {
                            editTextLoginPassword.error =
                                context?.getString(R.string.please_enter_password)
                            editTextLoginPassword.requestFocus()
                            return@setOnClickListener
                        }
                    }
                    context?.let { context ->
                        viewModel.doLogin(editTextLoginUser,
                            editTextLoginPassword,
                            requireActivity(),
                            context).observe(viewLifecycleOwner, { resourceMessage ->
                            resourceMessage.error?.let { message -> toast(context, message) }
                        })
                    }
                } catch (e: Exception) {
                    CoroutineScope(Dispatchers.Main).launch {
                        e.message?.let { message ->
                            context?.let { context -> toast(context, message) }
                        }
                    }
                }

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
                    context?.let { context ->
                        viewModel.authWithGoogle(account, context)
                            .observe(this, { resourceMessage ->
                                resourceMessage.error?.let { message -> toast(context, message) }
                            })
                    }
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
        statusAppBarNavigationBase(FALSE_MENU, Components(FALSE, FALSE), R.color.white_status_bar)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}