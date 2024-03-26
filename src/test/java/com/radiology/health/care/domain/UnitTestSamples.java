package com.radiology.health.care.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class UnitTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Unit getUnitSample1() {
        return new Unit().id(1L).name("name1");
    }

    public static Unit getUnitSample2() {
        return new Unit().id(2L).name("name2");
    }

    public static Unit getUnitRandomSampleGenerator() {
        return new Unit().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString());
    }
}
