package com.example.photoday.ui.fragment.configuration

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.photoday.R
import com.example.photoday.constants.FALSE
import com.example.photoday.constants.TRUE
import com.example.photoday.injector.ViewModelInjector
import com.example.photoday.navigation.Navigation.navFragmentConfigurationToSplashGoodbye
import com.example.photoday.navigation.Navigation.navFragmentConfigurationToTimeline
import com.example.photoday.stateAppBarBottonNavigation.Components
import com.example.photoday.ui.MainActivity
import com.example.photoday.ui.fragment.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_configuration.*

class ConfigurationFragment : BaseFragment() {

    private val viewModel by lazy { ViewModelInjector.providerConfigurationViewModel() }
    private val navFragment by lazy { findNavController() }
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_configuration, container, false)
        auth = FirebaseAuth.getInstance()
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_fragment_configuration, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_fragment_configuration_app_bar -> {
                navFragmentConfigurationToTimeline(navFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun init() {
        /*set name,email and photo of user*/
        viewModel.googleSingIn(text_view_user_name, text_view_user_email)

        initButton()
        statusBarNavigation()
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

    private fun initButton() {
        btn_logout.setOnClickListener {
            /*logout with Firebase*/
            auth.signOut()
            navFragmentConfigurationToSplashGoodbye(navFragment)
        }
    }

}
