package io.collective;

import java.time.Clock;

public class ExpiredEntry {
    private SimpleAgedCache cache;
    private Clock clock;

    public ExpiredEntry(SimpleAgedCache cache, Clock clock) {
        this.cache = cache;
        this.clock = clock;
    }

    public void cleanupExpiredEntries() {
        long currentTime = clock.millis();
        Object[] keys = cache.getKeys();
        Object[] values = cache.getValues();
        long[] expirationTimes = cache.getExpirationTimes();
        int size = cache.getSize();

        for (int i = 0; i < size; i++) {
            if (keys[i] != null && currentTime >= expirationTimes[i]) {
                keys[i] = null;
                values[i] = null;
                expirationTimes[i] = 0;
                size--;
            }
        }
        cache.setSize(size); // Update the size in the cache
    }
}
