package com.apps.rickandmortyappv2.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.apps.rickandmortyappv2.api.RickAndMortyViewModel
import com.apps.rickandmortyappv2.ui.theme.Background
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@Composable
fun Pantalla1(
    viewModel: RickAndMortyViewModel,
    navegarPantalla2: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    val currentPage = viewModel.currentPage
    val maxPages = viewModel.maxPages
    val listState = rememberLazyGridState()
    val fabVisibility by derivedStateOf {
        listState.firstVisibleItemIndex != 0
    }
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = modifier.fillMaxSize()) {
        LazyVerticalGrid(
            state = listState,
            // set number of items in a row
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.95f)
                .background(Background)
                .align(Alignment.TopCenter)
        ) {
            items(viewModel.state.value.size) { index ->
                val character = viewModel.state.value[index]
                RowItem(character, modifier = Modifier.clickable {
                    Log.d("Pantalla1", "characterId: ${character.id}")
                    navegarPantalla2(character.id.toString())
                })
            }
        }
        Row(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.05f)
                .background(Background)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = {
                viewModel.loadPreviousPage()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Previous page",
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Página ${currentPage.value} de ${maxPages.value}", color = Color.White)
            Spacer(modifier = Modifier.width(10.dp))
            IconButton(onClick = {
                viewModel.loadNextPage()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = "Next page",
                    tint = Color.White
                )
            }
        }
        AddPaymentFab(
            modifier = Modifier
                .padding(bottom = 50.dp, end = 20.dp)
                .align(Alignment.BottomEnd),
            isVisibleBecauseOfScrolling = fabVisibility
        ) {
            coroutineScope.launch {
                // Animate scroll to the first item
                listState.animateScrollToItem(index = 0)
            }
        }
    }
}

@Composable
private fun AddPaymentFab(
    modifier: Modifier,
    isVisibleBecauseOfScrolling: Boolean,
    onClick: () -> Unit = { }
) {
    val density = LocalDensity.current
    AnimatedVisibility(
        modifier = modifier,
        visible = isVisibleBecauseOfScrolling,
        enter = slideInVertically {
            with(density) { 40.dp.roundToPx() }
        } + fadeIn(),
        exit = fadeOut(
            animationSpec = keyframes {
                this.durationMillis = 120
            }
        )
    ) {
        FloatingActionButton(onClick = { onClick() }) {
            Icon(Icons.Filled.KeyboardArrowUp, "Back to top button", tint = Color.White)
        }
    }
}