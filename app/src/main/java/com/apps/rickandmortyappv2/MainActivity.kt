package com.apps.rickandmortyappv2

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.apps.rickandmortyappv2.navigation.NavigationHost
import com.apps.rickandmortyappv2.api.RickAndMortyViewModel
import com.apps.rickandmortyappv2.ui.theme.RickAndMortyAppv2Theme
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyAppv2Theme() {
                MainScreen()
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val viewModel = viewModel<RickAndMortyViewModel>()
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState(
        drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    )

    Scaffold(
        scaffoldState = scaffoldState,
    ) {
        NavigationHost(navController, viewModel)
    }
}