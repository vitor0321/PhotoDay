package com.example.photoday.ui.fragment.base

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.photoday.R
import com.example.photoday.injector.ViewModelInjector
import com.example.photoday.repository.LoginRepositoryShared


abstract class BaseFragment : Fragment() {

    private val loginViewModel by lazy {
        val sharedPref by lazy { requireActivity().getPreferences(Context.MODE_PRIVATE) }
        val repositoryShared = LoginRepositoryShared(sharedPref)
        ViewModelInjector.providerLoginViewModel(repositoryShared)
    }
    private val viewModel by lazy { ViewModelInjector.providerBaseViewModel() }
    private val navFragment by lazy { findNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.init(loginViewModel, navFragment)
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
}