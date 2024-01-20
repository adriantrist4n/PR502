package com.adriantrist4n.pr502.screens

import android.annotation.SuppressLint
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.adriantrist4n.pr502.navigations.AppNavigation
import com.adriantrist4n.pr502.navigations.AppScreens
import com.adriantrist4n.pr502.ui.theme.ColorScaffold
import com.adriantrist4n.pr502.ui.theme.TitleScaffold


/**
 * Crea el esqueleto principal (Scaffold) de la aplicación.
 *
 * Este Scaffold configura y organiza la barra superior (TopAppBar), la barra de navegación inferior (BottomNavigation)
 * y el contenido principal de la aplicación. Utiliza un NavController para gestionar la navegación entre las
 * diferentes pantallas de la aplicación.
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldContent(){
    val navController = rememberNavController()

    Scaffold(
        topBar = { TopBarContent() },
        bottomBar = {BottomNavigationContent(navController)},
        content = { AppNavigation(navController) }
    )
}

/**
 * Crea una barra superior (TopAppBar) para la aplicación.
 *
 * Esta barra superior incluye un botón de cierre que termina la aplicación cuando se hace clic en él.
 * También muestra el título de la aplicación.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarContent() {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = {
                System.exit(0)
            }) {
                Icon(imageVector = Icons.Filled.Close, contentDescription = "Close", tint = Color.Black)
            }
        },
        title = { Text(text = "PR502", color = TitleScaffold, fontWeight = FontWeight.Bold) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = ColorScaffold)
    )
}


/**
 * Define el contenido de la barra de navegación inferior en la aplicación.
 *
 * Esta barra de navegación permite al usuario cambiar entre diferentes pantallas de la aplicació.
 *
 * @param navController El NavController que gestiona la navegación entre las pantallas de la aplicación.
 */
@Composable
fun BottomNavigationContent(navController: NavController) {
    // Lista de pantallas que aparecerán en la barra de navegación inferior.
    val items = listOf(AppScreens.Lista)

    BottomNavigation(backgroundColor = ColorScaffold) {
        // Observa los cambios en la pila de navegación para actualizar el estado seleccionado.
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        items.forEach {
            // Determina si el ítem está seleccionado comparando la ruta actual con la ruta del ítem.
            val isSelected = currentRoute == it.route
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = it.icon),
                        contentDescription = null,
                        tint = if (isSelected) TitleScaffold else Color.Black
                    )
                },
                label = {
                    Text(
                        text = it.title,
                        color = if (isSelected) TitleScaffold else Color.Black
                    )
                },
                selected = isSelected,
                onClick = {
                    // Navega a la ruta correspondiente.
                    navController.navigate(it.route) {
                        // Configura el comportamiento de navegación.
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}