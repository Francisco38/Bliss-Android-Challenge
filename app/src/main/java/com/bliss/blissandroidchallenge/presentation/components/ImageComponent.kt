package com.bliss.blissandroidchallenge.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage

@Composable
fun ImageComponent(
    modifier: Modifier,
    imageURL: String,
    contentDescription: String
) {
    AsyncImage(
        model = imageURL,
        contentDescription = contentDescription,
        modifier = modifier
    )
}