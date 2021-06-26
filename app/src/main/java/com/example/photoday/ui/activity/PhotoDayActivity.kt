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
import com.example.photoday.databinding.ActivityPhotoDayBinding
import com.example.photoday.eventBus.MessageEvent
import com.example.photoday.generated.callback.OnClickListener
import com.example.photoday.ui.common.ExhibitionCameraOrGallery
import com.example.photoday.ui.databinding.data.ComponentsData
import com.example.photoday.ui.databinding.data.ItemNoteData
import com.example.photoday.ui.dialog.AddItemPhotoDialog
import com.example.photoday.ui.dialog.AddNoteDialog
import com.example.photoday.ui.fragment.configuration.ConfigurationFragment
import com.example.photoday.ui.model.item.ItemNote
import com.example.photoday.ui.toast.Toast.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class PhotoDayActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    AddItemPhotoDialog.AddItemListener, AddNoteDialog.NoteListener,
    ConfigurationFragment.SwitchListener {

    private var _viewDataBinding: ActivityPhotoDayBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    private val viewModel: PhotoDayViewModel by viewModel {
        parametersOf()
    }
    private val componentsData: ComponentsData by inject {
        parametersOf(this)
    }
    private val exhibition: ExhibitionCameraOrGallery by inject {
        parametersOf(this)
    }
    private val itemNoteData: ItemNoteData by inject {
        parametersOf(this)
    }

    private var datePhotoEventBus: String? = null
    private lateinit var controller: NavController

    @SuppressLint("SimpleDateFormat")
    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    private var valueDate: String? = null

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

    override fun onResume() {
        super.onResume()
        controller
    }

    private fun init() {
        initializeControl()
        getStatusSwitchPreferences()
        initButton()
        initObserve()
    }

    private fun initButton() {
        viewDataBinding.apply {
            datePickerFloatButton = View.OnClickListener { datePicker() }
        }
    }

    private fun initObserve() {
        viewModel.component.observe(this, { components ->
            when (components.actionBar) {
                TRUE -> supportActionBar?.show()
                FALSE -> supportActionBar?.hide()
            }
            componentsData.setComponentsData(components)
        })
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
                requestCode == REQUEST_NOTA && resultCode == Activity.RESULT_OK -> {
                    pushPhotoFirebase(data)
                }
                requestCode == REQUEST_IMAGE_GALLERY && resultCode == Activity.RESULT_OK -> {
                    pushPhotoFirebase(data)
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
                            viewModel.createPushPhoto(dateCalendar, Uri.parse(path))
                                .observe(this, { resources ->
                                    checkMessage(resources.message)
                                })
                        }
                }
            }
        } catch (e: Exception) {
            messageToast(R.string.failure_capture_image)
        }
    }

    private fun pushPhotoFirebase(data: Intent?) {
        data?.data?.let { photo ->
            datePhotoEventBus?.let { dateCalendar ->
                viewModel.createPushPhoto(dateCalendar, photo)
                    .observe(this, { resources ->
                        checkMessage(resources.message)
                    })
            }
        }
    }

    private fun checkMessage(message: Boolean?) {
        when (message) {
            TRUE -> messageToast(R.string.successfully_upload_image)
            FALSE -> messageToast(R.string.error_api_photo)
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
                val navHostFragment = supportFragmentManager
                    .findFragmentById(R.id.main_activity_nav_host) as NavHostFragment
                controller = navHostFragment.navController
                controller.graph.setStartDestination(R.id.nav_timelineFragment)

                //background menu navigation
                bottomNavMainActivity.background = null
                /*Action Bar Gone*/
                supportActionBar?.hide()
                controller.addOnDestinationChangedListener { _, destination, _ ->
                   /* when (destination.id) {
                        R.id.nav_galleryFragment -> {
                            ConfigurationFragment.newInstance().apply {
                                listenerSwitch = this@PhotoDayActivity
                            }
                        }
                        eu fiz isso para o listener mas não deu certo....
                    }*/
                    /* change the fragment title as it is in the nav_graph Label */
                    title = null
                }

                /* control all bottom navigation navigation */
                bottomNavMainActivity.setupWithNavController(controller)
            } catch (e: Exception) {
                messageToast(R.string.failure_initialize_control)
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
            messageToast(R.string.failure_initialize_date_picker)
        }
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        valueDate = simpleDateFormat.format(calendar.time)
        //get select date and send to photoDialog
        photoDialog()
    }

    override fun onSwitchSelected(status: Boolean) {
        onSwitchGallerySelected(status)
    }

    private fun getStatusSwitchPreferences() {
        viewModel.getStatusSwitchPreferences().apply {
            onSwitchGallerySelected(this)
        }
    }

    private fun onSwitchGallerySelected(switchCheck: Boolean) {
        when (switchCheck) {
            TRUE -> viewDataBinding.bottomNavMainActivity.menu.hasVisibleItems()
            FALSE -> viewDataBinding.bottomNavMainActivity.menu.removeItem(R.id.nav_galleryFragment)
        }
    }

    override fun onAccessSelected(accessSelected: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            when (accessSelected) {
                ADD_NOTE -> {
                    notaDialog()
                }
                else -> {
                    exhibition.exhibitionCaptureImage(
                        accessSelected,
                        valueDate,
                        this@PhotoDayActivity,
                        REQUEST_PHOTO_DAY
                    )
                }
            }
        }
    }

    private fun photoDialog() {
        /*open AddPhotoDialog*/
        AddItemPhotoDialog.newInstance().apply {
            listener = this@PhotoDayActivity
        }.show(supportFragmentManager, ADD_PHOTO_DIALOG)
    }

    override fun onNotaSelected(nota: ItemNote?, typeDialog: String?) {
        when (typeDialog) {
            SALVE_NOTE -> {
                nota?.let { itemNota ->
                    this.viewModel.salveNota(itemNota).observe(this) { resourceItem ->
                        resourceItem.message?.let {
                            messageToast(R.string.note_add_item)
                        } ?: run { messageToast(R.string.note_add_failure_activity) }
                    }
                }
            }
        }
    }

    private fun notaDialog() {
        valueDate?.let { date ->
            val itemNote = ItemNote(date = date, title = "", note = "")
            itemNoteData.setItemNotaData(itemNote)

            AddNoteDialog.newInstance(itemNote).apply {
                listenerNote = this@PhotoDayActivity
            }
                .show(supportFragmentManager, ADD_NOTA_DIALOG)
        }
    }

    private fun messageToast(message: Int?) {
        message?.let { messageInt ->
            val messageToast = this.getString(messageInt)
            toast(messageToast)
        }
    }

    override fun onPause() {
        controller
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        AddItemPhotoDialog.newInstance().apply {
            listener = null
        }
        _viewDataBinding = null
    }
}
