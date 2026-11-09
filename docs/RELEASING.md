# Release preparation

Normal builds never publish. Main, sources, Javadocs and SHA-256 form one audited
candidate. Gradle enforces Java 8 bytecode, deterministic archives, processed
metadata, dependency hashes and exclusion of tests, local context and shared
library copies. Prepared bundles contain exactly those three jars, `SHA256SUMS`
and `CHANGELOG.txt`. `verifyPreparedReleaseArtifacts` audits the bundle as supplied.

CI has a cold bootstrap/offline rebuild, build/tests, resource/schema checks,
packaged server qualification, Eclipse generation and clean reproducibility check.
CodeQL/wrapper checks are separate. Actions use full commit hashes.

The 1.11.2 version uses `Major.Minor.Patch.111021`. Tags validate metadata/prior CI without
publishing. The dispatcher requires `confirm_live_publication`, the upstream
repository, the full checked target commit, the verified game jar's SHA-256
and successful hosted checks. It publishes
the same candidate bytes to Maven, CurseForge **244357**, then GitHub. CurseForge
requires Base Metals **240967** and MMD OreSpawn **245586**, with Additional Loot
Tables **243157** optional. Credentials and an HTTPS Maven target are required.

Start the release from the repository's default branch, `master-1.12`. Its
dispatcher routes the version to `master-1.11.2`. If an upload times out, check
its status before trying again. After the CurseForge download is publicly
available and verified, update the Forge 1.11.2 entries in `update.json` to
the exact version reported by Forge.

CI stages the public Base Metals and OreSpawn jars listed in
`gradle/dependencies.json` and verifies their SHA-256 checksums.
