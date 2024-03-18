package com.radiology.health.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ServicesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Services getServicesSample1() {
        return new Services().id(1L).name("name1");
    }

    public static Services getServicesSample2() {
        return new Services().id(2L).name("name2");
    }

    public static Services getServicesRandomSampleGenerator() {
        return new Services().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString());
    }
}
