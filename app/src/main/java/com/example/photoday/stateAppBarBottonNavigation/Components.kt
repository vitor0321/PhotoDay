package com.example.fragmenttest.activity.state

/*são os componentes que vão aparecer em cada fragment, cada uma vai dicir se aparece ou não
* por padrão vai como falso, não vai aparecer*/

class Components (
    val appBar: Boolean = false,
    val bottomNavigation: Boolean = false
)
