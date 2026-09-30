# opencode-android Design Spec

**Date:** 2026-09-30
**Repo:** `Exodi-dio/opencode-android` (public)
**Upstream:** `anomalyco/opencode` (MIT, formerly `sst/opencode`), default branch `dev`. Exact pinned commit recorded in `UPSTREAM.md` at implementation time (M1). Full-port principle: use the official opencode on GitHub as the reference oracle, read ALL of its code before implementing each module, and port every capability and functionality so the app retains full upstream behavior in native Kotlin. Never guess behavior; never ship an intentional subset. Where Android makes exact behavior impossible, attempt a cloud substitute first, then document the gap in `docs/limitations.md` and continue.
**Status:** Conversational design approved (Sections 1-4). Written spec pending user review.

> **For agentic workers:** This spec travels with the implementation plan. The plan argues from this spec. Cloud-only: zero hardware. Everything (writing code, compiling, testing, debugging, profiling, releasing) happens in GitHub Codespaces and GitHub Actions. Never add a step, doc, or instruction that needs a local computer, a physical phone, or a local emulator. Never write "test on a device". If something seems to need hardware, find a cloud substitute; if none exists, document the limitation in `docs/limitations.md` and continue.

## 1. Goal and intent

**Intended outcome:** A NATIVE Android port of opencode that runs the full agent on-device: agent loop, tools, providers, sessions, permissions, and UI. It is NOT a client for `opencode serve`.

**Who it is for:** Android developers and F-Droid users who want an offline-capable, provider-agnostic coding agent in app-private storage with git via JGit and SAF import/export.

**Success criteria:**
- Milestones M1-M7 each end with green CI and a downloadable debug APK artifact from Actions. No time limit: take as much time as needed, favor completeness and correctness over speed, small correct commits.
- Full parity: every upstream capability ported (agent loop, all tools with identical schemas, all agents/modes, permissions, providers, config precedence, sessions, MCP, shell, server subset). `conformance.yml` differential-tests the port against upstream run via Bun as oracle (tool outputs, edit/patch results, permission decisions, config resolution, OpenAPI responses) and reaches 90%+ by M6, 100% by M7.
- No Google Play Services / Firebase. F-Droid friendly (fastlane metadata, SBOM, checksums, signed APK/AAB via Secrets).
- All tests deterministic via mock LLM server (recorded fixtures + scripted tool-call streams, no API keys).
- Every upstream-behavior decision recorded in `docs/parity.md`. Unverifiable-on-emulator perf risks listed in `docs/limitations.md`.

**What was said vs assumptions:**
- Said: Kotlin, Compose + Material 3, coroutines/Flow, kotlinx.serialization, Ktor client, Room, Hilt or Koin, minSdk 26, targetSdk latest, modules as listed, NDK busybox/ripgrep as `lib*.so` with `useLegacyPackaging=true`, 16 KB page alignment, arm64-v8a + x86_64, foreground service with correct FGS type for API 34+, all 7 required workflows, M1-M7, small commits + PR per milestone.
- Assumed and now locked: repo name `opencode-android` under `Exodi-dio`, public. DI = Hilt. Module strategy = A+C combined (full multi-module from M1 with pure-Kotlin discipline). Upstream pin read at M1 start from `dev` branch tip. TargetSdk resolved at M1 to latest stable in Codespaces (no hardcoded version in spec to avoid rot; plan pins exact version).

## 2. Architecture (Approved Section 1)

- Gradle multi-module from M1 (even if some are stubs): `:core:agent`, `:core:tools`, `:core:providers`, `:core:session`, `:core:permission`, `:core:config`, `:core:mcp`, `:core:shell`, `:core:server` (optional embedded Ktor API, off by default), `:app` (UI), `:native` (NDK).
- Pure-Kotlin discipline (C): `:core:agent`, `:core:tools`, `:core:providers`, `:core:config` contain zero `android.*` imports. Only `:core:session` (Room) and `:core:permission` (Keystore / notification / FGS bridge interfaces) touch Android. This lets JVM unit tests and `conformance.yml` run without an emulator.
- Stack: Kotlin, Compose + Material 3, coroutines/Flow, kotlinx.serialization, Ktor client, Room, Hilt. minSdk 26, targetSdk latest stable at build time. No GMS/Firebase. F-Droid friendly.
- Attribution and branding: keep upstream `LICENSE` (MIT) and attribution. App uses official opencode name, UX structure, and UI elements as the reference (sessions, streaming chat, tool cards, diffs, permission prompts, model/agent pickers, commands), reimplemented natively in Compose + Material 3 and optimized for mobile screens. Add README note clarifying this port is not built by the opencode team and is not affiliated, per upstream request for `opencode-*` named projects. Do not claim to be official; do not remove upstream credit.
- Cloud dev: `.devcontainer` with JDK 21, Android cmdline-tools, pinned SDK/NDK, Gradle, Bun (to run upstream as reference only, never embedded). Codespaces prebuilds enabled, 4-core+ machine. Gradle build cache + dependency caching in Actions.

