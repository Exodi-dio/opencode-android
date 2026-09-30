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

// M1 stub: settings. Provider/model/agent pickers are stub dropdowns (plain
// buttons with picker TalkBack labels) until config wiring lands after M1.
@Composable
fun SettingsScreen(
  onProviderClick: () -> Unit = {},
  onModelClick: () -> Unit = {},
  onAgentClick: () -> Unit = {}
) {
  Column(
    modifier = Modifier.fillMaxSize().padding(all = 16.dp),
    verticalArrangement = Arrangement.spacedBy(space = 12.dp)
  ) {
    Text(
      text = "Settings",
      style = MaterialTheme.typography.titleLarge
    )
    SettingStub(
      label = "Provider",
      description = "Provider picker",
      onClick = onProviderClick
    )
    SettingStub(
      label = "Model",
      description = "Model picker",
      onClick = onModelClick
    )
    SettingStub(
      label = "Agent",
      description = "Agent picker",
      onClick = onAgentClick
    )
  }
}

@Composable
private fun SettingStub(
  label: String,
  description: String,
  onClick: () -> Unit
) {
  Button(
    onClick = onClick,
    modifier = Modifier.semantics { contentDescription = description }
  ) {
    Text(text = label)
  }
}
