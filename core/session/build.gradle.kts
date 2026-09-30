plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.ksp)
}

android {
  namespace = "com.opencode.session"
  compileSdk = libs.versions.compileSdk.get().toInt()

  defaultConfig {
    minSdk = libs.versions.minSdk.get().toInt()
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
  }
  kotlinOptions {
    jvmTarget = "21"
  }
  testOptions {
    unitTests.isIncludeAndroidResources = true
  }
}

dependencies {
  implementation(libs.room.runtime)
  implementation(libs.room.ktx)
  ksp(libs.room.compiler)
  testImplementation(libs.junit4)
  testImplementation(libs.robolectric)
}

ksp {
  // M1 Room schema export dir.
  arg("room.schemaLocation", rootProject.file("core/session/schemas").path)
}
