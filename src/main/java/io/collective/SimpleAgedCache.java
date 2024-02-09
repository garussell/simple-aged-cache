package io.collective;

import java.time.Clock;
import java.util.Arrays;

public class SimpleAgedCache {
    private static final int INITIAL_CAPACITY = 10;
    private static final int GROWTH_FACTOR = 2;

    private Object[] keys;
    private Object[] values;
    private long[] expirationTimes;
    private final Clock clock;
    private int size;

    public SimpleAgedCache(Clock clock) {
        this.clock = clock;
        this.keys = new Object[INITIAL_CAPACITY];
        this.values = new Object[INITIAL_CAPACITY];
        this.expirationTimes = new long[INITIAL_CAPACITY];
        this.size = 0;
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
