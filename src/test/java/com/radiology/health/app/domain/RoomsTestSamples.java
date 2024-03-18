package com.radiology.health.app.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class RoomsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Rooms getRoomsSample1() {
        return new Rooms().id(1L).roomNo(1);
    }

    public static Rooms getRoomsSample2() {
        return new Rooms().id(2L).roomNo(2);
    }

    public static Rooms getRoomsRandomSampleGenerator() {
        return new Rooms().id(longCount.incrementAndGet()).roomNo(intCount.incrementAndGet());
    }
}
