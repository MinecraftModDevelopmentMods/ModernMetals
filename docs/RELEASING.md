# Release preparation

Normal builds never publish. Main, sources, Javadocs and SHA-256 form one audited
candidate. Gradle enforces Java 8 bytecode, deterministic archives, processed
metadata, dependency hashes and exclusion of tests, local context and shared
library copies. Prepared bundles contain exactly those three jars, `SHA256SUMS`
and `CHANGELOG.txt`; `verifyPreparedReleaseArtifacts` audits without rebuilding.

CI has a cold bootstrap/offline rebuild, build/tests, resource/schema checks,
packaged server qualification, Eclipse generation and clean reproducibility check.
CodeQL/wrapper checks are separate. Actions use full commit hashes.

Versions use `Major.Minor.Patch.110021`. Tags validate metadata/prior CI without
publishing. The dispatcher requires `confirm_live_publication`, the upstream
repository, matching target/tag/commit and successful hosted checks. It publishes
the same candidate bytes to Maven, CurseForge **244357**, then GitHub. CurseForge
requires Base Metals **240967** and MMD OreSpawn **245586**, with Additional Loot
Tables **243157** optional. Credentials and an HTTPS Maven target are required.

The dispatcher must be installed on the repository's default branch before a
manual release can be started. Merging the 1.10 branch does not install it on the
1.12 default branch. Publication is a separate step and needs approval. If an
upload times out, check its status before trying again.
