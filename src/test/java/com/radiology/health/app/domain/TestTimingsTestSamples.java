package com.radiology.health.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TestTimingsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TestTimings getTestTimingsSample1() {
        return new TestTimings().id(1L).timings("timings1");
    }

    public static TestTimings getTestTimingsSample2() {
        return new TestTimings().id(2L).timings("timings2");
    }

    public static TestTimings getTestTimingsRandomSampleGenerator() {
        return new TestTimings().id(longCount.incrementAndGet()).timings(UUID.randomUUID().toString());
    }
}
