package com.adriantrist4n.pr502.navigations

import com.adriantrist4n.pr502.R

/**
 * Clase sellada que representa diferentes pantallas en una aplicación.
 *
 * Cada pantalla tiene un título, un ícono y una ruta asociada. La clase sellada permite una representación
 * segura de tipo para estas pantallas, facilitando el manejo de navegación y presentación en la aplicación.
 *
 * @property title El título de la pantalla, utilizado para representación en la interfaz de usuario.
 * @property icon El recurso de ícono asociado con la pantalla.
 * @property route La ruta de navegación de la pantalla, utilizada en el sistema de navegación de la aplicación.
 */
sealed class AppScreens(
    var title:String,
    var icon:Int,
    var route:String
){
    object Lista: AppScreens("Lista", R.drawable.baseline_ballot_24,"lista_screen")
}