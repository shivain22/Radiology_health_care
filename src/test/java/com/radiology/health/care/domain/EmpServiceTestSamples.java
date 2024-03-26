package com.radiology.health.care.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EmpServiceTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static EmpService getEmpServiceSample1() {
        return new EmpService().id(1L).name("name1");
    }

    public static EmpService getEmpServiceSample2() {
        return new EmpService().id(2L).name("name2");
    }

    public static EmpService getEmpServiceRandomSampleGenerator() {
        return new EmpService().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString());
    }
}
