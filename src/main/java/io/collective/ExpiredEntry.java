package io.collective;

import java.time.Clock;

public class ExpiredEntry {
    private final Clock clock;

    public ExpiredEntry(Clock clock) {
        this.clock = clock;
    }

    public void cleanupExpiredEntries(Object[] keys, Object[] values, long[] expirationTimes, int size) {
        long currentTime = clock.millis();
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] != null && currentTime >= expirationTimes[i]) {
                keys[i] = null;
                values[i] = null;
                expirationTimes[i] = 0;
                size--;
            }
        }
    }
}
