// M1 native stub: com.android.library with a CMake build only (no Kotlin).

plugins {
  alias(libs.plugins.android.library)
}

android {
  namespace = "com.opencode.nativelib"
  compileSdk = libs.versions.compileSdk.get().toInt()

  defaultConfig {
    minSdk = libs.versions.minSdk.get().toInt()

    // NDK ABIs (docs/parity.md "native/SDK pins" row).
    ndk {
      abiFilters += listOf("arm64-v8a", "x86_64")
    }

    externalNativeBuild {
      cmake {
        // 16 KB page alignment linker flag.
        cppFlags("-Wl,-z,max-page-size=16384")
      }
    }
  }

  externalNativeBuild {
    cmake {
      path = file("src/main/cpp/CMakeLists.txt")
    }
  }
}
