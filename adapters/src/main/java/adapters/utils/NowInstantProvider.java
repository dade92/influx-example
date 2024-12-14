package adapters.utils;

import java.time.Instant;

public class NowInstantProvider implements InstantProvider {
    @Override
    public Instant get() {
        return Instant.now();
    }
}
