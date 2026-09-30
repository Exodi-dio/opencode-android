#!/usr/bin/env bash
# M1 conformance stub: prints the pinned upstream SHA and exits 0 with a
# TODO-M6 marker file. Never fails M1; the real differential oracle lands in M6.
set -u

PIN="${UPSTREAM_PIN_SHA:-2fa3363c924c5c3e367b84a87ae478296a0ed59b}"
REPORT_DIR="${1:-conformance-report}"

echo "conformance-stub: upstream pin $PIN"
mkdir -p "$REPORT_DIR"
printf '%s\n' "TODO-M6: differential conformance report not yet implemented (M1 stub, upstream pin $PIN)" > "$REPORT_DIR/TODO-M6.txt"
echo "conformance-stub: wrote $REPORT_DIR/TODO-M6.txt (exit 0, M1 stub)"
exit 0
