package com.radiology.health.care.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class TechnicianEquipmentMappingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TechnicianEquipmentMapping getTechnicianEquipmentMappingSample1() {
        return new TechnicianEquipmentMapping().id(1L);
    }

    public static TechnicianEquipmentMapping getTechnicianEquipmentMappingSample2() {
        return new TechnicianEquipmentMapping().id(2L);
    }

    public static TechnicianEquipmentMapping getTechnicianEquipmentMappingRandomSampleGenerator() {
        return new TechnicianEquipmentMapping().id(longCount.incrementAndGet());
    }
}
