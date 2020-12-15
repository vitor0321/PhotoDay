package com.example.photoday.ui.fragment.calendar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.photoday.R
import com.example.photoday.injector.ViewModelInjector
import com.example.photoday.ui.fragment.base.BaseFragment

class CalendarFragment : BaseFragment() {

    private val viewModelBase by lazy { ViewModelInjector.providerBaseViewModel()}
    private val viewModel by lazy { ViewModelInjector.providerCalendarViewModel()}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity()
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        viewModel.sentStatusToBase(viewModelBase)
    }
}
