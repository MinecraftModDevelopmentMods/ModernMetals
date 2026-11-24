# Releasing Modern Metals for Minecraft 1.15.2

Build with Java 17, Temurin 8u502-b07 for compilation and Java 25 for
Mavenizer. Run check, build, verify115ReleaseArtifacts,
write115ReleaseChecksums, genEclipseRuns and eclipse. Keep the packaged
gameplay and saved-world checks local, then complete manual testing.

Open a PR from the fork to MMD's master-1.15.2. After merging, wait for the
successful push CI run and download ModernMetals-1.15.2-<merged SHA>. Compare
all three jars with the locally qualified bundle. The CI bundle contains
the player, sources and Javadoc jars, SHA256SUMS and CHANGELOG.txt.

Run deploy-release.yml on master-1.15.2 with the release version, full merged
commit and qualified game-jar SHA-256. Leave confirm_live_publication false
for verification. Set it true only when publication is authorised. The
workflow checks the branch tip, successful merged CI and bundle hashes,
then publishes those exact files to MMD Maven, CurseForge and GitHub.
It does not rebuild the jars during publication.

MMD needs CURSEFORGE_TOKEN, MAVEN_UPLOAD_URL, MAVEN_UPLOAD_USERNAME and
MAVEN_UPLOAD_PASSWORD as repository or shared organisation secrets. Maven
publication requires HTTPS and validates the prepared bundle again.

Verify anonymous downloads and hashes on all three services, including the
CurseForge dependency metadata. If a publication job fails, preserve its
logs and retry only the failed job; do not duplicate an accepted upload.
Base Metals is optional and OreSpawn is required. Publish Base Metals first
and keep the compatibility pin on its public 1.15.2 release.
