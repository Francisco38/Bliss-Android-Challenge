package com.bliss.blissandroidchallenge.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun TextButton(
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit,
    shape: RoundedCornerShape = RoundedCornerShape(25),
    @StringRes textRes: Int? = null
) {
    if (textRes != null) {
        Button(
            modifier = modifier,
            shape = shape,
            onClick = onButtonClick
        ) {
            Text(
                text = stringResource(
                    id = textRes
                )
            )
        }
    }
}