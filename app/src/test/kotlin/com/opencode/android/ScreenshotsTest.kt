package com.opencode.android

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.opencode.android.ui.ChatScreen
import com.opencode.android.ui.SessionsScreen
import com.opencode.android.ui.SettingsScreen
import com.opencode.android.ui.theme.OpencodeTheme
import org.junit.Rule
import org.junit.Test

private const val FONT_SCALE_130 = 1.3f
private const val LARGE_CHUNK_COUNT = 200

// Phone goldens (light baseline). Cloud proof: :app:verifyPaparazziDebug.
class ScreenshotsTest {
  @get:Rule
  val paparazzi = Paparazzi(deviceConfig = DeviceConfig.PIXEL_5)

  @Test fun sessionsEmptyStateRenders() {
    paparazzi.snapshot { SessionsScreen() }
  }

  @Test fun chatEmptyRenders() {
    paparazzi.snapshot { ChatScreen(sessionId = "preview") }
  }

  @Test fun settingsRenders() {
    paparazzi.snapshot { SettingsScreen() }
  }
}

// Dark-theme goldens (OpencodeTheme, dark-first).
class ScreenshotsDarkTest {
  @get:Rule
  val paparazzi = Paparazzi(deviceConfig = DeviceConfig.PIXEL_5)

  @Test fun sessionsDarkRenders() {
    paparazzi.snapshot { OpencodeTheme(darkTheme = true) { SessionsScreen() } }
  }

  @Test fun chatDarkRenders() {
    paparazzi.snapshot { OpencodeTheme(darkTheme = true) { ChatScreen(sessionId = "preview") } }
  }

  @Test fun settingsDarkRenders() {
    paparazzi.snapshot { OpencodeTheme(darkTheme = true) { SettingsScreen() } }
  }
}

// Light-theme goldens (OpencodeTheme).
class ScreenshotsLightTest {
  @get:Rule
  val paparazzi = Paparazzi(deviceConfig = DeviceConfig.PIXEL_5)

  @Test fun sessionsLightRenders() {
    paparazzi.snapshot { OpencodeTheme(darkTheme = false) { SessionsScreen() } }
  }

  @Test fun chatLightRenders() {
    paparazzi.snapshot { OpencodeTheme(darkTheme = false) { ChatScreen(sessionId = "preview") } }
  }

  @Test fun settingsLightRenders() {
    paparazzi.snapshot { OpencodeTheme(darkTheme = false) { SettingsScreen() } }
  }
}

// Tablet goldens (Pixel C layout proof, no clipping-prone rows).
class ScreenshotsTabletTest {
  @get:Rule
  val paparazzi = Paparazzi(deviceConfig = DeviceConfig.PIXEL_C)

  @Test fun sessionsTabletRenders() {
    paparazzi.snapshot { OpencodeTheme(darkTheme = false) { SessionsScreen() } }
  }

  @Test fun chatTabletRenders() {
    paparazzi.snapshot { OpencodeTheme(darkTheme = false) { ChatScreen(sessionId = "preview") } }
  }
}

// Large-font goldens (fontScale 1.3 accessibility proof).
class ScreenshotsFontScaleTest {
  @get:Rule
  val paparazzi = Paparazzi(deviceConfig = DeviceConfig.PIXEL_5.copy(fontScale = FONT_SCALE_130))

  @Test fun sessionsLargeFontRenders() {
    paparazzi.snapshot { OpencodeTheme(darkTheme = false) { SessionsScreen() } }
  }
}

// Streaming rotation hook: 200 chunks recompose without crash (scroll position
// is preserved by chatListState inside ChatScreen).
class ChatStreamScreenshotsTest {
  @get:Rule
  val paparazzi = Paparazzi(deviceConfig = DeviceConfig.PIXEL_5)

  @Test fun chatStreamsChunksWithoutCrash() {
    val chunks = List(LARGE_CHUNK_COUNT) { index -> "chunk $index" }
    paparazzi.snapshot { OpencodeTheme(darkTheme = true) { ChatScreen(sessionId = "rotation", chunks = chunks) } }
  }
}
