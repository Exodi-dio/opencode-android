# Cloud dev environment (Codespaces-only)

Thin setup on `mcr.microsoft.com/devcontainers/java:1-21-bookworm` (JDK 21).
Everything here runs in the cloud; no local computer, physical phone, or local emulator is ever needed.

## What `postCreateCommand` does (`post-create.sh`)

1. Installs Android cmdline-tools `11076708`, then via `sdkmanager`:
   `platform-tools`, `platforms;android-35`, `build-tools;35.0.0`, `ndk;27.2.12479018`.
2. Runs `gradle wrapper --gradle-version 8.10.2` (Task 2 shipped wrapper properties only;
   this generates the missing `gradlew` scripts so later `./gradlew` steps work).
3. Installs Bun latest (upstream TypeScript oracle only, for reference — never embedded).
4. Smoke check: `./gradlew --version` must succeed (`codespacesReady()` invariant).

Pins mirror `gradle/libs.versions.toml` + `docs/parity.md` toolchain rows
(JDK 21, Gradle 8.10.2, NDK `27.2.12479018`, cmdline-tools `11076708`, SDK 35).

## Codespaces prebuilds

Prebuilds are not file-configurable (no supported `.github/codespaces/prebuilds.yml`
schema), so enable them in the cloud UI once per branch:

1. GitHub repo → Settings → Codespaces → Prebuilds → New prebuild.
2. Branch: `m1-skeleton` (later `main`); devcontainer: `.devcontainer/devcontainer.json`.
3. Machine: 4-core minimum (`hostRequirements.cpus: 4` in `devcontainer.json`).
4. Trigger on push; template repos inherit the configuration.

## Verify (cloud runner)

```bash
python3 -c "import json;json.load(open('.devcontainer/devcontainer.json'));print('VALID')"
./gradlew --version
```
