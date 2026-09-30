package com.opencode.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.opencode.android.ui.SessionsScreen
import com.opencode.android.ui.theme.OpencodeTheme

// M1 shell: sessions list is the start destination; full router lands later.
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      OpencodeTheme(darkTheme = true) {
        SessionsScreen()
      }
    }
  }
}
