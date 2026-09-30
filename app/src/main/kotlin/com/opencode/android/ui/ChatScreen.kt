package com.opencode.android.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

// M1 stub: chat detail. Streaming arrives as chunked text (stable index keys
// so inserts never reshuffle rows); scroll position survives recomposition and
// rotation via chatListState. Tool cards and the permission sheet are collapsed
// stubs until the agent loop lands after M1.
@Composable
fun ChatScreen(
  sessionId: String,
  chunks: List<String> = emptyList(),
  onPermissionRequest: () -> Unit = {}
) {
  val chatListState = rememberLazyListState()
  Column(
    modifier = Modifier.fillMaxSize().padding(all = 16.dp),
    verticalArrangement = Arrangement.spacedBy(space = 12.dp)
  ) {
    Text(
      text = sessionId,
      style = MaterialTheme.typography.titleMedium
    )
    if (chunks.isEmpty()) {
      Text(
        text = "No messages yet",
        style = MaterialTheme.typography.bodyMedium
      )
    } else {
      LazyColumn(
        state = chatListState,
        modifier = Modifier.fillMaxWidth().weight(weight = 1f),
        verticalArrangement = Arrangement.spacedBy(space = 8.dp)
      ) {
        items(count = chunks.size, key = { index -> "chunk-$index" }) { index ->
          Text(text = chunks[index])
        }
      }
    }
    OutlinedCard(
      modifier = Modifier.fillMaxWidth().semantics { contentDescription = "Tool card" }
    ) {
      Text(
        text = "No tool calls",
        modifier = Modifier.padding(all = 12.dp)
      )
    }
    Button(
      onClick = onPermissionRequest,
      modifier = Modifier.semantics { contentDescription = "Show permission sheet" }
    ) {
      Text(text = "Permissions")
    }
  }
}
