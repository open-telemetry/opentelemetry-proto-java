pluginManagement {
  plugins {
    id("com.google.protobuf") version "0.8.17"
    id("de.undercouch.download") version "4.1.2"
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
    id("nebula.release") version "16.0.0"
  }
}

rootProject.name = "opentelemetry-proto"
