package com.bliss.blissandroidchallenge.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Gray40,
    background = Blue80,
    surface = Blue80,
    onBackground = White,
    onSurface = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Gray40,
    background = Blue80,
    surface = Blue80,
    onBackground = White,
    onSurface = Pink80
)

@Composable
fun BlissAndroidChallengeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}