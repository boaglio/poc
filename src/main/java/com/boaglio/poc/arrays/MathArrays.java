package com.boaglio.poc.arrays;

import java.util.Iterator;
import java.util.TreeSet;

public class MathArrays {

    public static int[] unique(int[] data) {
        TreeSet<Integer> values = new TreeSet<Integer>();
        for (int datum : data) {
            values.add(datum);
        }

        final int count = values.size();
        final int[] out = new int[count];

        Iterator<Integer> iterator = values.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            out[count - ++i] = iterator.next();
        }
        return out;
    }

}
