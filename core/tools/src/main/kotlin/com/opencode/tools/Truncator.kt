package com.opencode.tools

object Truncator {
  fun truncate(b: ByteArray, limit: Int): ByteArray {
    if (b.size <= limit) return b
    val marker = "\n[truncated ${b.size} -> $limit bytes]\n".toByteArray(Charsets.UTF_8)
    return b.copyOf(limit) + marker
  }
}
