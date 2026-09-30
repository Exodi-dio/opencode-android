package com.opencode.android.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

// M1 stub: sessions list. Consumes Task 2 SessionDao, which reads an empty
// list in M1, so the empty state is the default render. DAO wiring (Flow +
// ViewModel) lands with full UI work after M1; pass sessions explicitly until
// then to keep this composable previewable and golden-testable.
@Composable
fun SessionsScreen(
  onNewSession: () -> Unit = {},
  sessions: List<String> = emptyList()
) {
  Column(
    modifier = Modifier.fillMaxSize().padding(all = 16.dp),
    verticalArrangement = Arrangement.spacedBy(space = 12.dp)
  ) {
    Text(
      text = "opencode",
      style = MaterialTheme.typography.titleLarge
    )
    if (sessions.isEmpty()) {
      Text(
        text = "No sessions yet",
        style = MaterialTheme.typography.headlineSmall
      )
      Text(
        text = "Sessions you create will appear here.",
        style = MaterialTheme.typography.bodyMedium
      )
      Button(
        onClick = onNewSession,
        modifier = Modifier.semantics { contentDescription = "New session" }
      ) {
        Text(text = "New session")
      }
    } else {
      sessions.forEach { session ->
        Text(text = session)
      }
    }
  }
}
