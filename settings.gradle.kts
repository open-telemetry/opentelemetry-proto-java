pluginManagement {
  plugins {
    id("com.google.protobuf") version "0.9.4"
    id("de.undercouch.download") version "5.6.0"
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
    id("nebula.release") version "20.0.0"
  }
}

rootProject.name = "opentelemetry-proto"
