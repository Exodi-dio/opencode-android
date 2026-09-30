plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  // Task 7 official-UX shell (Compose UI).
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.hilt)
  alias(libs.plugins.ksp)
  // Task 7 screenshot gates (screenshots.yml: verifyPaparazziDebug + compareRoborazziDebug).
  alias(libs.plugins.paparazzi)
  alias(libs.plugins.roborazzi)
}

android {
  namespace = "com.opencode.android"
  compileSdk = libs.versions.compileSdk.get().toInt()
  ndkVersion = libs.versions.ndk.get()

  defaultConfig {
    applicationId = "com.opencode.android"
    minSdk = libs.versions.minSdk.get().toInt()
    targetSdk = libs.versions.targetSdk.get().toInt()
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    // NDK ABIs (docs/parity.md "native/SDK pins" row).
    ndk {
      abiFilters += listOf("arm64-v8a", "x86_64")
    }
  }

  packaging {
    jniLibs {
      useLegacyPackaging = true
    }
  }

  // Task 7 official-UX shell (Compose UI).
  buildFeatures {
    compose = true
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
  }
  kotlinOptions {
    jvmTarget = "21"
  }
  // Robolectric (StartupTest) needs the merged manifest/resources.
  testOptions {
    unitTests.isIncludeAndroidResources = true
  }
}

dependencies {
  // Task 7 official-UX shell (versions resolve from the Compose BOM pin).
  implementation(platform(libs.compose.bom))
  implementation(libs.compose.ui)
  implementation(libs.compose.foundation)
  implementation(libs.compose.material3)
  implementation(libs.hilt.android)
  ksp(libs.hilt.compiler)
  // M1 StartupTest (Robolectric, activity launches).
  testImplementation(libs.junit4)
  testImplementation(libs.robolectric)
  // Task 7 Paparazzi goldens (screenshots workflow verify step).
  testImplementation(libs.paparazzi)
  // M1 LaunchTest (instrumented, emulator/scenarios workflows).
  androidTestImplementation(libs.androidx.test.ext.junit)
  androidTestImplementation(libs.test.runner)
  androidTestImplementation(libs.espresso.core)
}
