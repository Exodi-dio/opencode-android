package com.opencode.permission

enum class Verdict {
  ALLOW,
  DENY,
}

data class Rule(
  val pattern: String,
  val verdict: Verdict,
)

class PermissionTable(
  private val rules: List<Rule>,
) {
  fun decide(tool: String): Verdict {
    var allowMatch = false
    for (rule in rules) {
      if (matches(rule.pattern, tool)) {
        if (rule.verdict == Verdict.DENY) return Verdict.DENY
        allowMatch = true
      }
    }
    return if (allowMatch) Verdict.ALLOW else Verdict.DENY
  }

  private fun matches(pattern: String, tool: String): Boolean {
    if (pattern.endsWith("*")) {
      return tool.startsWith(pattern.dropLast(1))
    }
    return tool == pattern
  }
}
