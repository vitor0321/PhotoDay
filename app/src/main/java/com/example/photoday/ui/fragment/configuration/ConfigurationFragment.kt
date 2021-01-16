package com.example.photoday.ui.fragment.configuration

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.photoday.R
import com.example.photoday.constants.FALSE
import com.example.photoday.constants.TRUE
import com.example.photoday.databinding.FragmentConfigurationBinding
import com.example.photoday.ui.PhotoDayActivity
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.injector.ViewModelInjector
import com.example.photoday.ui.navigation.Navigation.navFragmentConfigurationToSplashGoodbye
import com.example.photoday.ui.stateBarNavigation.Components
import com.google.firebase.auth.FirebaseAuth

class ConfigurationFragment : BaseFragment() {

    private var _binding: FragmentConfigurationBinding? = null
    private val binding: FragmentConfigurationBinding get() = _binding!!
    private val navFragment by lazy { findNavController() }
    private val viewModel by lazy {
        ViewModelInjector.providerConfigurationViewModel(
            context,
            layoutInflater
        )
    }
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfigurationBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_fragment_configuration, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initButton()
        getDataStoreUser()
        statusBarNavigation()
    }

    private fun initButton() {
        binding.apply {
            /*Button logout*/
            btnLogout.setOnClickListener {
                /*logout with Firebase*/
                viewModel.logout()
                navFragmentConfigurationToSplashGoodbye(navFragment)
            }

            /*Button edit user photo*/
            btnEditPhotoUser.setOnClickListener { viewModel.photoDialog(activity) }

            /*Button edit user name */
            btnEditNameUser.setOnClickListener {
                viewModel.alertDialogNewUserName(lifecycleScope
                )
            }
        }
    }

    private fun getDataStoreUser() {
        /*set name,email and photo of user*/
        binding.apply {
            viewModel.getDataStoreUser(
                textViewUserName,
                textViewUserEmail,
                imageUser,
                context,
                lifecycleScope
            )
        }
    }

    private fun statusBarNavigation() {
        /*show OptionsMenu when inflate*/
        setHasOptionsMenu(true)
        arguments?.let {}

        /*Sending status AppBar and Bottom Navigation to the Activity*/
        val statusAppBarNavigation = Components(TRUE, FALSE)
        val mainActivity = requireActivity() as PhotoDayActivity
        mainActivity.statusAppBarNavigation(statusAppBarNavigation)

        /*change color statusBar*/
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.orange_status_bar)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}

