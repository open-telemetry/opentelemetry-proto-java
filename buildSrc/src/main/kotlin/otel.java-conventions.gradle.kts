import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
  `java-library`

  eclipse
  idea

  id("otel.spotless-conventions")
}

java {
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(17))
  }

  withJavadocJar()
  withSourcesJar()
}

tasks {
  withType<JavaCompile>().configureEach {
    with(options) {
      release.set(8)

      //disable deprecation warnings for the protobuf module
      compilerArgs.add("-Xlint:-deprecation")

      encoding = "UTF-8"
    }
  }

  withType<Test>().configureEach {
    useJUnitPlatform()

    testLogging {
      exceptionFormat = TestExceptionFormat.FULL
      showExceptions = true
      showCauses = true
      showStackTraces = true
    }
    maxHeapSize = "1500m"
  }

  withType<Javadoc>().configureEach {
    exclude("io/opentelemetry/**/internal/**")

    with(options as StandardJavadocDocletOptions) {
      source = "8"
      encoding = "UTF-8"
      docEncoding = "UTF-8"
      breakIterator(true)

      addBooleanOption("html5", true)

      links("https://docs.oracle.com/javase/8/docs/api/")
      addBooleanOption("Xdoclint:all,-missing", true)
    }
  }

  withType<Jar>().configureEach {
    manifest {
      attributes(
        "Automatic-Module-Name" to "io.opentelemetry.proto",
        "Built-By" to System.getProperty("user.name"),
        "Built-JDK" to System.getProperty("java.version"),
        "Implementation-Title" to project.name,
        "Implementation-Version" to project.version)
    }
  }

  afterEvaluate {
    withType<Javadoc>().configureEach {
      with(options as StandardJavadocDocletOptions) {
        val title = "${project.description}"
        docTitle = title
        windowTitle = title
      }
    }
  }
}

configurations.configureEach {
  resolutionStrategy {
    failOnVersionConflict()
    preferProjectModules()
  }
}
