package com.delivery.order.web;

import org.springframework.stereotype.Component;

@Component
public class OrderRateLimiter {

    private static final double CAPACITY = 20.0;
    private static final double REFILL_PER_SECOND = 20.0;

    private final Object lock = new Object();
    private double tokens = CAPACITY;
    private long lastNanos = System.nanoTime();

    public boolean tryAcquire() {
        synchronized (lock) {
            long now = System.nanoTime();
            double elapsedSeconds = (now - lastNanos) / 1_000_000_000.0;
            tokens = Math.min(CAPACITY, tokens + elapsedSeconds * REFILL_PER_SECOND);
            lastNanos = now;
            if (tokens < 1.0) {
                return false;
            }
            tokens -= 1.0;
            return true;
        }
    }
}
