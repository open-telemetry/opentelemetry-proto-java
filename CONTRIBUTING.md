# Contributing to OpenTelemetry Proto Java

## Introduction

Welcome to the OpenTelemetry Proto Java repository! 🎉

Thank you for considering contributing to this project. Whether you are fixing a bug, adding new features, improving documentation, or reporting an issue, your contributions help enhance the OpenTelemetry ecosystem.

This repository provides Java bindings for OpenTelemetry protocol (OTLP) by generating code from the OpenTelemetry protocol definitions. 

If you have any questions, feel free to ask in the community channels. We’re happy to help! 😊


## Pre-requisites

Before getting started, ensure you have the following installed:

* **JDK 11 or higher** – [Install OpenJDK](https://adoptopenjdk.net/)
* **Gradle** – [Install Gradle](https://gradle.org/install/)
* **Docker** (optional, for testing)

Additional Notes:
* Ensure your JAVA_HOME is correctly set.
* Consider using a Gradle wrapper (`./gradlew`) instead of a system-wide Gradle installation.

## Local Run/Build

To set up your local development environment:

```bash
# Clone the repository
git clone https://github.com/open-telemetry/opentelemetry-proto-java.git
cd opentelemetry-proto-java
```

### Building the Project

To build the project and generate Java bindings from the OpenTelemetry proto definitions:

```bash
./gradlew build
```

By default, the proto definitions will be downloaded from the latest published tag of `opentelemetry-proto-java`. 

If the latest version is, for example, `0.20.0`, the protos will be downloaded from the `v0.20.0` release.

To override this and specify a different version:

```bash
./gradlew build -Prelease.version.prop=1.0.0
```

## Testing

To run tests:

```bash
./gradlew test
```

To run a specific test:

```bash
./gradlew test --tests com.example.SomeTest
```

To generate a coverage report:

```bash
./gradlew jacocoTestReport
```

## Contributing Rules

- Follow [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html).
- Ensure new features have appropriate test coverage.
- Run `./gradlew check` before submitting a PR.
- Include clear and concise documentation updates if needed.

Check for issues labeled [`good first issue`](https://github.com/open-telemetry/opentelemetry-proto-java/issues?q=is%3Aissue+is%3Aopen+label%3A%22good+first+issue%22) to start contributing.


## Further Help

Need help? Join our community:

- **Slack**: [OpenTelemetry Slack](https://opentelemetry.io/community/)
- **GitHub Discussions**: [OpenTelemetry Java Discussions](https://github.com/open-telemetry/opentelemetry-java/discussions)
- **Issues**: If you encounter a bug, [open an issue](https://github.com/open-telemetry/opentelemetry-proto-java/issues)



## Troubleshooting Guide

### Common Issues & Fixes

#### Build fails due to missing dependencies
**Error:** `Could not resolve dependencies`

**Fix:** Run:
```bash
./gradlew build --refresh-dependencies
```

#### Tests failing
**Error:** `Test XYZ failed`

**Fix:**
```bash
./gradlew test --info
```
Check logs and rerun the failing test with:
```bash
./gradlew test --tests com.example.FailingTest
```


## Additional Information

### Dependency Updates
To check for outdated dependencies:
```bash
./gradlew dependencyUpdates
```

### Publishing to Maven Local
To publish artifacts to your local Maven repository:
```bash
./gradlew publishToMavenLocal
```

### Project setup

The build downloads proto definitions
from [open-telemetry/opentelemetry-proto](https://github.com/open-telemetry/opentelemetry-proto) and
generates Java bindings:

```shell
./gradlew build
```

By default protos definitions will be downloaded for the latest published tag
of `opentelemetry-proto-java`. For example, if the latest version
is [0.20.0](https://github.com/open-telemetry/opentelemetry-proto-java/tree/v0.20.0), protos will be
downloaded from
the [v0.20.0 release](https://github.com/open-telemetry/opentelemetry-proto/releases/tag/v0.20.0).
This can be overridden for the build or other gradle tasks (e.g. `publishToMavenLocal`)
with `-Prelease.version.prop`:

```shell
./gradlew build -Prelease.version.prop=1.0.0
```

Thank you for contributing!
