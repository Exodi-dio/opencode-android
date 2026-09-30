package com.opencode.android.ui

// M1 nav stub: route strings matching the official information architecture
// (sessions list, chat detail, settings). Pure Kotlin on purpose: no
// navigation-compose dependency in M1, so no new version pin is needed. A
// state-based router and MainActivity wiring land with full UI work after M1.
object NavRoutes {
  const val SESSIONS = "sessions"
  const val CHAT_PATTERN = "chat/{id}"
  const val SETTINGS = "settings"

  fun chat(id: String): String = CHAT_PREFIX + id

  fun sessionIdFrom(route: String?): String? =
    route
      ?.takeIf { it.startsWith(CHAT_PREFIX) }
      ?.removePrefix(CHAT_PREFIX)
      ?.takeIf { it.isNotEmpty() }

  private const val CHAT_PREFIX = "chat/"
}
