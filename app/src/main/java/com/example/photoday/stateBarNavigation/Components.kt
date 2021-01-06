package com.example.photoday.stateBarNavigation

import com.example.photoday.constants.FALSE

/*são os componentes que vão aparecer em cada fragment, cada uma vai dicir se aparece ou não
* por padrão vai como falso, não vai aparecer*/

data class Components(
    val appBar: Boolean = FALSE,
    val bottomNavigation: Boolean = FALSE
)
