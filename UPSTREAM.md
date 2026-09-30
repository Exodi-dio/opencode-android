# Upstream pin (M1)

- Upstream repo: `https://github.com/anomalyco/opencode`
- Pin source branch: `dev` (repository default branch; `HEAD` resolves to the same commit)
- Pinned SHA: `2fa3363c924c5c3e367b84a87ae478296a0ed59b`
- Pin date (UTC): `2026-09-30`
- Pin command (cloud runner, no local clone):
  `git ls-remote https://github.com/anomalyco/opencode HEAD | awk '{print $1}'`
- Cross-check: `git ls-remote https://github.com/anomalyco/opencode dev` returned the
  same SHA (`2fa3363c924c5c3e367b84a87ae478296a0ed59b` on `refs/heads/dev`), and
  `gh api repos/anomalyco/opencode` reports `default_branch: dev`.
- Upstream LICENSE source: `LICENSE` at the pinned SHA (blob
  `6439474beed8e0271df9862eff97ffd70ec2464c`, 1065 bytes), copied verbatim into `/LICENSE`.

## M1 file list read (at pinned SHA)

Listings recorded read-only via `gh api repos/anomalyco/opencode/contents/<path>?ref=<SHA>`
(no upstream clone).

- `README.md` (blob `b5a4c8ddd9794e8c20bd287603bfa0c336522796`, 5402 bytes; root also holds
  localized `README.*.md` translations plus `AGENTS.md`, `CONTEXT.md`, `CONTRIBUTING.md`,
  `SECURITY.md`, `STATS.md`)
- `LICENSE` (see pin entry above)
- `packages/opencode/src/tool/` index: `apply_patch.ts`, `code-mode.ts`, `edit.ts`,
  `external-directory.ts`, `glob.ts`, `grep.ts`, `invalid.ts`, `json-schema.ts`, `lsp.ts`,
  `mcp-websearch.ts`, `plan.ts`, `question.ts`, `read.ts`, `registry.ts`, `schema.ts`,
  `shell.ts`, `shell/` (dir), `skill.ts`, `task.ts`, `todo.ts`, `tool.ts`, `truncate.ts`,
  `truncation-dir.ts`, `webfetch.ts`, `websearch.ts`, `write.ts` (plus co-located prompt
  `*.txt` files: `apply_patch.txt`, `edit.txt`, `glob.txt`, `grep.txt`, `lsp.txt`,
  `plan-enter.txt`, `plan-exit.txt`, `question.txt`, `read.txt`, `skill.txt`, `task.txt`,
  `todowrite.txt`, `webfetch.txt`, `websearch.txt`, `write.txt`)
- `packages/opencode/src/session/` index: `compaction.ts`, `instruction.ts`, `llm.ts`,
  `llm/` (dir), `message-error.ts`, `message-v2.ts`, `message.ts`, `overflow.ts`,
  `processor.ts`, `prompt.ts`, `prompt/` (dir), `reminders.ts`, `retry.ts`, `revert.ts`,
  `run-state.ts`, `schema.ts`, `session.ts`, `status.ts`, `summary.ts`, `system.ts`,
  `todo.ts`, `tools.ts`
- TUI entry: `packages/tui/src/` index (`app.tsx`, `index.tsx`, `runtime.tsx`,
  `attention.ts`, `audio.ts`, `audio.d.ts`, `clipboard.ts`, `editor.ts`, `editor-zed.ts`,
  `keymap.tsx`, `logo.ts`, `parsers-config.ts`, `terminal-win32.ts`, `theme/`,
  `component/`, `config/`, `context/`, `feature-plugins/`, `plugin/`, `prompt/`,
  `routes/`, `ui/`, `util/`) and `packages/cli/src/` entry (`index.ts`, `tui.ts`,
  `commands/`, `framework/`, `services/`)
- `packages/` index (34 entries, incl. `app`, `cli`, `client`, `codemode`, `console`,
  `containers`, `core`, `desktop`, `docs`, `function`, `httpapi-codegen`, `identity`,
  `llm`, `opencode`, `plugin`, `protocol`, `schema`, `sdk`, `sdk-next`, `server`,
  `session-ui`, `slack`, `stats`, `storybook`, `tui`, `ui`, `web`)
