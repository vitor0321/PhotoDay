package com.example.photoday.ui.fragment.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.photoday.R
import com.example.photoday.constants.FALSE
import com.example.photoday.constants.RC_SIGN_IN
import com.example.photoday.constants.Uteis.showToast
import com.example.photoday.injector.ViewModelInjector
import com.example.photoday.repository.LoginRepositoryShared
import com.example.photoday.stateAppBarBottonNavigation.Components
import com.example.photoday.ui.MainActivity
import com.example.photoday.ui.fragment.base.BaseFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment() {

    private val viewModel by lazy {
        val sharedPref by lazy { requireActivity().getPreferences(Context.MODE_PRIVATE) }
        val repositoryShared = LoginRepositoryShared(sharedPref)
        ViewModelInjector.providerLoginViewModel(repositoryShared)
    }
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
        createRequestLoginGoogle()
        auth = FirebaseAuth.getInstance()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        buttonNavigation()
        viewModel.loginStatus(controlNavigation)
    }

    private fun init() {
        /*Enviando o status do AppBar e do Bottom Navigation para a Activity*/
        val statusAppBarNavigation = Components(FALSE, FALSE)
        val mainActivity = requireActivity() as MainActivity
        mainActivity.statusAppBarNavigation(statusAppBarNavigation)

        /*mudar a cor do statusBar*/
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
    }

    private fun buttonNavigation() {
        //Button para logar
        login_button_log.setOnClickListener {
            viewModel.login()
            viewModel.navFragmentLogin(controlNavigation)
        }

        //Button para registar no login
        login_button_register.setOnClickListener {
            viewModel.navFragmentRegister(controlNavigation)
        }

        //Button Login Google
        login_button_google.setOnClickListener {
            viewModel.signIn(googleSignInClient, requireActivity())
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
                    requireActivity()
                )
            } catch (e: ApiException) {
                e.message?.let { showToast(requireActivity(), it) }
            }
        }
    }
}