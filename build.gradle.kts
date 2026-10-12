plugins {
    id("java")
    id("application")
}

group = "com.assess"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

application {
    // main class for `run` and for packaging into docker image
    mainClass.set("com.assess.Main")
}

repositories {
    mavenCentral()
}

// Versions centralized for easy updates
val springSecurityVersion = "7.1.1"
val postgresqlVersion = "42.7.14"
val otelVersion = "1.62.0"

dependencies {
    // Spring Security (core + OAuth2/OIDC client support)
    implementation("org.springframework.security:spring-security-core:$springSecurityVersion")
    implementation("org.springframework.security:spring-security-config:$springSecurityVersion")
    implementation("org.springframework.security:spring-security-oauth2-client:$springSecurityVersion")

    // Postgres JDBC driver
    implementation("org.postgresql:postgresql:$postgresqlVersion")

    // OpenTelemetry - API + SDK + OTLP exporter (use javaagent in production for automatic instrumentation)
    implementation("io.opentelemetry:opentelemetry-api:$otelVersion")
    implementation("io.opentelemetry:opentelemetry-sdk:$otelVersion")
    implementation("io.opentelemetry:opentelemetry-exporter-otlp:$otelVersion")

    // Testing - JUnit 5 (already present, keep BOM)
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

// Create an executable fat JAR to make Docker image simple (uses built-in Jar; for complex cases consider shadowJar)
tasks.register<Jar>("fatJar") {
    archiveBaseName.set("app")
    archiveVersion.set("")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes(mapOf("Main-Class" to application.mainClass.get()))
    }
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith(".jar") }.map { zipTree(it) }
    })
}
