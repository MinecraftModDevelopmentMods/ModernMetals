#!/usr/bin/env bash
set -euo pipefail

mirror="${1:?Supply a local dependency mirror}"
file_id=9611746
archive="$mirror/curse/maven/mmd-orespawn-245586/$file_id/mmd-orespawn-245586-$file_id.jar"
mkdir -p "$(dirname "$archive")"
if [[ ! -f "$archive" ]]; then
    temporary="$(mktemp "$(dirname "$archive")/orespawn.XXXXXX")"
    trap 'rm -f "$temporary"' EXIT
    curl --fail --silent --show-error --location --retry 3 --max-time 60 \
        'https://mediafilez.forgecdn.net/files/9611/746/OreSpawn-4.1.7.113021.jar' -o "$temporary"
    printf '%s  %s\n' e65dcca0623a20111c40779cb0fe2078e98c618faa68f79c9a28e1e53c63957e "$temporary" | sha256sum --check
    mv "$temporary" "$archive"
fi
printf '%s  %s\n' e65dcca0623a20111c40779cb0fe2078e98c618faa68f79c9a28e1e53c63957e "$archive" | sha256sum --check
