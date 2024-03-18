package com.radiology.health.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TechicianEquipmentMappingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TechicianEquipmentMapping getTechicianEquipmentMappingSample1() {
        return new TechicianEquipmentMapping().id(1L).dateTime("dateTime1");
    }

    public static TechicianEquipmentMapping getTechicianEquipmentMappingSample2() {
        return new TechicianEquipmentMapping().id(2L).dateTime("dateTime2");
    }

    public static TechicianEquipmentMapping getTechicianEquipmentMappingRandomSampleGenerator() {
        return new TechicianEquipmentMapping().id(longCount.incrementAndGet()).dateTime(UUID.randomUUID().toString());
    }
}
