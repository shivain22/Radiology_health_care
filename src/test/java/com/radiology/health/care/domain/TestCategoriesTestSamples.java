package com.radiology.health.care.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TestCategoriesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TestCategories getTestCategoriesSample1() {
        return new TestCategories().id(1L).testName("testName1");
    }

    public static TestCategories getTestCategoriesSample2() {
        return new TestCategories().id(2L).testName("testName2");
    }

    public static TestCategories getTestCategoriesRandomSampleGenerator() {
        return new TestCategories().id(longCount.incrementAndGet()).testName(UUID.randomUUID().toString());
    }
}
