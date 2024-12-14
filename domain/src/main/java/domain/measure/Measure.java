package domain.measure;

import java.time.Instant;

public record Measure(Instant time, String field, Double value) {}

