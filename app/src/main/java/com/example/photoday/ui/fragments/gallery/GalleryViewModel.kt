package com.example.photoday.ui.fragments.gallery

import androidx.lifecycle.ViewModel
import com.example.photoday.constants.TRUE
import com.example.photoday.stateAppBarBottonNavigation.Components
import com.example.photoday.stateAppBarBottonNavigation.SendDataToActivityInterface

class GalleryViewModel : ViewModel() {

    fun stateAppBarNavigation(sendDataToActivityInterface: SendDataToActivityInterface) {
        /*aqui estamos passando os parametros para estar visivel ou não a AppBar e o Navigation*/
        val components = Components(TRUE, TRUE)
        sendDataToActivityInterface.sendStateComponents(components)
    }
}