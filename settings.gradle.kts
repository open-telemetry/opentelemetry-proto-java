pluginManagement {
  plugins {
    id("com.google.protobuf") version "0.9.4"
    id("de.undercouch.download") version "5.4.0"
    id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
    id("nebula.release") version "17.2.2"
  }
}

rootProject.name = "opentelemetry-proto"
