package com.example.photoday.ui.fragment.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.photoday.R
import com.example.photoday.constants.DEFAULT_WEB_CLIENT_ID
import com.example.photoday.constants.FALSE
import com.example.photoday.constants.ONSTART
import com.example.photoday.constants.RC_SIGN_IN
import com.example.photoday.constants.Uteis.showToast
import com.example.photoday.injector.ViewModelInjector
import com.example.photoday.navigation.Navigation.navFragmentLoginToRegister
import com.example.photoday.stateBarNavigation.Components
import com.example.photoday.ui.MainActivity
import com.example.photoday.ui.fragment.login.Logout.updateUI
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private val viewModel by lazy { ViewModelInjector.providerLoginViewModel() }
    private val controlNavigation by lazy { findNavController() }
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
        init()
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser, controlNavigation, context, ONSTART)
    }

    private fun init() {
        // Configure Google Sign In
        createRequestLoginGoogle()
        initButton()
        statusBarNavigation()
    }

    private fun initButton() {
        //Button to login
        login_button_log.setOnClickListener {
            viewModel.doLogin(
                login_user_id,
                login_password,
                auth,
                requireActivity(),
                context,
                controlNavigation
            )
        }

        //Button register
        login_button_register.setOnClickListener { navFragmentLoginToRegister(controlNavigation) }

        //Button Login Google
        login_button_google.setOnClickListener { signIn() }

        //Button forgot Password
        login_button_forgot_password.setOnClickListener { alertDialogForgotPassword() }
    }

    private fun statusBarNavigation() {
        /*Sending status AppBar and Bottom Navigation to the Activity*/
        val statusAppBarNavigation = Components(FALSE, FALSE)
        val mainActivity = requireActivity() as MainActivity
        mainActivity.statusAppBarNavigation(statusAppBarNavigation)

        /*change color statusBar*/
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent
        when (requestCode) {
            RC_SIGN_IN -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    viewModel.firebaseAuthWithGoogle(
                        account.idToken!!,
                        auth,
                        controlNavigation,
                        context
                    )
                } catch (e: ApiException) {
                    e.message?.let { showToast(requireActivity(), it) }
                }
            }
        }
    }

    private fun alertDialogForgotPassword() {
        /*Alert Dialog Forgot the password*/
        val builder =
            context?.let { context -> AlertDialog.Builder(context, R.style.MyDialogTheme) }
        val inflater = layoutInflater
        builder?.setTitle(getString(R.string.what_is_your_email))
        val view = inflater.inflate(R.layout.dialog_forgot_password, null)
        val userEmail = view.findViewById<EditText>(R.id.edit_text_email_confirm)
        builder?.setView(view)
        builder?.setPositiveButton(getString(R.string.ok)) { _, _ ->
            viewModel.forgotPassword(userEmail,context, auth)
        }
        builder?.setNegativeButton(getString(R.string.cancel)) { _, _ -> }
        builder?.show()
    }
}