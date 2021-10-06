plugins {
  `maven-publish`
  signing
}

publishing {
  publications {
    register<MavenPublication>("mavenPublication") {
      val publication = this
      groupId = "io.opentelemetry.proto"
      afterEvaluate {
        // not available until evaluated.
        artifactId = base.archivesName.get()
        pom.description.set(project.description)
        val versionParts = publication.version.split('-').toMutableList()
        versionParts[0] += "-alpha"
        publication.version = versionParts.joinToString("-")
      }

      plugins.withId("java-platform") {
        from(components["javaPlatform"])
      }
      plugins.withId("java-library") {
        from(components["java"])
      }

      versionMapping {
        allVariants {
          fromResolutionResult()
        }
      }

      pom {
        name.set("OpenTelemetry Protocol")
        url.set("https://github.com/open-telemetry/opentelemetry-proto-java")

        licenses {
          license {
            name.set("The Apache License, Version 2.0")
            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
          }
        }

        developers {
          developer {
            id.set("opentelemetry")
            name.set("OpenTelemetry")
            url.set("https://github.com/open-telemetry/community")
          }
        }

        scm {
          connection.set("scm:git:git@github.com:open-telemetry/opentelemetry-proto-java.git")
          developerConnection.set("scm:git:git@github.com:open-telemetry/opentelemetry-proto-java.git")
          url.set("git@github.com:open-telemetry/opentelemetry-proto-java.git")
        }
      }
    }
  }
}

afterEvaluate {
  val publishToSonatype by tasks.getting
  val release by rootProject.tasks.existing
  release.configure {
    finalizedBy(publishToSonatype)
  }
}

if (System.getenv("CI") != null) {
  signing {
    useInMemoryPgpKeys(System.getenv("GPG_PRIVATE_KEY"), System.getenv("GPG_PASSWORD"))
    sign(publishing.publications["mavenPublication"])
  }
}
