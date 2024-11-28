package com.bliss.blissandroidchallenge.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bliss.blissandroidchallenge.presentation.ui.theme.White

@Composable
fun LoaderComponent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(80.dp)
            .height(80.dp)
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = White
        )
    }
}