package com.opencode.providers

import com.opencode.permission.PermissionTable
import com.opencode.permission.Rule
import com.opencode.permission.Verdict
import com.opencode.tools.PathPolicy
import com.opencode.tools.Truncator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MockLlmDeterminismTest {
  @Test fun replayIsByteIdentical() {
    val s = MockLlmServer()
    val p = s.start()
    assertTrue(p > 0)
    val a = s.replay("chat_stream_001")
    val b = s.replay("chat_stream_001")
    s.stop()
    assertEquals(a, b)
    assertTrue(a.any { it.contains("tool_call") || it.contains("delta") })
  }

  @Test fun traversalDenied() {
    assertTrue(PathPolicy.isOutsideRoot("/ws", "/ws/../secrets/key"))
  }

  @Test fun denyBeatsAllow() {
    val table = PermissionTable(listOf(Rule("bash:*", Verdict.DENY), Rule("bash:*", Verdict.ALLOW)))
    assertEquals(Verdict.DENY, table.decide("bash:ls"))
  }

  @Test fun tenMbTruncates() {
    val out = Truncator.truncate(ByteArray(10 * 1024 * 1024), 64 * 1024)
    assertTrue(out.size <= 66 * 1024 && String(out).contains("[truncated]"))
  }
}
