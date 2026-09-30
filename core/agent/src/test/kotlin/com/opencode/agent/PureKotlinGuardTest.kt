package com.opencode.agent

import java.io.File
import org.junit.Assert.assertTrue
import org.junit.Test

class PureKotlinGuardTest {

  @Test fun pureKotlinModulesHaveNoAndroidImports() {
    // Gradle runs unit tests with the module dir as working directory, so
    // resolve the repo root (dir containing settings.gradle.kts) first.
    val userDir = File(System.getProperty("user.dir", "."))
    val root = generateSequence(userDir) { it.parentFile }
      .firstOrNull { File(it, "settings.gradle.kts").exists() } ?: userDir
    val roots = listOf("core/agent/src", "core/tools/src", "core/providers/src", "core/config/src")
    // Line-anchored: a real import starts the line. (A substring match would
    // self-flag this very file, which names the forbidden pattern in a string.)
    val hits = roots.flatMap { File(root, it).walk().filter { f -> f.extension == "kt" } }
      .filter { f -> f.readLines().any { line -> line.startsWith("import android.") } }
    assertTrue("android.* imports in pure modules: $hits", hits.isEmpty())
  }
}
