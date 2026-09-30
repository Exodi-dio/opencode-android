package com.opencode.android.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// M1 stub palette: dark-first, terminal-informed. No upstream console-theme
// parity row exists in docs/parity.md, so these are local M1 values (accent
// green on near-black, light-on-dark contrast above 7:1). A later task can pin
// upstream hexes with a dated parity row without touching call sites.
private const val DARK_BACKGROUND = 0xFF09090B
private const val DARK_SURFACE = 0xFF131316
private const val DARK_ON_SURFACE = 0xFFE4E4E7
private const val DARK_MUTED = 0xFFA1A1AA
private const val DARK_ERROR = 0xFFF87171
private const val ACCENT_GREEN = 0xFF4ADE80
private const val ON_ACCENT_GREEN = 0xFF052E16
private const val LIGHT_BACKGROUND = 0xFFFAFAF9
private const val LIGHT_SURFACE = 0xFFFFFFFF
private const val LIGHT_ON_SURFACE = 0xFF09090B
private const val LIGHT_MUTED = 0xFF52525B
private const val LIGHT_PRIMARY = 0xFF15803D
private const val LIGHT_ERROR = 0xFFB3261E

private val DarkColors = darkColorScheme(
  primary = Color(ACCENT_GREEN),
  onPrimary = Color(ON_ACCENT_GREEN),
  secondary = Color(DARK_MUTED),
  background = Color(DARK_BACKGROUND),
  onBackground = Color(DARK_ON_SURFACE),
  surface = Color(DARK_SURFACE),
  onSurface = Color(DARK_ON_SURFACE),
  error = Color(DARK_ERROR)
)

private val LightColors = lightColorScheme(
  primary = Color(LIGHT_PRIMARY),
  onPrimary = Color(LIGHT_SURFACE),
  secondary = Color(LIGHT_MUTED),
  background = Color(LIGHT_BACKGROUND),
  onBackground = Color(LIGHT_ON_SURFACE),
  surface = Color(LIGHT_SURFACE),
  onSurface = Color(LIGHT_ON_SURFACE),
  error = Color(LIGHT_ERROR)
)

@Composable
fun OpencodeTheme(
  darkTheme: Boolean = true,
  content: @Composable () -> Unit
) {
  MaterialTheme(
    colorScheme = if (darkTheme) DarkColors else LightColors,
    content = content
  )
}
