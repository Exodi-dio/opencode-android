# Limitations

## Cloud substitutes

- All builds, tests, screenshots, and emulator runs execute in GitHub Codespaces and
  GitHub Actions; no workflow step requires a local computer, physical phone, or local
  emulator.

## Unverifiable perf risks

- Phantom-process behavior under Android background-execution limits is recorded here
  and will be confirmed with cloud-emulator evidence in M3/M6.
- Weak-hardware (2 GB RAM class device) cold-start and ANR budgets are recorded here
  and will be confirmed with cloud-emulator evidence in M3/M6.

## Known constraints

- MCP-stdio subprocess behavior on Android is recorded here and will be confirmed
  with implementation evidence in M3/M6.
- minSdk 26, targetSdk 35, no Google Play Services / Firebase; F-Droid friendly set
  stays fixed unless a parity row approves the bump.
- `:core:permission` is a pure-JVM `kotlin("jvm")` library in M1 (converts back to
  `com.android.library` in M3 for Keystore/FGS bridges); `:core:providers` must keep
  depending only on the pure policy types (`PermissionTable`, `Rule`, `Verdict`).
