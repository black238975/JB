package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val DarkColorScheme = darkColorScheme(
    primary = IndigoPrimaryDark,
    onPrimary = IndigoOnPrimaryDark,
    primaryContainer = IndigoPrimaryContainerDark,
    onPrimaryContainer = IndigoOnPrimaryContainerDark,
    secondary = AmberSecondaryDark,
    onSecondary = AmberOnSecondaryDark,
    secondaryContainer = AmberSecondaryContainerDark,
    onSecondaryContainer = AmberOnSecondaryContainerDark,
    tertiary = EmeraldTertiaryDark,
    onTertiary = EmeraldOnTertiaryDark,
    tertiaryContainer = EmeraldTertiaryContainerDark,
    onTertiaryContainer = EmeraldOnTertiaryContainerDark,
    background = BackgroundDark,
    onBackground = OnSurfaceDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = OnSurfaceVariantDark,
    outline = OutlineDark
)

private val LightColorScheme = lightColorScheme(
    primary = IndigoPrimaryLight,
    onPrimary = IndigoOnPrimaryLight,
    primaryContainer = IndigoPrimaryContainerLight,
    onPrimaryContainer = IndigoOnPrimaryContainerLight,
    secondary = AmberSecondaryLight,
    onSecondary = AmberOnSecondaryLight,
    secondaryContainer = AmberSecondaryContainerLight,
    onSecondaryContainer = AmberOnSecondaryContainerLight,
    tertiary = EmeraldTertiaryLight,
    onTertiary = EmeraldOnTertiaryLight,
    tertiaryContainer = EmeraldTertiaryContainerLight,
    onTertiaryContainer = EmeraldOnTertiaryContainerLight,
    background = BackgroundLight,
    onBackground = OnSurfaceLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = OnSurfaceVariantLight,
    outline = OutlineLight
)

val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(22.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = AppShapes,
        content = content
    )
}
