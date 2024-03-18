package com.radiology.health.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EquipmentsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Equipments getEquipmentsSample1() {
        return new Equipments().id(1L).name("name1");
    }

    public static Equipments getEquipmentsSample2() {
        return new Equipments().id(2L).name("name2");
    }

    public static Equipments getEquipmentsRandomSampleGenerator() {
        return new Equipments().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString());
    }
}
