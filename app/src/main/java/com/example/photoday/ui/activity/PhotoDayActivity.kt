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
import com.example.photoday.constants.*
import com.example.photoday.constants.Utils.toast
import com.example.photoday.databinding.ActivityPhotoDayBinding
import com.example.photoday.eventBus.MessageEvent
import com.example.photoday.repository.BaseRepositoryPhoto.baseRepositoryUploadImageToStorage
import com.example.photoday.ui.dialog.AddPhotoDialog
import com.example.photoday.ui.stateBarNavigation.Components
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class PhotoDayActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var binding: ActivityPhotoDayBinding
    private var datePhotoEventBus: String? = null

    @SuppressLint("SimpleDateFormat")
    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoDayBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        binding.apply {
            buttonFabAdd.setOnClickListener { datePicker() }
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
                requestCode == REQUEST_GALLERY_TIMELINE && resultCode == Activity.RESULT_OK -> {
                    data?.data?.let { photo ->
                            datePhotoEventBus?.let { dateCalendar ->
                                baseRepositoryUploadImageToStorage(
                                    this,
                                    dateCalendar,
                                    photo
                                )
                            }
                    }
                }
                requestCode == REQUEST_IMAGE_CAPTURE_TIMELINE && resultCode == Activity.RESULT_OK -> {
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
                            baseRepositoryUploadImageToStorage(
                                this,
                                dateCalendar,
                                Uri.parse(path)
                            )
                        }
                }
            }
        } catch (e: Exception) {
            e.message?.let {toast(this, it.toInt()) }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(datePhoto: MessageEvent) {
        /*The Event Bus get date that Exhibition send*/
        datePhotoEventBus = datePhoto.message
    }

    private fun initializeControl() {
        binding.apply {
            try {
                val navHostFragment =
                        supportFragmentManager.findFragmentById(R.id.main_activity_nav_host) as NavHostFragment
                val navController: NavController = navHostFragment.navController
                // all components start with HIDE and then each fragment decides what appears or not
                supportActionBar?.hide()
                bottomAppBar.visibility = View.INVISIBLE
                buttonFabAdd.visibility = View.INVISIBLE
                bottomNavMainActivity.visibility = View.INVISIBLE
                //background menu navigation
                bottomNavMainActivity.background = null
                /* control all bottom navigation navigation */
                bottomNavMainActivity.setupWithNavController(navController)
                navController
                        .addOnDestinationChangedListener { controller, destination, arguments ->
                            /* change the fragment title as it is in the nav_graph Label */
                            title = null
                        }
            } catch (e: Exception) {
                e.message?.let { Utils.toast(this@PhotoDayActivity, it.toInt()) }
            }

        }
    }

    fun statusAppBarNavigation(stateComponents: Components) {
        try {
            binding.apply {
                when {
                    /* here will activate or not the actionBar */
                    stateComponents.appBar -> supportActionBar?.show()
                    !stateComponents.appBar -> supportActionBar?.hide()
                }
                when {
                    /*here will activate or not the Bottom navegation*/
                    stateComponents.bottomNavigation -> {
                        bottomNavMainActivity.visibility = View.VISIBLE
                        bottomAppBar.visibility = View.VISIBLE
                        buttonFabAdd.visibility = View.VISIBLE
                    }
                    !stateComponents.bottomNavigation -> {
                        bottomNavMainActivity.visibility = View.INVISIBLE
                        bottomAppBar.visibility = View.INVISIBLE
                        buttonFabAdd.visibility = View.INVISIBLE
                    }
                }
            }
        } catch (e: Exception) {
            e.message?.let { toast(this@PhotoDayActivity, it.toInt()) }
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
            e.message?.let { toast(this@PhotoDayActivity, it.toInt()) }
        }
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val valueDate = simpleDateFormat.format(calendar.time)
        //get select date and send to photoDialog
        photoDialog(valueDate)
    }

    private fun photoDialog(valueDate: String) {
        /*open AddPhotoDialog*/
        AddPhotoDialog.newInstance(valueDate)
                .show(supportFragmentManager, ADD_PHOTO_DIALOG)
    }

    override fun onDestroy() {
        binding
        super.onDestroy()
    }
}
