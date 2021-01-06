package com.example.photoday.ui.fragment.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.photoday.R
import com.example.photoday.constants.FALSE
import com.example.photoday.databinding.FragmentRegisterUserBinding
import com.example.photoday.injector.ViewModelInjector
import com.example.photoday.stateBarNavigation.Components
import com.example.photoday.ui.MainActivity
import com.example.photoday.ui.fragment.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_register_user.*

class Register : BaseFragment() {

    private val viewModel by lazy { ViewModelInjector.providerRegisterViewModel() }
    private val controlNavigation by lazy { findNavController() }
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentRegisterUserBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register_user, container, false)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterUserBinding.bind(view)
        init()
    }

    private fun init() {
        statusBarNavigation()
        initButton()
    }

    private fun statusBarNavigation() {
        /*Sending status AppBar and Bottom Navigation to the Activity*/
        val statusAppBarNavigation = Components(FALSE, FALSE)
        val mainActivity = requireActivity() as MainActivity
        mainActivity.statusAppBarNavigation(statusAppBarNavigation)

        /*change color statusBar*/
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
    }

    private fun initButton() {
        binding.run {
            buttonRegisterUser.setOnClickListener {
                context?.let { context ->
                    viewModel.signUpUser(
                        registerUserId,
                        registerUserPassword,
                        registerUserConfirmPassword,
                        context,
                        controlNavigation
                    )
                }
            }
        }
    }
}

