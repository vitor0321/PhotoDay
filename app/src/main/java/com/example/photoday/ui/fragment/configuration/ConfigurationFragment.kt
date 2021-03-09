package com.example.photoday.ui.fragment.configuration

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.photoday.R
import com.example.photoday.constants.*
import com.example.photoday.constants.Utils.toast
import com.example.photoday.databinding.FragmentConfigurationBinding
import com.example.photoday.navigation.Navigation.navFragmentConfigurationToSplashGoodbye
import com.example.photoday.repository.BaseRepositoryUser
import com.example.photoday.ui.dialog.AddPhotoDialog
import com.example.photoday.ui.dialog.NewUserNameDialog
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.injector.ViewModelInjector
import com.example.photoday.ui.stateBarNavigation.Components
import com.google.firebase.auth.FirebaseAuth
import java.io.ByteArrayOutputStream

class ConfigurationFragment : BaseFragment() {

    private var _binding: FragmentConfigurationBinding? = null
    private val binding get() = _binding!!

    private val navFragment by lazy { findNavController() }
    private val baseRepositoryUser: BaseRepositoryUser = BaseRepositoryUser()
    private val viewModel by lazy {
        ViewModelInjector.providerConfigurationViewModel(baseRepositoryUser)
    }
    private var auth = FirebaseAuth.getInstance()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfigurationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getUserDBFirebase()
    }

    private fun init() {
        initButton()
        statusBarNavigation()
        initStateFlowObserve()

    }

    private fun initButton() {
        binding.apply {
            /*Button logout*/
            btnLogout.setOnClickListener {
                /*logout with Firebase*/
                context?.let { context -> viewModel.logout(context) }
                navFragmentConfigurationToSplashGoodbye(navFragment)
            }
            /*Button edit user photo*/
            btnEditPhotoUser.setOnClickListener { photoDialog() }
            /*Button edit user name */
            btnEditNameUser.setOnClickListener { newUserNameDialog() }
        }
    }

    private fun initStateFlowObserve() {
        viewModel.uiStateFlow.asLiveData().observe(viewLifecycleOwner) { userFirebase ->
            binding.run {
                val auth = auth.currentUser
                textViewUserName.text = userFirebase.name
                textViewUserEmail.text = userFirebase.email
                userFirebase.image?.let {
                    context?.let { context ->
                        if (auth != null) {
                            Glide.with(context)
                                .load(auth.photoUrl)
                                .fitCenter()
                                .placeholder(R.drawable.ic_photo_edit)
                                .into(imageUser)
                        }
                    }
                }
            }
        }

        viewModel.uiStateFlowMessage.asLiveData().observe(viewLifecycleOwner) { message ->
            context?.let { context -> toast(context, message) }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            try {
                //here get the image of ChangeUserFirebase
                when {
                    requestCode == REQUEST_IMAGE_GALLERY_USER && resultCode == RESULT_OK -> {
                        data?.data?.let { data ->
                            viewModel.imageUser(data)
                        }
                    }
                    requestCode == REQUEST_IMAGE_CAPTURE_USER && resultCode == RESULT_OK -> {
                        val imageBitmap =
                            data?.extras?.get(ContactsContract.Intents.Insert.DATA) as Bitmap
                        val bytes = ByteArrayOutputStream()
                        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
                        val path: String = MediaStore.Images.Media.insertImage(
                            context?.contentResolver,
                            imageBitmap,
                            getString(R.string.change_image_user),
                            null
                        )
                        viewModel.imageUser(Uri.parse(path))
                    }
                }
            } catch (e: Exception) {
                e.message?.let { message -> context?.let { context -> toast(context, message) } }
            }
    }

    private fun photoDialog() {
        /*open AddPhotoDialog*/
        activity?.let {
            AddPhotoDialog.newInstance(null)
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
        statusAppBarNavigationBase(FALSE_MENU, Components(FALSE_MENU, TRUE), R.color.orange_status_bar)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

