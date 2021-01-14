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
import com.example.photoday.ui.injector.ViewModelInjector
import com.example.photoday.ui.PhotoDayActivity
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.stateBarNavigation.Components
import com.google.firebase.auth.FirebaseAuth

class Register : BaseFragment() {

    private var _binding: FragmentRegisterUserBinding? = null
    private val binding: FragmentRegisterUserBinding get() = _binding!!
    private val viewModel by lazy {
        ViewModelInjector.providerRegisterViewModel(
            context,
            controlNavigation
        )
    }
    private val controlNavigation by lazy { findNavController() }
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterUserBinding.inflate(inflater, container, false)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        statusBarNavigation()
        initButton()
    }

    private fun statusBarNavigation() {
        /*Sending status AppBar and Bottom Navigation to the Activity*/
        val statusAppBarNavigation = Components(FALSE, FALSE)
        val mainActivity = requireActivity() as PhotoDayActivity
        mainActivity.statusAppBarNavigation(statusAppBarNavigation)

        /*change color statusBar*/
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
    }

    private fun initButton() {
        binding.apply {
            buttonRegisterUser.setOnClickListener {
                viewModel.signUpUser(
                    editTextUserEmail,
                    editTextUserPassword,
                    editTextUserConfirmPassword
                )
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}

