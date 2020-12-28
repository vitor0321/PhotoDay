package com.example.photoday.ui.fragment.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.photoday.R
import com.example.photoday.constants.FALSE
import com.example.photoday.constants.FIRSTLOGIN
import com.example.photoday.constants.ONSTART
import com.example.photoday.constants.RC_SIGN_IN
import com.example.photoday.constants.Uteis.showToast
import com.example.photoday.injector.ViewModelInjector
import com.example.photoday.stateAppBarBottonNavigation.Components
import com.example.photoday.ui.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private val viewModel by lazy { ViewModelInjector.providerLoginViewModel() }
    private val controlNavigation by lazy { findNavController() }
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        //create login google
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        createRequestLoginGoogle()
        auth = FirebaseAuth.getInstance()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        buttonNavigation()
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        viewModel.updateUI(currentUser, controlNavigation, context, ONSTART)
    }

    private fun init() {
        /*Sending status AppBar and Bottom Navigation to the Activity*/
        val statusAppBarNavigation = Components(FALSE, FALSE)
        val mainActivity = requireActivity() as MainActivity
        mainActivity.statusAppBarNavigation(statusAppBarNavigation)

        /*change color statusBar*/
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
    }

    private fun buttonNavigation() {
        //Button to login
        login_button_log.setOnClickListener {
            doLogin()
        }

        //Button register
        login_button_register.setOnClickListener {
            viewModel.navFragmentRegister(controlNavigation)
        }

        //Button Login Google
        login_button_google.setOnClickListener {
            viewModel.signIn(googleSignInClient, requireActivity())
        }

        //Button forgot Password
        login_button_forgot_password.setOnClickListener {
            /*Alert Dialog Forgot the password*/
            val builder =
                context?.let { context -> AlertDialog.Builder(context, R.style.MyDialogTheme) }
            val inflater = layoutInflater
            builder?.setTitle(getString(R.string.what_is_your_email))
            val view = inflater.inflate(R.layout.dialog_forgot_password, null)
            val userEmail = view.findViewById<EditText>(R.id.edit_text_email_confirm)
            builder?.setView(view)
            builder?.setPositiveButton(getString(R.string.ok)) { _, _ ->
                forgotPassword(userEmail)
            }
            builder?.setNegativeButton(getString(R.string.cancel)) { _, _ -> }
            builder?.show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                viewModel.firebaseAuthWithGoogle(
                    account.idToken!!,
                    auth,
                    controlNavigation,
                    requireActivity(),
                    context
                )
            } catch (e: ApiException) {
                e.message?.let { showToast(requireActivity(), it) }
            }
        }
    }

    private fun forgotPassword(userEmail: EditText) {
        when {
            userEmail.text.toString().isEmpty() -> {
                showToast(context, getString(R.string.please_enter_email))
            }
            !Patterns.EMAIL_ADDRESS.matcher(userEmail.text.toString()).matches() -> {
                showToast(context, getString(R.string.please_enter_valid_email))
            }
            else -> {
                auth.sendPasswordResetEmail(userEmail.text.toString())
                    .addOnCompleteListener { task ->
                        when {
                            task.isSuccessful -> {
                                showToast(context, getString(R.string.email_sent))
                            }
                            !task.isSuccessful -> {
                                showToast(context, getString(R.string.unregistered_email))
                            }
                        }
                    }
            }
        }
    }

    private fun createRequestLoginGoogle() {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = activity?.let { GoogleSignIn.getClient(it, gso) }!!
    }

    private fun doLogin() {
        /*here you will authenticate your email and password*/
        when {
            login_user_id.text.toString().isEmpty() -> {
                login_user_id.error = getString(R.string.please_enter_email)
                login_user_id.requestFocus()
                return
            }
            !Patterns.EMAIL_ADDRESS.matcher(login_user_id.text.toString()).matches() -> {
                login_user_id.error = getString(R.string.please_enter_valid_email)
                login_user_id.requestFocus()
                return
            }
            login_password.text.toString().isEmpty() -> {
                login_password.error = getString(R.string.please_enter_password)
                login_password.requestFocus()
                return
            }
        }
        signInWithEmailAndPassword()
    }

    private fun signInWithEmailAndPassword() {
        /*checking if the user exists*/
        auth.signInWithEmailAndPassword(
            login_user_id.text.toString(),
            login_password.text.toString()
        )
            .addOnCompleteListener(requireActivity()) { task ->
                when {
                    task.isSuccessful -> {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        viewModel.updateUI(user, controlNavigation, context, FIRSTLOGIN)
                    }
                    else -> {
                        // If sign in fails, display a message to the user.
                        showToast(context, getString(R.string.login_failed))
                        viewModel.updateUI(null, controlNavigation, context, FIRSTLOGIN)
                    }
                }
            }
    }
}