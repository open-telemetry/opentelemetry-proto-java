import com.google.protobuf.gradle.*
import de.undercouch.gradle.tasks.download.Download
import nebula.plugin.release.git.base.TagStrategy
import nebula.plugin.release.git.semver.NearestVersionLocator
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
  defaultVersionStrategy = nebula.plugin.release.git.opinion.Strategies.getSNAPSHOT()
}

tasks {
  named("release") {
    mustRunAfter("snapshotSetup", "finalSetup")
  }
}

description = "Java Bindings for the OpenTelemetry Protocol (OTLP)"

val grpcVersion = "1.65.1"
val protobufVersion = "4.27.2"

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

// Proto version is set from -Prelease.version or inferred from the latest tag
var protoVersion = if (properties.contains(
    "release.version"
  )) {
  properties.get("release.version") as String
} else {
  NearestVersionLocator(TagStrategy()).locate(release.grgit).any.toString()
}
val protoArchive = file("$buildDir/archives/opentelemetry-proto-$protoVersion.zip")

tasks {
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
