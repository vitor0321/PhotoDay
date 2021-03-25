package com.example.photoday.ui.databinding.data

import androidx.lifecycle.MutableLiveData
import com.example.photoday.ui.stateBarNavigation.Components

class ComponentsData(
    private var components: Components = Components(),
    val appBar: MutableLiveData<Boolean> = MutableLiveData<Boolean>().also {
        it.value = components.appBar
    },
    val bottomNavigation: MutableLiveData<Boolean> = MutableLiveData<Boolean>().also {
        it.value = components.bottomNavigation
    },
    val floatingActionButton: MutableLiveData<Boolean> = MutableLiveData<Boolean>().also {
        it.value = components.floatingActionButton
    },
) {
    fun setComponentsData(components: Components) {
        this.components = components
        appBar.postValue(this.components.appBar)
        bottomNavigation.postValue(this.components.bottomNavigation)
        floatingActionButton.postValue(this.components.floatingActionButton)
    }

    fun getComponentsData(): Components? {
        return this.components.copy(
            appBar = appBar.value ?: return null,
            bottomNavigation = bottomNavigation.value ?: return null,
            floatingActionButton = floatingActionButton.value ?: return null
        )
    }
}