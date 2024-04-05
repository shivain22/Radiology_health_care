package com.radiology.health.care.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PatientTestTimingsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static PatientTestTimings getPatientTestTimingsSample1() {
        return new PatientTestTimings()
            .id(1L)
            .priority("priority1")
            .clinicalNote("clinicalNote1")
            .spclInstruction("spclInstruction1")
            .status("status1");
    }

    public static PatientTestTimings getPatientTestTimingsSample2() {
        return new PatientTestTimings()
            .id(2L)
            .priority("priority2")
            .clinicalNote("clinicalNote2")
            .spclInstruction("spclInstruction2")
            .status("status2");
    }

    public static PatientTestTimings getPatientTestTimingsRandomSampleGenerator() {
        return new PatientTestTimings()
            .id(longCount.incrementAndGet())
            .priority(UUID.randomUUID().toString())
            .clinicalNote(UUID.randomUUID().toString())
            .spclInstruction(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString());
    }
}
