package com.example.photoday.ui.fragment.configuration

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.photoday.R
import com.example.photoday.constants.ADD_PHOTO_DIALOG
import com.example.photoday.constants.FALSE
import com.example.photoday.constants.TRUE
import com.example.photoday.databinding.FragmentConfigurationBinding
import com.example.photoday.dialog.AddPhotoDialog
import com.example.photoday.injector.ViewModelInjector
import com.example.photoday.repository.firebase.FirebaseLogout.logout
import com.example.photoday.ui.MainActivity
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.navigation.Navigation.navFragmentConfigurationToSplashGoodbye
import com.example.photoday.ui.stateBarNavigation.Components
import com.google.firebase.auth.FirebaseAuth

class ConfigurationFragment : BaseFragment() {

    private var _binding: FragmentConfigurationBinding? = null
    private val binding: FragmentConfigurationBinding get() = _binding!!
    private val viewModel by lazy { ViewModelInjector.providerConfigurationViewModel() }
    private val navFragment by lazy { findNavController() }
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
        googleSingIn()
        statusBarNavigation()
    }

    private fun initButton() {
        binding.apply {
            /*Button logout*/
            btnLogout.setOnClickListener {
                /*logout with Firebase*/
                context?.let { context -> logout(context) }
                navFragmentConfigurationToSplashGoodbye(navFragment)
            }

            /*Button edit user photo*/
            btnEditPhotoUser.setOnClickListener {
                activity?.let { activity ->
                    AddPhotoDialog.newInstance()
                        .show(activity.supportFragmentManager, ADD_PHOTO_DIALOG)
                }
            }

            /*Button edit user name */
            btnEditNameUser.setOnClickListener {
                viewModel.alertDialogNewUserName(context, layoutInflater, textViewUserName)
            }
        }
    }

    private fun googleSingIn() {
        val context = context
        /*set name,email and photo of user*/
        binding.apply {
            context?.let { context ->
                viewModel.googleSingIn(
                    textViewUserName,
                    textViewUserEmail,
                    imageUser, context
                )
            }
        }
    }

    private fun statusBarNavigation() {
        /*show OptionsMenu when inflate*/
        setHasOptionsMenu(true)
        arguments?.let {}

        /*Sending status AppBar and Bottom Navigation to the Activity*/
        val statusAppBarNavigation = Components(TRUE, FALSE)
        val mainActivity = requireActivity() as MainActivity
        mainActivity.statusAppBarNavigation(statusAppBarNavigation)

        /*change color statusBar*/
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.orange)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
