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
import com.example.photoday.ui.common.ExhibitionCameraOrGallery
import com.example.photoday.ui.databinding.data.UserFirebaseData
import com.example.photoday.ui.dialog.AddItemPhotoDialog
import com.example.photoday.ui.dialog.NewUserNameDialog
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.model.item.Components
import com.example.photoday.ui.model.user.UserFirebase
import com.example.photoday.ui.toast.Toast.toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.io.ByteArrayOutputStream

class ConfigurationFragment : BaseFragment(), AddItemPhotoDialog.AddItemListener,
    NewUserNameDialog.NewUserNameListener {

    private var _viewDataBinding: FragmentConfigurationBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    private val viewModel: ConfigurationViewModel by viewModel {
        parametersOf(findNavController())
    }
    private val exhibition: ExhibitionCameraOrGallery by inject {
        parametersOf(this)
    }
    private val userFirebaseData: UserFirebaseData by inject {
        parametersOf(this)
    }

    private val auth: FirebaseAuth by inject()

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
            checkMessage(resourceUser.message)
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
                            checkMessage(resourceUser.message)
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
                        checkMessage(resourceUser.message)
                    })
                }
            }
        } catch (e: Exception) {
            messageToast(R.string.failure_capture_image_user)
        }
    }

    private fun logout() {
        CoroutineScope(Dispatchers.Main).launch {
            /*logout with Firebase*/
            viewModel.logout().observe(viewLifecycleOwner) { resource ->
                resource.login?.let {
                    messageToast(R.string.successfully_logged)
                    viewModel.navController(GOODBYE)
                    auth.signOut()
                    onDestroy()
                } ?: run {
                    messageToast(R.string.failure_api)
                }
            }
        }
    }

    private fun photoDialog() {
        /*open AddPhotoDialog*/
        activity?.let { activity ->
            AddItemPhotoDialog.newInstance().apply {
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
                messageToast(R.string.error_version_less_23)
            }
        }
    }

    private fun newUserNameDialog() {
        /*open NewUserNameDialog*/
        activity?.let { activity ->
            NewUserNameDialog.newInstance().apply {
                listener = this@ConfigurationFragment
            }
                .show(activity.supportFragmentManager, NEW_NAME)
        }
    }

    override fun onNewNameSelected(userFirebase: UserFirebase?, message: Int?) {
        message?.let {
            userFirebase?.let {
                viewModel.newNameUser(userFirebase).observe(this, { resourceResult ->
                    this.userFirebaseData.setData(resourceResult.data)
                    resourceResult.message?.let {
                        messageToast(R.string.name_change_successful)
                    } ?: run { messageToast(R.string.failure_api) }
                })
            }
        } ?: run { messageToast(message) }
    }

    private fun statusBarNavigation() {
        statusAppBarNavigationBase(
            menu = FALSE_MENU,
            components = Components(
                appBar = TRUE,
                bottomNavigation = TRUE,
                floatingActionButton = FALSE,
                actionBar = FALSE
            ),
            barColor = R.color.orange_status_bar
        )
    }

    private fun checkMessage(check: Boolean?) {
        check?.let {
            messageToast(R.string.image_change_successful)
        }
    }

    private fun messageToast(message: Int?) {
        message?.let { messageInt ->
            val messageToast = this.getString(messageInt)
            toast(messageToast)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this._viewDataBinding = null
    }
}

