This is my readme file, i will update this later. Bye!

This project is a small Java application with added dependencies and example Docker/Docker Compose setup.

Build and run locally

1. Build and run tests:

```bash
./gradlew clean test fatJar
```

2. Run the fat JAR produced by the build (if present):

```bash
java -jar build/libs/app.jar
```

Docker

- To build the Docker image:

```bash
docker build -t thiranaivu-app:latest .
```

- To run the app with Docker Compose (includes a Postgres instance):

```bash
docker compose up --build
```

OpenTelemetry

- This project includes OpenTelemetry SDK and OTLP exporter dependencies. For automatic instrumentation in production prefer using the OpenTelemetry Java agent:

```bash
# Example: run with OTLP endpoint
java -javaagent:path/to/opentelemetry-javaagent.jar \
     -Dotel.exporter.otlp.endpoint=http://your-otlp-endpoint:4317 \
     -jar build/libs/app.jar
```

Notes

- Spring Security (OAuth2/OIDC client) and Postgres JDBC driver are added as dependencies.
- There's a simple JUnit 5 smoke test at `src/test/java/com/assess/MainTest.java`.
