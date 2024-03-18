package com.radiology.health.app.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class PatientTestInfoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static PatientTestInfo getPatientTestInfoSample1() {
        return new PatientTestInfo().id(1L);
    }

    public static PatientTestInfo getPatientTestInfoSample2() {
        return new PatientTestInfo().id(2L);
    }

    public static PatientTestInfo getPatientTestInfoRandomSampleGenerator() {
        return new PatientTestInfo().id(longCount.incrementAndGet());
    }
}
