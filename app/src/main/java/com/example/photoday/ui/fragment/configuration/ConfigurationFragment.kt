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
import com.example.photoday.navigation.Navigation.navFragmentConfigurationToSplashGoodbye
import com.example.photoday.repository.firebase.FirebaseLogout.logout
import com.example.photoday.stateBarNavigation.Components
import com.example.photoday.ui.MainActivity
import com.example.photoday.ui.fragment.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_configuration.*

class ConfigurationFragment : BaseFragment() {

    private lateinit var binding: FragmentConfigurationBinding
    private val viewModel by lazy { ViewModelInjector.providerConfigurationViewModel() }
    private val navFragment by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_configuration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentConfigurationBinding.bind(view)
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_fragment_configuration, menu)
    }

    private fun init() {
        initButton()
        firebaseLogin()
        statusBarNavigation()
    }

    private fun initButton() {
        binding.run {
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
                viewModel.alertDialogNewUserName(context, layoutInflater, text_view_user_name)
            }
        }
    }

    private fun firebaseLogin(){
        binding.run {
            viewModel.firebaseSingIn(textViewUserName, textViewUserEmail, imageUser, context)
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
}
