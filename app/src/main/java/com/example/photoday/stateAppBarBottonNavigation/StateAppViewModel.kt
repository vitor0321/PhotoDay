package com.example.fragmenttest.activity.state

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StateAppViewModel : ViewModel() {

        /*LiveData publico, que pode ser usado*/
        val components: LiveData<Components> get() = _components

        /*LiveData privado que será usado somente pela classe*/
        private val _components = MutableLiveData<Components>().also {
            it.value = hasComponents
        }

        var hasComponents: Components = Components()
        set(value) {
            field = value
            _components.value = value
        }

}


