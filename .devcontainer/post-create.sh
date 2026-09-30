#!/usr/bin/env bash
# Cloud-only Codespaces setup: pinned Android SDK/NDK + Gradle wrapper + Bun oracle.
# Runs as devcontainer `postCreateCommand` (cwd = workspace root). No local machine needed.
set -euo pipefail

cd "$(dirname "${BASH_SOURCE[0]}")/.."

CMDLINE_TOOLS=11076708
GRADLE_VERSION=8.10.2

export ANDROID_HOME="${ANDROID_HOME:-$HOME/Android/Sdk}"
export ANDROID_SDK_ROOT="$ANDROID_HOME"
mkdir -p "$ANDROID_HOME/cmdline-tools"

if ! command -v unzip >/dev/null 2>&1; then
  sudo apt-get update && sudo apt-get install -y unzip
fi

cd /tmp
curl -fsSLO "https://dl.google.com/android/repository/commandlinetools-linux-${CMDLINE_TOOLS}_latest.zip"
unzip -q -o "commandlinetools-linux-${CMDLINE_TOOLS}_latest.zip" -d "$ANDROID_HOME/cmdline-tools"
rm -rf "$ANDROID_HOME/cmdline-tools/latest"
mv "$ANDROID_HOME/cmdline-tools/cmdline-tools" "$ANDROID_HOME/cmdline-tools/latest"
rm "commandlinetools-linux-${CMDLINE_TOOLS}_latest.zip"
cd - >/dev/null

export PATH="$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools:$PATH"
yes | sdkmanager --licenses >/dev/null
sdkmanager "platform-tools" "platforms;android-35" "build-tools;35.0.0" "ndk;27.2.12479018"

# Task 2 shipped wrapper properties only (no gradlew scripts); generate them here (Gradle 8.10.2 feature).
gradle wrapper --gradle-version "$GRADLE_VERSION"

# Bun latest: upstream TS oracle only (reference), never embedded.
curl -fsSL https://bun.sh/install | bash

# codespacesReady() smoke check: succeeds only if SDK install + wrapper generation worked.
./gradlew --version
