package com.radiology.health.care.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class RoomTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Room getRoomSample1() {
        return new Room().id(1L).roomNo(1);
    }

    public static Room getRoomSample2() {
        return new Room().id(2L).roomNo(2);
    }

    public static Room getRoomRandomSampleGenerator() {
        return new Room().id(longCount.incrementAndGet()).roomNo(intCount.incrementAndGet());
    }
}
