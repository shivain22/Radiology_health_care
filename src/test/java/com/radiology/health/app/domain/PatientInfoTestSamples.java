package com.radiology.health.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PatientInfoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static PatientInfo getPatientInfoSample1() {
        return new PatientInfo().id(1L).age(1).gender("gender1").relation("relation1");
    }

    public static PatientInfo getPatientInfoSample2() {
        return new PatientInfo().id(2L).age(2).gender("gender2").relation("relation2");
    }

    public static PatientInfo getPatientInfoRandomSampleGenerator() {
        return new PatientInfo()
            .id(longCount.incrementAndGet())
            .age(intCount.incrementAndGet())
            .gender(UUID.randomUUID().toString())
            .relation(UUID.randomUUID().toString());
    }
}
