pluginManagement {
  plugins {
    id("com.google.protobuf") version "0.9.5"
    id("de.undercouch.download") version "5.6.0"
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
  }
}

plugins {
  id("org.gradle.toolchains.foojay-resolver-convention")
}

rootProject.name = "opentelemetry-proto"
