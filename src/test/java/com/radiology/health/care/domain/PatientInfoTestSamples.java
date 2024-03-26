package com.radiology.health.care.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PatientInfoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static PatientInfo getPatientInfoSample1() {
        return new PatientInfo().id(1L).name("name1").age(1).gender("gender1").dateOfBirth("dateOfBirth1").mobile(1).relation("relation1");
    }

    public static PatientInfo getPatientInfoSample2() {
        return new PatientInfo().id(2L).name("name2").age(2).gender("gender2").dateOfBirth("dateOfBirth2").mobile(2).relation("relation2");
    }

    public static PatientInfo getPatientInfoRandomSampleGenerator() {
        return new PatientInfo()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .age(intCount.incrementAndGet())
            .gender(UUID.randomUUID().toString())
            .dateOfBirth(UUID.randomUUID().toString())
            .mobile(intCount.incrementAndGet())
            .relation(UUID.randomUUID().toString());
    }
}
