// M1 root build: plugin versions resolve from gradle/libs.versions.toml.
// Hilt + ksp are applied here (apply false); Room schema export dir for M1 is
// core/session/schemas, configured via the ksp room.schemaLocation arg in
// :core:session (see core/session/build.gradle.kts).

plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.kotlin.android) apply false
  alias(libs.plugins.kotlin.jvm) apply false
  // Task 7 official-UX shell: Compose compiler tracks the Kotlin pin.
  alias(libs.plugins.compose.compiler) apply false
  alias(libs.plugins.hilt) apply false
  alias(libs.plugins.ksp) apply false
  // Task 7 screenshot gates: applied in :app so the bare task names resolve.
  alias(libs.plugins.paparazzi) apply false
  alias(libs.plugins.roborazzi) apply false
  // M1 lint gate (CI `lint` job runs `./gradlew ktlintCheck detekt`): applied to
  // the root project here so the bare task names resolve at the root too.
  alias(libs.plugins.ktlint)
  alias(libs.plugins.detekt)
}

// M1 lint wiring: ktlint + detekt run on every module. Declared (apply false)
// above is not enough — each project must apply the plugin for its
// `ktlintCheck`/`detekt` tasks to exist under bare-name invocation.
subprojects {
  apply(plugin = "org.jlleitschuh.gradle.ktlint")
  apply(plugin = "io.gitlab.arturbosch.detekt")

  // M1 `unit`-job shim: bare `./gradlew testDebugUnitTest` fails on any project
  // without that task (pure-Kotlin modules only have `test`). Alias it so the
  // brief's exact command stays green on every module.
  afterEvaluate {
    val isAndroid = plugins.hasPlugin("com.android.application") || plugins.hasPlugin("com.android.library")
    if (!isAndroid && tasks.findByName("testDebugUnitTest") == null) {
      tasks.register("testDebugUnitTest") {
        tasks.findByName("test")?.let { dependsOn(it) }
        description = "M1 shim: alias JVM 'test' so root 'testDebugUnitTest' covers pure-Kotlin modules."
      }
    }
  }
}

// Root aggregate placeholder so bare `./gradlew testDebugUnitTest` also resolves
// in the root project; Gradle name-matching runs each subproject's own task.
tasks.register("testDebugUnitTest") {
  description = "M1 aggregate: runs testDebugUnitTest in every module (pure-Kotlin modules via shim)."
}
