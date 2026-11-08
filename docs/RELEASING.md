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

The dispatcher must be installed on the repository's default branch before a
manual release can be started. The current dispatcher on the 1.12 default
branch routes 1.10.2. Install the reviewed 1.11.2 routing there as part of an
authorized release. If an upload times out, check its status before trying again.

For the default-branch change, copy only the audited `deploy-release.yml`. It
routes each legacy suffix to its target branch and builds there. After a
release is publicly available, update the Forge 1.11.2 entries in `update.json`
to the exact version reported by Forge.

The checksum-pinned Base Metals 1.11.2 and OreSpawn 4.1.5.111021 jars are
staged locally. Hosted CI requires matching files at their public Maven
coordinates. Reverify the pins if the published bytes differ from the
locally tested artifacts.
