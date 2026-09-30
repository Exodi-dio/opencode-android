plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.hilt)
  alias(libs.plugins.ksp)
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

    // NDK ABIs (docs/parity.md "native/SDK pins" row).
    ndk {
      abiFilters += listOf("arm64-v8a", "x86_64")
    }
  }

  packagingOptions {
    jniLibs {
      useLegacyPackaging = true
    }
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
  implementation(libs.hilt.android)
  ksp(libs.hilt.compiler)
  // M1 StartupTest (Robolectric, activity launches).
  testImplementation(libs.junit4)
  testImplementation(libs.robolectric)
}
