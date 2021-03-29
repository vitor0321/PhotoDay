package com.example.photoday.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.view.View
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.photoday.R
import com.example.photoday.constants.ADD_PHOTO_DIALOG
import com.example.photoday.constants.REQUEST_IMAGE_CAPTURE
import com.example.photoday.constants.REQUEST_IMAGE_GALLERY
import com.example.photoday.constants.toast.Toast.toast
import com.example.photoday.databinding.ActivityPhotoDayBinding
import com.example.photoday.eventBus.MessageEvent
import com.example.photoday.repository.BaseRepositoryPhoto
import com.example.photoday.ui.databinding.data.ComponentsData
import com.example.photoday.ui.dialog.AddPhotoDialog
import com.example.photoday.ui.injector.ViewModelInjector
import com.example.photoday.ui.stateBarNavigation.Components
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class PhotoDayActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private var _viewDataBinding: ActivityPhotoDayBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    private val viewModel by lazy {
        val baseRepositoryPhoto = BaseRepositoryPhoto()
        ViewModelInjector.providerPhotoDayViewModel(baseRepositoryPhoto)
    }
    private val componentsData by lazy { ComponentsData() }

    private var datePhotoEventBus: String? = null

    @SuppressLint("SimpleDateFormat")
    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewDataBinding = ActivityPhotoDayBinding.inflate(layoutInflater)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.components = componentsData
        setContentView(viewDataBinding.root)
        init()
    }

    override fun onStart() {
        super.onStart()
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    private fun init() {
        initializeControl()
        initButton()
    }

    private fun initButton() {
        viewDataBinding.apply {
            datePickerFloatButton = View.OnClickListener { datePicker() }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        supportFragmentManager.primaryNavigationFragment?.childFragmentManager?.fragments?.forEach { fragment ->
            //Here sent Result to Fragment
            fragment.onActivityResult(requestCode, resultCode, data)
        }
        try {
            //here get the image from Exhibition
            when {
                requestCode == REQUEST_IMAGE_GALLERY && resultCode == Activity.RESULT_OK -> {
                    data?.data?.let { photo ->
                        datePhotoEventBus?.let { dateCalendar ->
                            viewModel.createPushPhoto(dateCalendar, photo, this)
                                .observe(this, { resourcesError ->
                                    resourcesError.error?.let { message -> toast(message) }
                                })
                        }
                    }
                }
                requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK -> {
                    val imageBitmap =
                        data?.extras?.get(ContactsContract.Intents.Insert.DATA) as Bitmap
                    val bytes = ByteArrayOutputStream()
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
                    val path: String = MediaStore.Images.Media.insertImage(
                        this.contentResolver,
                        imageBitmap,
                        getString(R.string.change_image_user),
                        null
                    )
                        datePhotoEventBus?.let { dateCalendar ->
                            viewModel.createPushPhoto(dateCalendar, Uri.parse(path), this)
                                .observe(this,
                                    { resourcesError ->
                                        resourcesError.error?.let { message -> toast(message) }
                                    })
                        }
                }
            }
        } catch (e: Exception) {
            e.message?.let { message -> toast(message) }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(datePhoto: MessageEvent) {
        /*The Event Bus get date that Exhibition send*/
        datePhotoEventBus = datePhoto.message
    }

    private fun initializeControl() {
        viewDataBinding.apply {
            try {
                val navHostFragment =
                    supportFragmentManager.findFragmentById(R.id.main_activity_nav_host) as NavHostFragment
                val navController: NavController = navHostFragment.navController
                //background menu navigation
                bottomNavMainActivity.background = null
                /*Action Bar Gone*/
                supportActionBar?.hide()
                /* control all bottom navigation navigation */
                bottomNavMainActivity.setupWithNavController(navController)
                navController
                    .addOnDestinationChangedListener { controller, destination, arguments ->
                        /* change the fragment title as it is in the nav_graph Label */
                        title = null
                    }
            } catch (e: Exception) {
                e.message?.let { message -> toast(message) }
            }

        }
    }

    private fun datePicker() {
        /*inflater datePicker*/
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        try {
            DatePickerDialog(this, this, year, month, day ).show()
        } catch (e: Exception) {
            e.message?.let { message -> toast(message) }
        }
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val valueDate = simpleDateFormat.format(calendar.time)
        //get select date and send to photoDialog
        photoDialog(valueDate)
    }

    fun statusAppBarNavigation(components: Components) {
        componentsData.setComponentsData(components)
    }

    private fun photoDialog(valueDate: String) {
        /*open AddPhotoDialog*/
        AddPhotoDialog.newInstance(valueDate)
            .show(supportFragmentManager, ADD_PHOTO_DIALOG)
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewDataBinding = null
    }
}
