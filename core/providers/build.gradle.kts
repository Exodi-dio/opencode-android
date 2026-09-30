// Pure-Kotlin module: zero android.* imports (enforced by PureKotlinGuardTest).

plugins {
  alias(libs.plugins.kotlin.jvm)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
  compilerOptions {
    jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
  }
}

dependencies {
  testImplementation(project(":core:tools"))
  testImplementation(project(":core:permission"))
  testImplementation(libs.ktor.server.core)
  testImplementation(libs.ktor.server.netty)
  testImplementation(libs.serialization.json)
  testImplementation(libs.junit4)
}
