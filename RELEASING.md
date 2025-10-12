# Release Process

Releases are performed in lockstep
with [open-telemetry/opentelemetry-proto](https://github.com/open-telemetry/opentelemetry-proto) [releases](https://github.com/open-telemetry/opentelemetry-proto/releases).

## Preparing a new release

Applies to major, minor and patch releases of `open-telemetry/opentelemetry-proto`.

* Merge a PR to `main` updating the `protoVersion` variable in `build.gradle.kts` to the version
  of `opentelemetry-proto` to be released
* Run
  the [Prepare release branch workflow](https://github.com/open-telemetry/opentelemetry-proto-java/actions/workflows/prepare-release-branch.yml)
  * Press the "Run workflow" button, and leave the default branch `main` selected
  * Review and merge the pull request it creates (targeted to the release branch, which sets `snapshot = false`)

## Preparing a patch release

TODO: Define process for releasing a patch, which should add a 4th component to
the `opentelemetry-proto` release version, e.g. `v1.5.0.1`

## Making the release

* Run
  the [Release workflow](https://github.com/open-telemetry/opentelemetry-proto-java/actions/workflows/release.yml)
  * Press the "Run workflow" button, then select the release branch from the dropdown list,
    e.g. `release/v1.8.0`, and click the "Run workflow" button below that.
  * This workflow will publish artifacts to maven central and will publish a GitHub release with
    auto-generated release notes.

## Credentials

See [opentelemetry-java credentials](https://github.com/open-telemetry/opentelemetry-java/blob/main/RELEASING.md#credentials).
