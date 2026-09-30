// M1 root build: plugin versions resolve from gradle/libs.versions.toml.
// Hilt + ksp are applied here (apply false); Room schema export dir for M1 is
// core/session/schemas, configured via the ksp room.schemaLocation arg in
// :core:session (see core/session/build.gradle.kts).

plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.kotlin.android) apply false
  alias(libs.plugins.kotlin.jvm) apply false
  alias(libs.plugins.hilt) apply false
  alias(libs.plugins.ksp) apply false
}
