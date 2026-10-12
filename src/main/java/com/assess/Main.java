package com.assess;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;

public class Main {
    public static void main(String[] args) {
        // Basic OpenTelemetry usage (will be a noop unless SDK/exporter configured at runtime)
        Tracer tracer = GlobalOpenTelemetry.getTracer("com.assess.Main");
        Span span = tracer.spanBuilder("startup").startSpan();
        try {
            System.out.println("Hello and welcome!");
            for (int i = 1; i <= 5; i++) {
                System.out.println("i = " + i);
            }
        } finally {
            span.end();
        }
    }
}
