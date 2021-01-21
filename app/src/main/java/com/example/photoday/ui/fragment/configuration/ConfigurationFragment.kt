package com.example.photoday.ui.fragment.configuration

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.photoday.R
import com.example.photoday.constants.ADD_PHOTO_DIALOG
import com.example.photoday.constants.FALSE
import com.example.photoday.constants.NEW_USER_NAME
import com.example.photoday.constants.TRUE
import com.example.photoday.databinding.FragmentConfigurationBinding
import com.example.photoday.ui.PhotoDayActivity
import com.example.photoday.ui.dialog.AddPhotoDialog
import com.example.photoday.ui.dialog.NewUserNameDialog
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
        ViewModelInjector.providerConfigurationViewModel(context)
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
        initObservers()
        statusBarNavigation()
        context?.let { viewModel.getDataStoreUser(it, lifecycleScope) }
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
            btnEditPhotoUser.setOnClickListener { photoDialog() }

            /*Button edit user name */
            btnEditNameUser.setOnClickListener { newUserName() }
        }
    }

    private fun initObservers() {
        viewModel.getUserLiveData.observe(viewLifecycleOwner, Observer {
            binding.apply {
                textViewUserName.text = it.name
                textViewUserEmail.text = it.email
            }
        })
    }

    private fun photoDialog() {
        /*open AddPhotoDialog*/
        activity?.let {
            AddPhotoDialog.newInstance()
                    .show(it.supportFragmentManager, ADD_PHOTO_DIALOG)
        }
    }

    private fun newUserName() {
        /*open NewUserNameDialog*/
        activity?.let {
            NewUserNameDialog.newInstance()
                    .show(it.supportFragmentManager, NEW_USER_NAME)
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

