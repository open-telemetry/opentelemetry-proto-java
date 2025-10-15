import com.google.protobuf.gradle.*
import de.undercouch.gradle.tasks.download.Download
import java.time.Duration

plugins {
  id("com.google.protobuf")
  id("de.undercouch.download")
  id("io.github.gradle-nexus.publish-plugin")

  id("otel.java-conventions")
  id("otel.publish-conventions")
}

// start - updated by ./.github/workflows/prepare-release-branch.yml
val snapshot = true
// end

// The release version of opentelemetry-proto used to generate classes
var protoVersion = "1.8.0"

// Compute the artifact version, include the "-SNAPSHOT" suffix if not releasing
// Release example: 1.5.0
// Snapshot example: 1.5.0-SNAPSHOT
var ver = protoVersion
if (snapshot) {
  ver += "-SNAPSHOT"
}
version = ver

description = "Java Bindings for the OpenTelemetry Protocol (OTLP)"

val grpcVersion = "1.76.0"
val protobufVersion = "4.33.0"

repositories {
  mavenCentral()
  mavenLocal()
}

dependencies {
  api("com.google.protobuf:protobuf-java:$protobufVersion")

  // Workaround for @javax.annotation.Generated
  // see: https://github.com/grpc/grpc-java/issues/3633
  compileOnly("javax.annotation:javax.annotation-api:1.3.2")

  compileOnly("io.grpc:grpc-api:$grpcVersion")
  compileOnly("io.grpc:grpc-protobuf:$grpcVersion")
  compileOnly("io.grpc:grpc-stub:$grpcVersion")
}

protobuf {
  protoc {
    // The artifact spec for the Protobuf Compiler
    artifact = "com.google.protobuf:protoc:$protobufVersion"
  }
  plugins {
    id("grpc") {
      artifact = "io.grpc:protoc-gen-grpc-java:$grpcVersion"
    }
  }
  generateProtoTasks {
    all().configureEach {
      plugins {
        id("grpc")
      }
    }
  }
}

val protoArchive = file("$buildDir/archives/opentelemetry-proto-$protoVersion.zip")

tasks {
  test {
    useJUnitPlatform()
  }

  val downloadProtoArchive by registering(Download::class) {
    onlyIf { !protoArchive.exists() }
    src("https://github.com/open-telemetry/opentelemetry-proto/archive/v$protoVersion.zip")
    dest(protoArchive)
  }

  val unzipProtoArchive by registering(Copy::class) {
    dependsOn(downloadProtoArchive)
    from(zipTree(protoArchive))
    into("$buildDir/protos")
  }

  named("processResources") {
    dependsOn(unzipProtoArchive)
  }

  afterEvaluate {
    named("generateProto") {
      dependsOn(unzipProtoArchive)
    }
  }
}

sourceSets {
  main {
    proto {
      srcDir("$buildDir/protos/opentelemetry-proto-$protoVersion")
    }
  }
}

nexusPublishing {
  packageGroup.set("io.opentelemetry")

  repositories {
    // see https://central.sonatype.org/publish/publish-portal-ossrh-staging-api/#configuration
    sonatype {
      nexusUrl.set(uri("https://ossrh-staging-api.central.sonatype.com/service/local/"))
      snapshotRepositoryUrl.set(uri("https://central.sonatype.com/repository/maven-snapshots/"))
      username.set(System.getenv("SONATYPE_USER"))
      password.set(System.getenv("SONATYPE_KEY"))
    }
  }

  connectTimeout.set(Duration.ofMinutes(5))
  clientTimeout.set(Duration.ofMinutes(5))

  transitionCheckOptions {
    maxRetries.set(300)
    delayBetween.set(Duration.ofSeconds(10))
  }
}
