# <img src="https://opentelemetry.io/img/logos/opentelemetry-logo-nav.png" alt="OpenTelemetry Icon" width="45" height=""> Java Bindings for the OpenTelemetry Protocol (OTLP)

[![Maven Central](https://img.shields.io/maven-central/v/io.opentelemetry.proto/opentelemetry-proto.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.opentelemetry.proto%22%20AND%20a:%22opentelemetry-proto%22)

Java code-generation for the OpenTelemetry Protocol Buffer data model. This repository contains
workflows and build scripts that pull releases
from [opentelemetry-proto](https://github.com/open-telemetry/opentelemetry-proto) to generate and
build Java bindings and publish them to the
[Maven Central Repository](https://search.maven.org/artifact/io.opentelemetry.proto/opentelemetry-proto).

## Published releases

You can use the published releases available
on [Maven Central](https://search.maven.org/artifact/io.opentelemetry.proto/opentelemetry-proto). To
do so, add the following as dependencies to your build configuration and replace `{{version}}` with
your desired version.

### Maven

```xml

<dependency>
  <groupId>io.opentelemetry.proto</groupId>
  <artifactId>opentelemetry-proto</artifactId>
  <version>{{version}}</version>
</dependency>
```

### Gradle

```kotlin
implementation("io.opentelemetry.proto:opentelemetry-proto:{{version}}")
```

## Project setup

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

## Support

The generated java bindings published from this repository are provided as-is.

For generic documentation on how to use protobuf bindings,
see [gRPC documentation](https://grpc.io/docs/languages/java/generated-code/)
and [protobuf java documentation](https://protobuf.dev/reference/java/java-generated/).

We have no intention of eventually publishing stable artifacts. If you need guarantees,
please generate your own bindings,
consulting [grpc codegen](https://grpc.io/docs/languages/java/generated-code/#codegen) and
possibly [build.gradle.kts](build.gradle.kts)

## Releasing

See [RELEASING.md](./RELEASING.md)
