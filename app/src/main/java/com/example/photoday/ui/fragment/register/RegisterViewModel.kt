package com.example.photoday.ui.fragment.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.photoday.navigation.Navigation
import com.example.photoday.repository.BaseRepositoryUser
import com.example.photoday.model.resource.ResourceUser
import com.example.photoday.model.user.UserLogin

class RegisterViewModel(
    private val navFragment: NavController,
    private val repository: BaseRepositoryUser,
) : ViewModel() {

    fun signUpUser(userLogin: UserLogin): LiveData<ResourceUser<Void>> =
        repository.baseRepositoryCreateUserWithEmailAndPassword(userLogin)

    fun navigationRegister(){
        Navigation.navFragmentRegisterToLogin(navFragment)
    }
}


