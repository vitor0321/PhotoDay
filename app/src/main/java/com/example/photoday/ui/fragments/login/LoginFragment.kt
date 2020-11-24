package com.example.photoday.ui.fragments.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fragmenttest.activity.state.Components
import com.example.photoday.R
import com.example.photoday.injector.ViewModelInjector
import com.example.photoday.repository.LoginRepositoryShared
import com.example.photoday.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(){
    private val viewModel by lazy {
        val sharedPref by lazy { requireActivity().getPreferences(Context.MODE_PRIVATE) }
        val repositoryShared = LoginRepositoryShared(sharedPref)
        ViewModelInjector.providerLoginViewModel(repositoryShared)
    }
    private val stateViewModel  by lazy { ViewModelInjector.providerStateAppViewModel() }
    private val controlNavigation by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stateViewModel.hasComponents = Components(false,false)
        login_button_log.setOnClickListener {
            viewModel.login()
            viewModel.navFragmentLogin(controlNavigation)
        }
        login_button_register.setOnClickListener {
            viewModel.navFragmentRegister(controlNavigation)
        }
    }


}