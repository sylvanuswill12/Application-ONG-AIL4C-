package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = ForestGreenPrimary,
    onPrimary = Color.White,
    primaryContainer = ForestGreenContainer,
    onPrimaryContainer = OnForestGreenContainer,
    secondary = AccentOrange,
    onSecondary = Color.White,
    secondaryContainer = AccentOrangeContainer,
    onSecondaryContainer = OnAccentOrangeContainer,
    tertiary = EarthGold,
    onTertiary = Color.White,
    tertiaryContainer = EarthGoldContainer,
    onTertiaryContainer = Color(0xFF451A03),
    background = EcoBackgroundLight,
    onBackground = TextPrimary,
    surface = EcoSurfaceLight,
    onSurface = TextPrimary,
    surfaceVariant = EcoSurfaceVariant,
    onSurfaceVariant = TextSecondary,
    outline = Color(0xFFCBD5E1),
    outlineVariant = BorderSubtle
)

private val DarkColorScheme = darkColorScheme(
    primary = ForestGreenDarkTheme,
    onPrimary = Color(0xFF003914),
    primaryContainer = ForestGreenContainerDark,
    onPrimaryContainer = Color(0xFF86EFAC),
    secondary = AccentOrangeDarkTheme,
    onSecondary = Color(0xFF4A1800),
    secondaryContainer = AccentOrangeContainerDark,
    onSecondaryContainer = Color(0xFFFFDBC9),
    tertiary = Color(0xFFFBBF24),
    onTertiary = Color(0xFF451A00),
    tertiaryContainer = Color(0xFF78350F),
    onTertiaryContainer = Color(0xFFFEF3C7),
    background = EcoBackgroundDark,
    onBackground = TextPrimaryDark,
    surface = EcoSurfaceDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = EcoSurfaceVariantDark,
    onSurfaceVariant = TextSecondaryDark,
    outline = Color(0xFF475569),
    outlineVariant = Color(0xFF334155)
)

@Composable
fun Ail4cTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val activity = (view.context as? Activity)
                ?: (generateSequence(view.context) { if (it is android.content.ContextWrapper) it.baseContext else null }
                    .filterIsInstance<Activity>()
                    .firstOrNull())
            activity?.window?.let { window ->
                window.statusBarColor = colorScheme.surface.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
