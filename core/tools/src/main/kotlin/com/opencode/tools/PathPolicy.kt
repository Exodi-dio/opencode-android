package com.opencode.tools

import java.nio.file.Paths

object PathPolicy {
  fun isOutsideRoot(root: String, p: String): Boolean {
    val rootPath = Paths.get(root).normalize()
    val target = Paths.get(p).normalize()
    return !target.startsWith(rootPath)
  }
}
