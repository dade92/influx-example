package adapters.repository;

import java.time.Instant;

public record Measure(Instant time, String field, Object value) {}

