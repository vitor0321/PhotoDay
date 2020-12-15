package com.example.photoday.stateAppBarBottonNavigation

/*são os componentes que vão aparecer em cada fragment, cada uma vai dicir se aparece ou não
* por padrão vai como falso, não vai aparecer*/

data class Components(
    var appBar: Boolean = false,
    var bottomNavigation: Boolean = false
)
