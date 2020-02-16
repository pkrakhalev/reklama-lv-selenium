package com.reklama.lv.utils;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;


public class RandomHelper {

    private static final Random rng = new Random();

    public static Set<Integer> generateUniqueRandomInt(int numbersNeeded, int maxRange) {
        if (maxRange < numbersNeeded) {
            throw new IllegalArgumentException("Can't ask for more numbers than are available");
        }

        Set<Integer> generated = new LinkedHashSet<>();
        while (generated.size() < numbersNeeded) {
            Integer next = rng.nextInt(maxRange);
            generated.add(next);
        }
        return generated;
    }
}
