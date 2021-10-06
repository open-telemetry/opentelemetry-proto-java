import com.google.protobuf.gradle.*
import de.undercouch.gradle.tasks.download.Download
import de.undercouch.gradle.tasks.download.Verify
import nebula.plugin.release.git.opinion.Strategies
import java.time.Duration

plugins {
  id("com.google.protobuf")
  id("de.undercouch.download")
  id("io.github.gradle-nexus.publish-plugin")
  id("nebula.release")

  id("otel.java-conventions")
  id("otel.publish-conventions")
}

release {
  defaultVersionStrategy = Strategies.getSNAPSHOT()
}

tasks {
  named("release") {
    mustRunAfter("snapshotSetup", "finalSetup")
  }
}

description = "Java Bindings for the OpenTelemetry Protocol (OTLP)"

val grpcVersion = "1.41.0"
val protobufVersion = "3.18.0"

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

val protoVersion = version.toString().removeSuffix("-SNAPSHOT").removeSuffix("-alpha")
// To generate checksum, download the file and run "shasum -a 256 ~/path/to/vfoo.zip"
val protoChecksum = "5e4131064e9471eb09294374db0d55028fdb73898b08aa07a835d17d61e5f017"
val protoArchive = file("$buildDir/archives/opentelemetry-proto-$protoVersion.zip")

tasks {
  val downloadProtoArchive by registering(Download::class) {
    onlyIf { !protoArchive.exists() }
    src("https://github.com/open-telemetry/opentelemetry-proto/archive/v$protoVersion.zip")
    dest(protoArchive)
  }

  val verifyProtoArchive by registering(Verify::class) {
    dependsOn(downloadProtoArchive)
    src(protoArchive)
    algorithm("SHA-256")
    checksum(protoChecksum)
  }

  val unzipProtoArchive by registering(Copy::class) {
    dependsOn(verifyProtoArchive)
    from(zipTree(protoArchive))
    into("$buildDir/protos")
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
    sonatype {
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
