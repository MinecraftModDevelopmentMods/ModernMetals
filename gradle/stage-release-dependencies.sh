#!/usr/bin/env bash
set -euo pipefail
mirror="${1:?Supply a local dependency mirror}"
project_dir="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
value() { sed -n "s/^$1=//p" "$project_dir/gradle.properties" | tr -d '\r'; }
stage() (
    local mod_id="$1" module="$2" version expected relative archive temporary
    version="$(value "${mod_id}_version")"
    expected="$(value "${mod_id}_sha256")"
    [[ "$version" =~ ^[0-9]+\.[0-9]+\.[0-9]+\.115021$ ]]
    [[ "$expected" =~ ^[0-9a-f]{64}$ ]]
    relative="zone/moddev/mc/$mod_id/$module/$version/$module-$version.jar"
    archive="$mirror/$relative"
    mkdir -p "$(dirname "$archive")"
    if [[ ! -f "$archive" ]]; then
        temporary="$(mktemp "$(dirname "$archive")/download.XXXXXX")"
        trap 'rm -f "$temporary"' EXIT
        curl --fail --silent --show-error --location --retry 3 --max-time 60 \
            "https://maven.moddev.zone/releases/$relative" -o "$temporary"
        printf '%s  %s\n' "$expected" "$temporary" | sha256sum --check
        mv "$temporary" "$archive"
    fi
    printf '%s  %s\n' "$expected" "$archive" | sha256sum --check
)

stage basemetals BaseMetals
stage orespawn OreSpawn
