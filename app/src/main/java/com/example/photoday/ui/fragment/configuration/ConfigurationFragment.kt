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
import androidx.navigation.fragment.findNavController
import com.example.photoday.R
import com.example.photoday.constants.*
import com.example.photoday.databinding.FragmentConfigurationBinding
import com.example.photoday.repository.BaseRepositoryUser
import com.example.photoday.ui.common.ExhibitionCameraOrGallery
import com.example.photoday.ui.databinding.data.UserFirebaseData
import com.example.photoday.ui.dialog.AddPhotoDialog
import com.example.photoday.ui.dialog.NewUserNameDialog
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.stateBarNavigation.Components
import com.example.photoday.ui.toast.Toast.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.io.ByteArrayOutputStream

class ConfigurationFragment : BaseFragment(), AddPhotoDialog.AddPhotoListener {

    private var _viewDataBinding: FragmentConfigurationBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    private val viewModel: ConfigurationViewModel by viewModel {
        parametersOf(findNavController())
    }
    private val repositoryUser: BaseRepositoryUser by inject {
        parametersOf(this)
    }
    private val exhibition: ExhibitionCameraOrGallery by inject {
        parametersOf(this)
    }
    private val userFirebaseData: UserFirebaseData by inject {
        parametersOf(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _viewDataBinding = FragmentConfigurationBinding.inflate(inflater, container, false)
        this.viewDataBinding.userFirebase = userFirebaseData
        this.viewDataBinding.lifecycleOwner = this
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initButton()
        statusBarNavigation()
        initObserve()
    }

    private fun initButton() {
        viewDataBinding.apply {
            /*Button logout*/
            this.logoutButton = View.OnClickListener { logout() }
            /*Button edit user photo*/
            this.addImageClickButton = View.OnClickListener { photoDialog() }
            /*Button edit user name */
            this.changeNameButton = View.OnClickListener { newUserNameDialog() }
        }
    }

    private fun initObserve() {
        this.viewModel.getUserDBFirebase().observe(viewLifecycleOwner, { resourceUser ->
            this.userFirebaseData.setData(resourceUser.data)
            messageToast(resourceUser.error)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            //here get the image of ChangeUserFirebase
            when {
                requestCode == REQUEST_IMAGE_GALLERY_USER && resultCode == RESULT_OK -> {
                    data?.data?.let { data ->
                        viewModel.imageUser(data).observe(this, { resourceUser ->
                            this.userFirebaseData.setData(resourceUser.data)
                            when {
                                resourceUser.error != null -> {
                                    messageToast(resourceUser.error)
                                }
                                resourceUser.message != null -> {
                                    messageToast(context?.getString(resourceUser.message))
                                }
                            }

                        })
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
                    viewModel.imageUser(Uri.parse(path)).observe(this, { resourceUser ->
                        this.userFirebaseData.setData(resourceUser.data)
                        messageToast(resourceUser.error)
                    })
                }
            }
        } catch (e: Exception) {
            messageToast(e.message)
        }
    }

    private fun logout() {
        /*logout with Firebase*/
        this.viewModel.logout().observe(viewLifecycleOwner, { resource ->
            when (resource.login) {
                GOODBYE -> {
                    messageToast(resource.message?.let { message -> context?.getString(message) })
                    viewModel.navController(GOODBYE)
                    onDestroy()
                }
                null -> {
                    messageToast(resource.message?.let { message -> context?.getString(message) })
                }
            }
        })
    }

    private fun photoDialog() {
        /*open AddPhotoDialog*/
        activity?.let { activity ->
            AddPhotoDialog.newInstance().apply {
                listener = this@ConfigurationFragment
            }
                .show(activity.supportFragmentManager, ADD_PHOTO_DIALOG)
        }
    }

    override fun onAccessSelected(accessSelected: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val activity = requireActivity()
                exhibition.exhibitionCaptureImage(
                    accessSelected,
                    null,
                    activity,
                    REQUEST_CONFIGURATION
                )
            } catch (e: Exception) {
                messageToast(context?.getString(R.string.error_version_less_23))
            }
        }
    }

    private fun newUserNameDialog() {
        /*open NewUserNameDialog*/
        activity?.let { activity ->
            NewUserNameDialog.newInstance(repositoryUser, userFirebaseData)
                .show(activity.supportFragmentManager, NEW_NAME)
        }
    }

    private fun statusBarNavigation() {
        statusAppBarNavigationBase(
            menu = FALSE_MENU,
            components = Components(
                appBar = TRUE,
                bottomNavigation = TRUE,
                floatingActionButton = FALSE
            ),
            barColor = R.color.orange_status_bar
        )
    }

    private fun messageToast(message: String?) {
        message?.let { message -> toast(message) }
    }

    override fun onDestroy() {
        super.onDestroy()
        this._viewDataBinding = null
    }
}

