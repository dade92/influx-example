package adapters.utils;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InfluxDBDateFormatterTest {

    private final InfluxDBDateFormatter influxDBDateFormatter = new InfluxDBDateFormatter();

    @Test
    void formatCorrectly() {
        Instant now = LocalDateTime.of(2024, 12, 15, 9, 58, 1)
            .toInstant(ZoneOffset.UTC);
        String actual = influxDBDateFormatter.format(now);

        assertEquals("2024-12-15T09:58:01Z", actual);
    }
}