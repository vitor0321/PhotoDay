package com.example.photoday.ui.fragment.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.photoday.R
import com.example.photoday.injector.ViewModelInjector
import com.example.photoday.stateAppBarBottonNavigation.SendDataToActivityInterface

abstract class BaseFragment : Fragment() {

    private val viewModel by lazy { ViewModelInjector.providerBaseViewModel() }
    private val navFragment by lazy { findNavController() }
    private lateinit var sendDataToActivityInterface: SendDataToActivityInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*para aparecer o menu quando for inflado*/
        setHasOptionsMenu(true)
        arguments?.let {}

        /*mudar a cor do statusBar*/
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.orange)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_fragment_base, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_fragment_base_app_bar -> {
                viewModel.navFragmentLogin(navFragment)
            }
        }
        return super.onOptionsItemSelected(item)
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