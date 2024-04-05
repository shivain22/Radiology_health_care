package com.radiology.health.care.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TestCategoriesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static TestCategories getTestCategoriesSample1() {
        return new TestCategories().id(1L).testName("testName1").testDuration(1);
    }

    public static TestCategories getTestCategoriesSample2() {
        return new TestCategories().id(2L).testName("testName2").testDuration(2);
    }

    public static TestCategories getTestCategoriesRandomSampleGenerator() {
        return new TestCategories()
            .id(longCount.incrementAndGet())
            .testName(UUID.randomUUID().toString())
            .testDuration(intCount.incrementAndGet());
    }
}
