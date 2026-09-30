# Parity log

Every upstream-behavior decision is recorded here. Schema:

| Date | Area | Upstream file@SHA | Decision |
| ---- | ---- | ----------------- | -------- |

`Upstream file@SHA` uses the short path plus the 7-char prefix of the pinned SHA
(`2fa3363`) unless a row states otherwise; full pin lives in `UPSTREAM.md`.

| Date | Area | Upstream file@SHA | Decision |
| ---- | ---- | ----------------- | -------- |
| 2026-09-30 | upstream pin | `UPSTREAM.md` records `2fa3363c924c5c3e367b84a87ae478296a0ed59b` (`dev`, `git ls-remote ... HEAD`) | All M1 rows below are read against this SHA; any newer upstream change needs a new dated row |
| 2026-09-30 | toolchain pins | `packages/` layout@2fa3363 (Bun/TS oracle, not embedded) | M1 builds with Kotlin 2.0.21, AGP 8.5.2, Gradle 8.10.2, JDK 21, compileSdk 35, targetSdk 35, minSdk 26; bump only with a new dated row |
| 2026-09-30 | native/SDK pins | n/a (Android-side only) | NDK r27 (`27.2.12479018`), cmdline-tools `11076708`, ABIs `arm64-v8a` + `x86_64`, 16 KB page alignment, `useLegacyPackaging = true` for `lib*.so` |
| 2026-09-30 | library pins | n/a (Android-side only) | Compose BOM `2024.09.00`, Hilt `2.51.1` (ksp), Room `2.6.1`, Ktor client `2.3.12`, kotlinx.serialization `1.7.3`, coroutines `1.9.0`, JGit `6.10.0` (dependency only until M4/M6), JUnit4 + Robolectric `4.13` + Paparazzi `1.3.2` + Roborazzi `1.27.0`, ktlint `12.1.2`, detekt `1.23.7`; failed installs bump to nearest stable with a new dated row |
| 2026-09-30 | module map | `packages/opencode/src/tool/`@2fa3363, `packages/opencode/src/session/`@2fa3363, `packages/tui/src/`@2fa3363, `packages/cli/src/`@2fa3363 | Port as `:core:agent`, `:core:tools`, `:core:providers`, `:core:session` (Room), `:core:permission`, `:core:config`, `:core:mcp`, `:core:shell`, `:core:server`, `:app`, `:native`; `:core:agent,tools,providers,config` stay pure-Kotlin (zero `android.*` imports), full tool/session logic lands after M1 |
| 2026-09-30 | theme | `packages/tui/src/theme/assets/opencode.json`@2fa3363 (blob `e92dca8`, default theme via `packages/tui/src/theme/index.ts`@2fa3363 blob `e8a5f2c`) | Hexes adopted in `Theme.kt`: dark bg `#0A0A0A` / surface `#141414` / text `#EEEEEE` / primary `#FAB283` / secondary `#5C9CF5` / accent `#9D7CD8` / muted `#808080` / error `#E06C75`; light bg `#FFFFFF` / surface `#FAFAFA` / text `#1A1A1A` / primary `#3B7DD8` / secondary `#7B5BB6` / accent `#D68C27` / muted `#8A8A8A` / error `#D1383D`. Approximations: M3 `onPrimary` dark `#1A1A1A` (no upstream slot, dark text on peach for contrast), `surface` maps to upstream `backgroundPanel`, muted maps to M3 `onSurfaceVariant`; dark-first default kept |
| 2026-09-30 | permission-module | n/a (Android-side only) | JVM library for M1 so providers JVM tests resolve; convert back to com.android.library in M3 for Keystore/FGS bridges |
| 2026-09-30 | paparazzi-bump | n/a (Android-side only) | Paparazzi `1.3.2` -> `1.3.5` (newest stable 1.x; layoutlib Jellyfish, Compose 1.7.5, Kotlin 2.0.21, Gradle 8.10.2 support) to fix `IllegalAccessError at ResourceType.java:175` render crash with compileSdk 35 + JDK 21; Roborazzi stays `1.27.0` |
