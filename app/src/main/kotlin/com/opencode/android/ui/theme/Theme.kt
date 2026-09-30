package com.opencode.android.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Upstream-parity palette: default TUI theme
// packages/tui/src/theme/assets/opencode.json@2fa3363 (blob e92dca8),
// resolved via defs (see docs/parity.md theme row, 2026-09-30). Dark-first
// default; M3 onPrimary/tertiary are approximations with no upstream slot.
private const val DARK_BACKGROUND = 0xFF0A0A0A
private const val DARK_SURFACE = 0xFF141414
private const val DARK_ON_SURFACE = 0xFFEEEEEE
private const val DARK_MUTED = 0xFF808080
private const val DARK_PRIMARY = 0xFFFAB283
private const val DARK_ON_PRIMARY = 0xFF1A1A1A
private const val DARK_SECONDARY = 0xFF5C9CF5
private const val DARK_ACCENT = 0xFF9D7CD8
private const val DARK_ERROR = 0xFFE06C75
private const val LIGHT_BACKGROUND = 0xFFFFFFFF
private const val LIGHT_SURFACE = 0xFFFAFAFA
private const val LIGHT_ON_SURFACE = 0xFF1A1A1A
private const val LIGHT_MUTED = 0xFF8A8A8A
private const val LIGHT_PRIMARY = 0xFF3B7DD8
private const val LIGHT_ON_PRIMARY = 0xFFFFFFFF
private const val LIGHT_SECONDARY = 0xFF7B5BB6
private const val LIGHT_ACCENT = 0xFFD68C27
private const val LIGHT_ERROR = 0xFFD1383D

private val DarkColors = darkColorScheme(
  primary = Color(DARK_PRIMARY),
  onPrimary = Color(DARK_ON_PRIMARY),
  secondary = Color(DARK_SECONDARY),
  tertiary = Color(DARK_ACCENT),
  background = Color(DARK_BACKGROUND),
  onBackground = Color(DARK_ON_SURFACE),
  surface = Color(DARK_SURFACE),
  onSurface = Color(DARK_ON_SURFACE),
  onSurfaceVariant = Color(DARK_MUTED),
  error = Color(DARK_ERROR)
)

private val LightColors = lightColorScheme(
  primary = Color(LIGHT_PRIMARY),
  onPrimary = Color(LIGHT_ON_PRIMARY),
  secondary = Color(LIGHT_SECONDARY),
  tertiary = Color(LIGHT_ACCENT),
  background = Color(LIGHT_BACKGROUND),
  onBackground = Color(LIGHT_ON_SURFACE),
  surface = Color(LIGHT_SURFACE),
  onSurface = Color(LIGHT_ON_SURFACE),
  onSurfaceVariant = Color(LIGHT_MUTED),
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
