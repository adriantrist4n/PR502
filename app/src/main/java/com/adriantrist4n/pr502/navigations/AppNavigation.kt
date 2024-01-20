package com.adriantrist4n.pr502.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.adriantrist4n.pr502.screens.ListaScreen


/**
 * Configura la navegación de la aplicación utilizando el componente NavHost.
 *
 * Define las rutas y los componentes asociados para las diferentes pantallas de la aplicación.
 * Utiliza un objeto NavController para manejar la navegación entre pantallas y un ViewModel
 * para compartir datos entre ellas.
 *
 * @param navController Controlador de navegación para manejar la transición entre pantallas.
 */
@Composable
fun AppNavigation(navController: NavController){
    NavHost(navController = navController as NavHostController, startDestination = AppScreens.Lista.route ){
        composable(route = AppScreens.Lista.route){
            ListaScreen()
        }
    }
}