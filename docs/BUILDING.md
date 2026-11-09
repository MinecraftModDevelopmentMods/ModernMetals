# Building Modern Metals 1.11.2

Use Gradle 9.6.1, ForgeGradle 7.0.34, Renamer 1.1.5 and Foojay 1.0.0. Gradle runs
on Java 17 and Mavenizer uses Java 25. Compilation, bytecode and Minecraft use Java 8.
Forge is 13.20.1.2588 with stable-32 mappings. Release pins are Temurin 8u502-b07,
17.0.1+12 and 25.0.3+9-LTS. Install them or pass their homes with
`org.gradle.java.installations.paths`. Do not launch Gradle on Java 8.

Use Java 17 for `JAVA_HOME` and `PATH`. Stage the pinned public Base Metals
and OreSpawn jars before building or importing the project into Eclipse.

```powershell
$env:GRADLE_USER_HOME='<one workspace cache shared with Eclipse>'
python gradle/stage-legacy-dependencies.py .gradle/legacy-dependencies
python -m pip install -r tools/validation/requirements.txt
.\gradlew.bat clean check build javadoc verifyReleaseArtifacts verifyProjectResources prepareEclipse
```

Use `-PpythonExecutable=<python executable>` if Python is not named `python3`.
`gradle/dependencies.json` pins production downloads and SHA-256. Staging creates
a verified Maven mirror. Use `-PlegacyDependencyVerificationRepository=<mirror>`
to select another location. Remapping and signature removal apply to development
copies. No floating mod versions, JCenter or
`mavenLocal()` are used. A normal build never publishes.

`build/libs/ModernMetals-2.5.2.111021.jar` is the installed jar. Sources/Javadocs
are separate. Test probes are isolated from all release archives.

## Eclipse

Run `prepareEclipse` and import the inner Git/Gradle project using Buildship. Use
Java 17 for Gradle, a Java 8 project JRE and one physical cache for Eclipse and the
terminal. Production profiles contain Modern Metals, Base Metals and OreSpawn.
Optional APIs are compilation dependencies. Test profiles are separate. Regenerate
profiles when dependency/toolchain locations change. FG7 uses `genEclipseRuns` and
`prepareEclipse`.

Gradle launches merge production classes and processed resources into one mod
directory. Eclipse uses `bin/main`, with resources from `build/resources/main` so
metadata is not duplicated. `verifyEclipseProductionClasspath` checks isolation.

## Verification

`generateOreModels` generates the ore block models as part of the normal build.
See [ore overlays](ORE_OVERLAYS.md) for the inputs, output and client checks.

`test` covers config switches, provider conversion, titanium defaults, fantasy
policy and namespace compatibility. `verifyProjectResources` parses resources,
checks material assets, validates the provider against the pinned OreSpawn schema
and audits workflow pins. Packaged server checks require a clean Forge template:

```text
gradlew packagedForgeRuntimeTest -PpackagedForgeRuntimeRoot=<clean Forge template> -PpackagedForgeRuntimeOutput=<new disposable directory>
```

The output directory must not already exist. See the validation README for exact
client commands, migration scenarios and limitations.
