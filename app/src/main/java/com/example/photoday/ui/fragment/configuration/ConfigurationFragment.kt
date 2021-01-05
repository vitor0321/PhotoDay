package com.example.photoday.ui.fragment.configuration

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.photoday.R
import com.example.photoday.constants.ADD_PHOTO_DIALOG
import com.example.photoday.constants.FALSE
import com.example.photoday.constants.TRUE
import com.example.photoday.dialog.AddPhotoDialog
import com.example.photoday.injector.ViewModelInjector
import com.example.photoday.navigation.Navigation.navFragmentConfigurationToSplashGoodbye
import com.example.photoday.repository.firebase.FirebaseLogout.logout
import com.example.photoday.stateBarNavigation.Components
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

    private fun init() {
        initButton()
        editUser()
        statusBarNavigation()
    }

    private fun initButton() {
        /*Button logout*/
        btn_logout.setOnClickListener {
            /*logout with Firebase*/
            context?.let { context -> logout(context) }
            navFragmentConfigurationToSplashGoodbye(navFragment)
        }

        /*Button edit user photo*/
        btn_edit_photo_user.setOnClickListener {
            activity?.let { activity ->
                AddPhotoDialog.newInstance()
                    .show(activity.supportFragmentManager, ADD_PHOTO_DIALOG)
            }
        }

        /*Button edit user name */
        btn_edit_name_user.setOnClickListener {
            viewModel.alertDialogNewUserName(context, layoutInflater)
        }
    }

    private fun editUser() {
        val fragmentActivity = FragmentActivity()
        val userName = text_view_user_name
        val userEmail = text_view_user_email
        val userImage = image_user

        viewModel.userName.observe(fragmentActivity, Observer {
            userName.text = it
        })
        /*set name,email and photo of user*/
        viewModel.editUser(userName, userEmail, userImage, this)
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
