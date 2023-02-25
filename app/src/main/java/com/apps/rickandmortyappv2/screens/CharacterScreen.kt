package com.apps.rickandmortyappv2.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.apps.rickandmortyappv2.api.RickAndMortyViewModel
import com.apps.rickandmortyappv2.ui.theme.*
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun Screen2(
    characterId: Int,
    viewModel: RickAndMortyViewModel,
    onBack: () -> Unit,
) {
    //Compare characterId to the id of the character in the list
    val character = viewModel.getCharacterById(characterId)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(20.dp)
            .clip(RoundedCornerShape(20)),
    ) {
        Column(
            modifier = Modifier
                .background(SecondaryBackground)
                .align(Alignment.Center)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            GlideImage(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(20)),
                imageModel = { character?.image }, // loading a network image or local resource using an URL.
                imageOptions = ImageOptions(
                    requestSize = IntSize(500, 500),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                ),

                )
            Spacer(modifier = Modifier.height(8.dp))
            if (character != null) {
                Text(
                    text = "${character.name}",
                    color = Color.White,
                    style = MaterialTheme.typography.h5
                )
            } else {
                Text(text = "Error", color = Color.Red, style = MaterialTheme.typography.h5)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${character?.species}",
                    color = Color.White,
                    style = MaterialTheme.typography.subtitle2
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = if (character?.status == "Alive") Icons.Filled.CheckCircle else Icons.Filled.Close,
                    contentDescription = "Character status",
                    tint = if (character?.status == "Alive") Alive else Dead,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${character?.status}",
                    color = Color.White,
                    style = MaterialTheme.typography.subtitle2
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.Center) {
                Column {
                    Text(
                        text = "Gender",
                        color = TextGray,
                        style = MaterialTheme.typography.subtitle1
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${character?.gender}",
                        color = Color.White,
                        style = MaterialTheme.typography.subtitle2
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Origin",
                        color = TextGray,
                        style = MaterialTheme.typography.subtitle1
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${character?.origin?.name}",
                        color = Color.White,
                        style = MaterialTheme.typography.subtitle2
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Last Known Location",
                    color = TextGray,
                    style = MaterialTheme.typography.subtitle1
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${character?.location?.name}",
                    color = Color.White,
                    style = MaterialTheme.typography.subtitle2
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onBack) {
                Text(text = "Regresar")
            }
        }
    }
}