## 3. Components (Approved Section 2)

1. **Agent loop** (`:core:agent`): streaming tool-calling, multi-step, abort via coroutine cancellation, retries with backoff on 429/5xx, context compaction/summarization preserving todos, todo tracking. Modes: `build` (full access, default), `plan` (read-only: denies edits, asks before bash), `general` (subagent for search/multistep), plus custom agents/commands from config.
2. **Tools** (`:core:tools`): `bash`, `read`, `write`, `edit`, `patch`, `grep`, `glob`, `list`, `webfetch`, `todo`, `task` (subagents). Tool JSON schemas identical to upstream. All file/shell tools confined to workspace root; path traversal denied. Large-output truncation enforced.
3. **Permissions** (`:core:permission`): `allow` / `ask` / `deny` with per-tool patterns, matching upstream semantics. Deny-by-default on bypass attempts. In-app approval sheet + notification actions. Keystore failure falls back to denied.
4. **Providers** (`:core:providers`): Anthropic, OpenAI (chat + responses), Google, OpenAI-compatible endpoints (OpenRouter, Ollama / LM Studio over LAN). Model catalog and options as upstream does it. API keys in Android Keystore. OAuth / device flows are a later milestone (M7), not M1-M6.
5. **Config** (`:core:config`): `opencode.json` / `jsonc`, `AGENTS.md`, rules, commands, agents, with upstream precedence rules. Resolution logic differential-tested in conformance.
6. **Sessions** (`:core:session`): create / resume / fork / revert, message parts, diffs, snapshots via JGit, Room storage.
7. **MCP** (`:core:mcp`): remote (HTTP/SSE) first. Local stdio only where binaries can run on Android (constrained; document gaps in `limitations.md`).
8. **Shell** (`:core:shell`): `/system/bin/sh` + toybox plus NDK-built `busybox` and `ripgrep` shipped as `jniLibs` `lib*.so` with `useLegacyPackaging = true` (only reliable exec under W^X on targetSdk 29+). Optional milestone: NDK-built `git` (JGit remains primary).
9. **Server** (`:core:server`, optional, off by default): embedded localhost Ktor API implementing the upstream OpenAPI subset. Used for conformance tests and LAN use.
10. **UI** (`:app`): faithful mobile adaptation of official opencode UX — sessions list, streaming chat with markdown and collapsible tool cards, diff viewer, file browser/editor, permission sheet, model/agent picker, settings, commands palette — same information architecture and element semantics as upstream TUI/console, restyled natively with Material 3 for phones, tablets, and foldables. Mobile-perfect goal: no clipped text, no overlapping sheets, no lost scroll position during streaming, no jank on weak hardware. Means: LazyColumn with stable keys and chunked markdown rendering, cancellable streaming collectors, R8 + baseline-profile, strictMode-clean, TalkBack labels on every interactive card/sheet, adaptive navigation (single-pane phone, two-pane tablet/foldable).
11. **Native** (`:native`): NDK builds for `busybox`, `ripgrep` (M3), later `git` and QuickJS plugin runtime (M7). ABIs `arm64-v8a` + `x86_64`. Linker flag `-Wl,-z,max-page-size=16384` for 16 KB page alignment. Verified in CI with `readelf`.

Out of scope for M1-M6, scheduled M7: plugins/custom tools via embedded QuickJS (documented subset), OAuth/device flows, NDK git, polish, F-Droid release.

## 4. Data flow and Android runtime (Approved Section 3)

- Flow: Compose UI -> `SessionViewModel` -> `AgentLoop` (pure Kotlin, `Flow`) -> Provider streaming (Ktor SSE) -> tool-call JSON -> Permission gate -> Tool executor (sandboxed, workspace-root confined) -> Room appends parts/diffs -> UI re-renders streaming chunks and tool cards.
- Abort: coroutine cancellation propagates to provider stream and tool process (`destroy()` + cleanup). Timeouts enforced. Process cleanup verified in tests.
- Retries: backoff on 429/5xx, abort on 401/403 surfaced as configuration error.
- Compaction: when context exceeds threshold, summarize preserving todos and session intents, matching upstream compaction semantics as read from source.
- Subagents: `task` tool spawns child loop with scoped permissions and isolated todo list, parent sees summary + diffs.
- Workspaces: live in app-private storage (real POSIX paths, needed by shell). Import/export via SAF zip. `git clone` / `pull` / `push` via JGit.
- Long commands and agent runs: run in a foreground service with correct FGS type for API 34+, cancellable, output streaming, timeouts. Design accounts for Android 12+ phantom-process killer (child-process death treated as retryable tool failure, documented in `limitations.md`).
- TalkBack: all interactive cards and sheets expose content descriptions and actions.
- Weak-hardware smoothness (all devices, especially low-end, minSdk 26): cold-start and chat-scroll budgets enforced in CI via JVM benchmarks + constrained-emulator smoke (low-RAM AVD profile, e.g. 2 GB, API 30, x86_64 KVM) with frame-time and memory ceilings; baseline-profile + R8 required for release; streaming UI renders incrementally (no full-list recomposition per token); images/coarse assets avoided in chat path; Room paging for sessions/parts. No real-hardware perf claims — unverifiable risks go in `docs/limitations.md`.

