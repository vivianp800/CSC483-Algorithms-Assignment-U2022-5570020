package com.csc483.assignment2.sorting;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class SortingDataGenerator {

    private static final Random RAND = new Random(42);

    public static int[] random(int size) {
        int[] data = new int[size];
        for (int i = 0; i < size; i++) data[i] = RAND.nextInt(size * 10);
        return data;
    }

    public static int[] sorted(int size) {
        int[] data = new int[size];
        for (int i = 0; i < size; i++) data[i] = i;
        return data;
    }

    public static int[] reverseSorted(int size) {
        int[] data = new int[size];
        for (int i = 0; i < size; i++) data[i] = size - i;
        return data;
    }

    public static int[] nearlySorted(int size) {
        int[] data = sorted(size);
        int swaps  = size / 10;
        for (int i = 0; i < swaps; i++) {
            int a = RAND.nextInt(size), b = RAND.nextInt(size);
            int tmp = data[a]; data[a] = data[b]; data[b] = tmp;
        }
        return data;
    }

    public static int[] manyDuplicates(int size) {
        int[] data = new int[size];
        for (int i = 0; i < size; i++) data[i] = RAND.nextInt(10);
        return data;
    }

    public static void writeTxt(int[] data, String filePath) throws IOException {
        try (FileWriter fw = new FileWriter(filePath)) {
            for (int i = 0; i < data.length; i++) {
                fw.write(data[i] + (i < data.length - 1 ? "," : "\n"));
            }
        }
    }
}
