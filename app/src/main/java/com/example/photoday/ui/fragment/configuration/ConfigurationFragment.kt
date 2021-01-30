package com.example.photoday.ui.fragment.configuration

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.view.*
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.photoday.R
import com.example.photoday.constants.*
import com.example.photoday.databinding.FragmentConfigurationBinding
import com.example.photoday.repository.firebase.ChangeUserFirebase.changeImageUser
import com.example.photoday.ui.dialog.AddPhotoDialog
import com.example.photoday.ui.dialog.NewUserNameDialog
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.injector.ViewModelInjector
import com.example.photoday.ui.navigation.Navigation.navFragmentConfigurationToSplashGoodbye
import com.example.photoday.ui.stateBarNavigation.Components
import java.io.ByteArrayOutputStream


class ConfigurationFragment : BaseFragment() {

    private var _binding: FragmentConfigurationBinding? = null
    private val binding: FragmentConfigurationBinding get() = _binding!!
    private val navFragment by lazy { findNavController() }
    private val viewModel by lazy {
        ViewModelInjector.providerConfigurationViewModel(context)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfigurationBinding.inflate(inflater, container, false)
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

    override fun onResume() {
        binding.apply {
            context?.let { viewModel.getDataStoreUser(it, textViewUserName, textViewUserEmail, imageUser) }
            super.onResume()
        }
    }

    private fun init() {
        initButton()
        initObservers()
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
            btnEditPhotoUser.setOnClickListener { photoDialog() }

            /*Button edit user name */
            btnEditNameUser.setOnClickListener {
                newUserNameDialog()
            }
        }
    }

    private fun initObservers() {
        viewModel.getUserLiveData.observe(viewLifecycleOwner, Observer {
            binding.apply {
                textViewUserName.text = it
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            when {
                requestCode == REQUEST_GALLERY && resultCode == RESULT_OK -> {
                    data?.data?.let {
                        context?.let { context -> changeImageUser(context, it) }
                    }
                }
                requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK -> {
                    val imageBitmap = data?.extras?.get(ContactsContract.Intents.Insert.DATA) as Bitmap
                    val bytes = ByteArrayOutputStream()
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
                    val path: String = MediaStore.Images.Media.insertImage(
                            context?.contentResolver, imageBitmap, getString(R.string.change_image_user), null)
                    context?.let { context -> changeImageUser(context, Uri.parse(path)) }
                }
            }
        } catch (e: Exception) {
            e.message?.let { context?.let { context -> Utils.toast(context, it.toInt()) } }
        }
    }

    private fun photoDialog() {
        /*open AddPhotoDialog*/
        activity?.let {
            AddPhotoDialog.newInstance()
                    .show(it.supportFragmentManager, ADD_PHOTO_DIALOG)
        }
    }

    private fun newUserNameDialog() {
        /*open NewUserNameDialog*/
        activity?.let {
            NewUserNameDialog.newInstance()
                    .show(it.supportFragmentManager, NEW_USER_NAME)
        }
    }

    private fun statusBarNavigation() {
        statusAppBarNavigationBase(true, Components(TRUE, FALSE), R.color.orange_status_bar)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}

