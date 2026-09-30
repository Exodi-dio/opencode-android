pluginManagement {
  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
  }
}

dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
  }
}

rootProject.name = "opencode-android"

include(
  ":core:agent",
  ":core:tools",
  ":core:providers",
  ":core:session",
  ":core:permission",
  ":core:config",
  ":core:mcp",
  ":core:shell",
  ":core:server",
  ":app",
  ":native",
)
