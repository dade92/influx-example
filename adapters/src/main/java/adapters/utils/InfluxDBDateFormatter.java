package adapters.utils;

import java.time.Instant;

public class InfluxDBDateFormatter {

    public String format(Instant instant) {
        return instant.toString();
    }


}
