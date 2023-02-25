package com.apps.rickandmortyappv2.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.apps.rickandmortyappv2.api.Character
import com.apps.rickandmortyappv2.ui.theme.Background
import com.apps.rickandmortyappv2.ui.theme.SecondaryBackground
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.crossfade.CrossfadePlugin
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin

@Composable
fun RowItem(item: Character, modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .padding(5.dp)
            .clip(RoundedCornerShape(10))
            .background(SecondaryBackground)
            .border(1.dp, SecondaryBackground, RoundedCornerShape(10)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        GlideImage(
            imageModel = { item.image }, // loading a network image or local resource using an URL.
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                requestSize = IntSize(150, 150)
            ),
            component = rememberImageComponent {
                // shows a shimmering effect when loading an image.
                +ShimmerPlugin(
                    baseColor = Background,
                    highlightColor = Color.White
                )
                +CrossfadePlugin(
                    duration = 550
                )
            },
            failure = {
                Text(text = "image request failed.")
            },
            modifier = Modifier
                .padding(top = 8.dp)
                .background(SecondaryBackground)
                .clip(RoundedCornerShape(15))
                .border(1.dp, SecondaryBackground, RoundedCornerShape(15))
                .size(150.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = item.name, color = Color.White, textAlign = TextAlign.Center, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Spacer(modifier = Modifier.height(10.dp))
    }
}