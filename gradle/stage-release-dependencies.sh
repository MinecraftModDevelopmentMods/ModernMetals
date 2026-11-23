#!/usr/bin/env bash
set -euo pipefail
mirror="${1:?Supply a local dependency mirror}"
project_dir="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
value() { sed -n "s/^$1=//p" "$project_dir/gradle.properties" | tr -d '\r'; }
version="$(value basemetals_version)"
expected="$(value basemetals_sha256)"
[[ "$version" =~ ^[0-9]+\.[0-9]+\.[0-9]+\.114041$ ]]
[[ "$expected" =~ ^[0-9a-f]{64}$ ]]
archive="$mirror/zone/moddev/mc/basemetals/BaseMetals/$version/BaseMetals-$version.jar"
mkdir -p "$(dirname "$archive")"
if [[ ! -f "$archive" ]]; then
    temporary="$(mktemp "$(dirname "$archive")/basemetals.XXXXXX")"
    trap 'rm -f "$temporary"' EXIT
    curl --fail --silent --show-error --location --retry 3 --max-time 60 \
        "https://maven.moddev.zone/releases/zone/moddev/mc/basemetals/BaseMetals/$version/BaseMetals-$version.jar" -o "$temporary"
    printf '%s  %s\n' "$expected" "$temporary" | sha256sum --check
    mv "$temporary" "$archive"
fi
printf '%s  %s\n' "$expected" "$archive" | sha256sum --check
