package com.example.photoday.ui.stateBarNavigation

import com.example.photoday.constants.FALSE

/**
 * are the components that will appear in each fragment,
 * each one will decide whether or not it appears by default it will be false, it will not appear
 * */

data class Components(
    val appBar: Boolean? = null,
    val bottomNavigation: Boolean? = null,
    val floatingActionButton: Boolean? = null,
)
