package com.bliss.blissandroidchallenge.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit,
    shape: RoundedCornerShape = RoundedCornerShape(25),
    @DrawableRes iconRes: Int? = null,
    @StringRes contentDescRes: Int? = null,
) {
    Button(
        modifier = modifier,
        shape = shape,
        onClick = onButtonClick
    ) {
        if (iconRes != null) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = contentDescRes?.let {
                    stringResource(id = it)
                }
            )
        }
    }
}