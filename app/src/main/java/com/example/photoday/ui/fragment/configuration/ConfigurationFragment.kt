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
import com.example.photoday.constants.toast.Toast.toast
import com.example.photoday.databinding.FragmentConfigurationBinding
import com.example.photoday.ui.databinding.data.UserFirebaseData
import com.example.photoday.ui.dialog.AddPhotoDialog
import com.example.photoday.ui.dialog.NewUserNameDialog
import com.example.photoday.ui.fragment.base.BaseFragment
import com.example.photoday.ui.stateBarNavigation.Components
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.io.ByteArrayOutputStream

class ConfigurationFragment : BaseFragment() {

    private var _viewDataBinding: FragmentConfigurationBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    private val userFirebaseData by lazy { UserFirebaseData() }

    private val viewModel: ConfigurationViewModel by viewModel {
        parametersOf(findNavController())
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
        this.viewModel.logout().observe(viewLifecycleOwner, { resourceMessage ->
            when {
                resourceMessage.error != null -> {
                    messageToast(resourceMessage.error)
                }
                resourceMessage.message != null -> {
                    messageToast(context?.getString(resourceMessage.message))
                }
            }
        })
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
        NewUserNameDialog.newInstance()
    }

    private fun statusBarNavigation() {
        statusAppBarNavigationBase(
            menu = FALSE_MENU,
            components = Components(
                appBar = TRUE,
                bottomNavigation = TRUE,
                floatingActionButton = FALSE),
            barColor = R.color.orange_status_bar)
    }

    private fun messageToast(message: String?) {
        message?.let { message -> toast(message) }
    }

    override fun onDestroy() {
        super.onDestroy()
        this._viewDataBinding = null
    }
}

