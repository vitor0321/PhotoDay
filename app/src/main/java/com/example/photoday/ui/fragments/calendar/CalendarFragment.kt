package com.example.photoday.ui.fragments.calendar

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.photoday.R
import com.example.photoday.injector.ViewModelInjector
import com.example.photoday.stateAppBarBottonNavigation.SendDataToActivityInterface
import com.example.photoday.ui.fragments.base.BaseFragment

class CalendarFragment : BaseFragment() {

    private val viewModel by lazy { ViewModelInjector.providerCalendarViewModel() }
    private lateinit var sendDataToActivityInterface: SendDataToActivityInterface

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*enviando o status da AppBar e do Navigation a Activity*/
        viewModel.stateAppBarNavigation(sendDataToActivityInterface)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val activity: Activity = context as Activity
        /*ativando a interface para enviar dados a fragment*/
        sendDataToActivityInterface = activity as SendDataToActivityInterface
    }
}
