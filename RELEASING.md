# Versioning and releasing

Releases of the Java bindings for the OpenTelemetry Protocol (OTLP) follow the same versions
as [releases in opentelemetry-proto](https://github.com/open-telemetry/opentelemetry-proto/releases).

## Starting the release

Upon release of a new [opentelemetry-proto](https://github.com/open-telemetry/opentelemetry-proto) version:

- Open
  the ["Release Build" workflow](https://github.com/open-telemetry/opentelemetry-proto-java/actions/workflows/release-build.yml)
  in your browser
- Click the button that says "Run workflow" next to the phrase "This workflow has a
  `workflow_dispatch` event trigger." and then
  - select the `main` branch
  - enter the version of the new [opentelemetry-proto](https://github.com/open-telemetry/opentelemetry-proto) release
  - click "Run workflow"

A successful workflow run will create:
- a new [tag](https://github.com/open-telemetry/opentelemetry-proto-java/tags)
- a new [release announcement](https://github.com/open-telemetry/opentelemetry-proto-java/releases)
- a new release
  on [Maven Central](https://search.maven.org/artifact/io.opentelemetry.proto/opentelemetry-proto)
