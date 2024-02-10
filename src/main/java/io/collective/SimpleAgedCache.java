package io.collective;

import java.time.Clock;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Arrays;

public class SimpleAgedCache {
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private static final int INITIAL_CAPACITY = 10;
    private static final int GROWTH_FACTOR = 2;

    protected Object[] keys;
    protected Object[] values;
    protected long[] expirationTimes;
    protected final Clock clock;
    protected int size;
    private final ExpiredEntry expiredEntry;

    public SimpleAgedCache(Clock clock) {
        this.clock = clock;
        this.keys = new Object[INITIAL_CAPACITY];
        this.values = new Object[INITIAL_CAPACITY];
        this.expirationTimes = new long[INITIAL_CAPACITY];
        this.size = 0;

        // Initialize the scheduler for cleanup
        scheduler.scheduleAtFixedRate(this::runCleanupTask, 0, 1, TimeUnit.MINUTES);

        // Instantiate ExpiredEntry
        this.expiredEntry = new ExpiredEntry(clock);
    }

    private void runCleanupTask() {
        expiredEntry.cleanupExpiredEntries(keys, values, expirationTimes, size);
    }

    public SimpleAgedCache() {
        this(Clock.systemDefaultZone());
    }

    private void ensureCapacity(int minCapacity) {
        int currentCapacity = keys.length;
        if (minCapacity > currentCapacity) {
            int newCapacity = Math.max(currentCapacity * GROWTH_FACTOR, minCapacity);
            keys = Arrays.copyOf(keys, newCapacity);
            values = Arrays.copyOf(values, newCapacity);
            expirationTimes = Arrays.copyOf(expirationTimes, newCapacity);
        }
    }

    public void put(Object key, Object value, int retentionInMillis) {
        ensureCapacity(size + 1);
        long expirationTime = clock.millis() + retentionInMillis;
        keys[size] = key;
        values[size] = value;
        expirationTimes[size] = expirationTime;
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public Object get(Object key) {
        long currentTime = clock.millis();
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] != null && keys[i].equals(key)) {
                if (currentTime >= expirationTimes[i]) {
                    keys[i] = null;
                    values[i] = null;
                    expirationTimes[i] = 0;
                    size--;
                    return null;
                } else {
                    return values[i];
                }
            }
        }
        return null;
    }
}
