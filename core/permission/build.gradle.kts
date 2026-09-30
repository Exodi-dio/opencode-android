// Pure-Kotlin module: zero android.* imports (enforced by PureKotlinGuardTest).
// M1: JVM library so :core:providers JVM tests resolve (Android variants
// declare androidJvm, not jvm). Converts back to an Android library in M3
// for Keystore/FGS bridges (see docs/parity.md permission-module row).

plugins {
  alias(libs.plugins.kotlin.jvm)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
  compilerOptions {
    jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
  }
}

dependencies {
  testImplementation(libs.junit4)
}
