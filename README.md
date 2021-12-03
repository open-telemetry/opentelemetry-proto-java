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

```groovy
implementation 'io.opentelemetry.proto:opentelemetry-proto:{{version}}'
```

## Releasing

See [RELEASING.md](./RELEASING.md)