## 5. Error handling, testing, cloud CI (Approved Section 4)

**Error handling (deny-safe):**
- Path traversal outside workspace root: deny + log.
- Permission bypass attempts: deny-by-default, audit event.
- Large output: truncate with `[truncated]` marker + total byte count, identical policy to upstream as read.
- Cancel/timeout: kill process tree, close streams, mark tool part as cancelled.
- Keystore unavailable: deny provider calls requiring keys, surface settings hint.
- Offline / LAN unreachable (Ollama/LM Studio): retryable error with endpoint shown.

**Testing rules (all cloud, no hardware):**
- Mock LLM server (recorded fixtures + scripted tool-call streams) so every test is deterministic and needs no API keys.
- JVM unit tests for agent loop, tool sandboxing, path traversal, permission bypass attempts, large-output truncation, cancellation, process cleanup.
- Robolectric for Android-coupled logic. JVM benchmarks for perf budgets (no hardware performance claims).
- Emulator instrumented tests on `x86_64` emulators (API 30, 34, latest) using KVM-enabled runners and `android-emulator-runner`. Artifacts uploaded: logcat, screenshots, screenrecord mp4, UI hierarchy dumps, tombstones/ANR traces. The `scenarios.yml` workflow_dispatch E2E with mock LLM + video + logs is the cloud debugger.
- Golden screenshots via Roborazzi or Paparazzi covering phone + tablet + foldable + dark/light + large-font; diffs posted as PR comments; zero-tolerance for clipping/overlap/regression before merge.
- `conformance.yml`: pins upstream, runs it via Bun on the runner as oracle, differential-tests this port (tool outputs, edit/patch results, permission decisions, config resolution, OpenAPI responses, plus UI element/UX parity checklist for screens/states). Fails on divergence. Target 90%+ by M6, 100% by M7.
- `upstream-watch.yml` (nightly): detects new upstream releases, diffs OpenAPI spec / tool schemas, opens an issue.
- `baseline-profile` generation in CI on the emulator.
- `release.yml`: signed APK/AAB (keys in GitHub Secrets), checksums, SBOM, GitHub Release, fastlane metadata for F-Droid.

**Required workflows (all in cloud):** `ci.yml` (ktlint, detekt, unit tests, Robolectric, debug APK, NDK matrix both ABIs, ELF 16 KB alignment check with `readelf`), `emulator.yml`, screenshots, `conformance.yml`, `scenarios.yml`, `upstream-watch.yml`, `release.yml`.

**Docs required:** `UPSTREAM.md` (pinned commit + ALL upstream source files read per module including TUI/console/web UI source for UX parity, no module implemented from memory), `docs/parity.md` (every upstream-behavior decision including UI/UX element mapping, zero undocumented divergence), `docs/limitations.md` (cloud-substitute gaps + unverifiable perf risks including real weak-hardware behavior + phantom-process + MCP stdio constraints), F-Droid fastlane metadata by M7.

## 6. Milestones (each ends green CI + downloadable debug APK, each gets its own implementation plan starting with M1)

- **M1** skeleton + CI + devcontainer + mock LLM + UPSTREAM pin + Room/Hilt shell + `:app` nav.
- **M2** provider streaming + agent loop + read/write/edit/grep/glob/list + abort/retries/truncation tests.
- **M3** shell runtime (busybox/ripgrep via NDK, 16 KB check) + bash tool + permissions (sheet + notification actions) + FGS.
- **M4** sessions/storage/compaction/subagents + config parity (precedence, AGENTS.md, commands/agents).
- **M5** full UI, notifications, foreground service, adaptive layout, TalkBack, baseline-profile.
- **M6** MCP (remote first), webfetch, JGit, embedded server (off by default), conformance suite at 90%+ (path to 100%).
- **M7** plugins (QuickJS subset covering all upstream plugin hooks), OAuth, polish, full 100% conformance, F-Droid release (signed AAB/APK, SBOM, checksums, fastlane).

Process: small commits, PR per milestone, `docs/parity.md` updated per decision. Questions to maintainer limited to architecture-level tradeoffs, never hardware.

## 7. Non-goals and risks

- Non-goals: embedding Bun/Node, rooting, GMS/Firebase, local-emulator or on-device manual QA steps.
- Risks: phantom-process killer killing long tools (mitigate: FGS + retryable failure + `limitations.md`); W^X exec constraints (mitigate: `lib*.so` + `useLegacyPackaging`); 16 KB page size on new devices (mitigate: linker flag + `readelf` gate); MCP stdio binaries unavailable (mitigate: remote-first + documented subset); provider API drift (mitigate: `upstream-watch.yml` + conformance oracle).
