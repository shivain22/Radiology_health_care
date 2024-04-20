package com.radiology.health.care.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class OfficeTimingsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static OfficeTimings getOfficeTimingsSample1() {
        return new OfficeTimings().id(1L);
    }

    public static OfficeTimings getOfficeTimingsSample2() {
        return new OfficeTimings().id(2L);
    }

    public static OfficeTimings getOfficeTimingsRandomSampleGenerator() {
        return new OfficeTimings().id(longCount.incrementAndGet());
    }
}
