plugins {
  `kotlin-dsl`
}

repositories {
  mavenCentral()
  gradlePluginPortal()
  mavenLocal()
}

dependencies {
  implementation("com.diffplug.spotless:spotless-plugin-gradle:5.16.0")
  implementation("gradle.plugin.com.google.protobuf:protobuf-gradle-plugin:0.8.17")
}
