package com.apps.rickandmortyappv2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.apps.rickandmortyappv2.screens.Pantalla1
import com.apps.rickandmortyappv2.screens.Screen2
import com.apps.rickandmortyappv2.api.RickAndMortyViewModel
import com.apps.rickandmortyappv2.navigation.Destinations.Pantalla1
import com.apps.rickandmortyappv2.navigation.Destinations.Pantalla2

@Composable
fun NavigationHost(
    navController: NavHostController,
    viewModel: RickAndMortyViewModel
) {

    NavHost(navController = navController, startDestination = Pantalla1.route) {
        composable(Pantalla1.route) {
            Pantalla1(viewModel = viewModel,
                navegarPantalla2 = { newText ->
                    navController.navigate(Pantalla2.createRoute(newText))
                }
            )
        }
        composable(
            Pantalla2.route,
            arguments = listOf(navArgument("newText") { defaultValue = "Pantalla 2" })
        ) { navBackStackEntry ->
            val newText = navBackStackEntry.arguments?.getString("newText")
            requireNotNull(newText)
            Screen2(newText.toInt(), viewModel = viewModel) {
                navController.popBackStack()
            }
        }
    }
}