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
import com.example.photoday.constants.toast.Toast.toast
import com.example.photoday.databinding.FragmentLoginBinding
import com.example.photoday.model.resource.ResourceUser
import com.example.photoday.model.user.UserLogin
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.stateBarNavigation.Components
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class LoginFragment : BaseFragment() {

    private var _viewDataBinding: FragmentLoginBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    private val viewModel: LoginViewModel by viewModel {
        parametersOf(findNavController())
    }
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _viewDataBinding = FragmentLoginBinding.inflate(inflater, container, false)
        init()
        return this.viewDataBinding.root
    }

    private fun init() {
        createRequestLoginGoogle()
        initButton()
        statusBarNavigation()
        initObserver()
    }

    private fun initObserver() {
        // Check if user is signed in (non-null) and update UI accordingly.
        this.viewModel.updateUI(ON_START)
            .observe(viewLifecycleOwner, { resourceUser ->
                navigation(resourceUser)
            })

        this.viewModel.uiStateFlowMessage.asLiveData().observe(viewLifecycleOwner) { message ->
            messageToast(message)
        }
    }

    private fun initButton() {
        this.viewDataBinding.apply {
            //Button to login
            loginButton = View.OnClickListener { login() }

            //Button register
            registerButton = View.OnClickListener { viewModel.navController(REGISTER) }

            //Button Login Google
            loginGoogleButton = View.OnClickListener { signIn() }

            //Button forgot Password
            forgotPasswordButton = View.OnClickListener { viewModel.navController(FORGOT_PASSWORD) }
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
                        .observe(this, { resourceUser ->
                            navigation(resourceUser)
                        })
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

    private fun login() {
        try {
            cleanAllItem()
            /*here you will authenticate your email and password*/
            this.viewDataBinding.apply {
                val email = editTextLoginUser.text.toString()
                val password = editTextLoginPassword.text.toString()
                when (confirmItem(UserLogin(email, password))) {
                    true -> viewModel.signInWithEmailAndPassword(email, password)
                        .observe(viewLifecycleOwner, { resourceUser ->
                            navigation(resourceUser)
                        })
                }
            }
        } catch (e: Exception) {
            messageToast(e.message)
        }
    }

    private fun cleanAllItem() {
        this.viewDataBinding.apply {
            editTextLoginUser.error = null
            editTextLoginPassword.error = null
        }
    }

    private fun confirmItem(userLogin: UserLogin): Boolean {
        this.viewDataBinding.apply {
            when {
                !Patterns.EMAIL_ADDRESS.matcher(userLogin.email)
                    .matches() -> {
                    editTextLoginUser.error =
                        context?.getString(R.string.please_enter_valid_email_login)
                    editTextLoginUser.requestFocus()
                    return false
                }
                userLogin.password.isBlank() -> {
                    editTextLoginPassword.error =
                        context?.getString(R.string.please_enter_password)
                    editTextLoginPassword.requestFocus()
                    return false
                }
            }
            return true
        }
    }

    private fun navigation(resourceUser: ResourceUser<Void>) {
        this.messageToast(resourceUser.message?.let { message ->
            context?.getString(message)
        })
        when (resourceUser.login) {
            ON_START -> {
                this.viewModel.navController(ON_START)
            }
            FIRST_LOGIN -> {
                this.viewModel.navController(FIRST_LOGIN)
            }
        }
    }

    private fun statusBarNavigation() {
        statusAppBarNavigationBase(
            menu = FALSE_MENU,
            components = Components(
                appBar = FALSE,
                bottomNavigation = FALSE,
                floatingActionButton = FALSE
            ),
            barColor = R.color.white_status_bar
        )
    }

    private fun messageToast(message: String?) {
        message?.let { message -> toast(message) }
    }

    override fun onDestroy() {
        super.onDestroy()
        this._viewDataBinding = null
    }
}