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
