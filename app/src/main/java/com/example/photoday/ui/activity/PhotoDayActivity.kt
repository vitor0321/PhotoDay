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
import com.example.photoday.ui.common.ExhibitionCameraOrGallery
import com.example.photoday.ui.databinding.data.ComponentsData
import com.example.photoday.ui.dialog.AddItemDialog
import com.example.photoday.ui.dialog.AddNoteDialog
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
    AddItemDialog.AddItemListener, AddNoteDialog.AddNotaListener {

    private var _viewDataBinding: ActivityPhotoDayBinding? = null
    private val viewDataBinding get() = _viewDataBinding!!

    private val viewModel: PhotoDayViewModel by viewModel()

    private val componentsData: ComponentsData by inject {
        parametersOf(this)
    }

    private val exhibition: ExhibitionCameraOrGallery by inject {
        parametersOf(this)
    }

    private var datePhotoEventBus: String? = null

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

    private fun init() {
        initializeControl()
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
                    data?.data?.let { photo ->
                        datePhotoEventBus?.let { dateCalendar ->
                            viewModel.createPushPhoto(dateCalendar, photo)
                                .observe(this, { resources ->
                                    messageToast(resources.message)
                                })
                        }
                    }
                }
                requestCode == REQUEST_IMAGE_GALLERY && resultCode == Activity.RESULT_OK -> {
                    data?.data?.let { photo ->
                        datePhotoEventBus?.let { dateCalendar ->
                            viewModel.createPushPhoto(dateCalendar, photo)
                                .observe(this, { resources ->
                                    messageToast(resources.message)
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
                            viewModel.createPushPhoto(dateCalendar, Uri.parse(path))
                                .observe(this, { resources ->
                                    messageToast(resources.message)
                                })
                        }
                }
            }
        } catch (e: Exception) {
            messageToast(R.string.failure_capture_image)
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

    private fun notaDialog() {
        val itemNote = valueDate?.let { date ->
            ItemNote(date = date)
        }
        AddNoteDialog.newInstance(itemNote).apply {
            listener = this@PhotoDayActivity
        }
            .show(supportFragmentManager, ADD_NOTA_DIALOG)
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

    override fun onNotaSelected(nota: ItemNote?, message: Int?) {
        when (message) {
            null -> {
                nota?.let {
                    this.viewModel.salveNota(nota).observe(this) { ResourceItem ->
                        messageToast(ResourceItem.message)
                    }
                }
            }
            else -> messageToast(message)
        }
    }

    private fun photoDialog() {
        /*open AddPhotoDialog*/
        AddItemDialog.newInstance().apply {
            listener = this@PhotoDayActivity
        }
            .show(supportFragmentManager, ADD_PHOTO_DIALOG)
    }

    private fun messageToast(message: Int?) {
        message?.let { messageInt ->
            val messageToast = this.getString(messageInt)
            toast(messageToast)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        _viewDataBinding = null
    }
}
