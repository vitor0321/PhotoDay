package com.example.photoday.ui.fragment.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import com.example.photoday.R
import com.example.photoday.constants.*
import com.example.photoday.databinding.FragmentLoginBinding
import com.example.photoday.ui.dialog.ForgotPasswordDialog
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.model.item.Components
import com.example.photoday.ui.model.resource.ResourceUser
import com.example.photoday.ui.model.user.UserLogin
import com.example.photoday.ui.toast.Toast.toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class LoginFragment : BaseFragment(), ForgotPasswordDialog.ForgotPasswordListener {

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
            forgotPasswordButton = View.OnClickListener { forgotPassword(activity) }
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
            messageToast(R.string.failure_login_user)
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
        when(resourceUser.message){
            TRUE->messageToast(R.string.login_is_success)
            FALSE-> messageToast(R.string.verify_your_email_address)
            null->messageToast(R.string.failure_api_login)
        }
        when (resourceUser.login) {
            ON_START -> {
                this.viewModel.navController(ON_START)
                onDestroy()
            }
            FIRST_LOGIN -> {
                this.viewModel.navController(FIRST_LOGIN)
                onDestroy()
            }
            ERROR_LOGIN -> {
                this.messageToast(R.string.check_your_email_and_confirm)
            }
        }
    }

    private fun forgotPassword(activity: FragmentActivity?) {
        activity?.let {
            ForgotPasswordDialog.newInstance().apply {
                listener = this@LoginFragment
            }
                .show(it.supportFragmentManager, FORGOT_PASSWORD)
        }
    }

    override fun onEmailSelected(email: String) {
        this.viewModel.forgotPassword(email).observe(viewLifecycleOwner, { resource ->
            when (resource.message) {
                TRUE -> messageToast(R.string.email_sent)
                FALSE -> messageToast(R.string.unregistered_email)
                null -> messageToast(R.string.failure_api_login)
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