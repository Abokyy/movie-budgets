package hu.benefanlabs.moviebudgets.android.styling

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColors = darkColors(
    primary = colorPrimary,
    secondary = colorSecondary,
    onPrimary = Color.White
)

private val LightColors = lightColors(
    primary = colorSecondary,
    secondary = colorPrimary,
    onSecondary = Color.White,
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColors else LightColors,
        content = content
    )
}