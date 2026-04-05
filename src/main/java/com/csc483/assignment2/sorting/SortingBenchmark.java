package com.csc483.assignment2.sorting;

import java.util.Arrays;

public class SortingBenchmark {

    static final int[] SIZES = {100, 1_000, 10_000, 100_000};
    static final int   RUNS  = 5;

    static final Sorter[] SORTERS = {
        new InsertionSort(),
        new MergeSort(),
        new QuickSort()
    };

    public static void main(String[] args) throws Exception {
        String[][] types = {
            {"Random",          "random"},
            {"Sorted",          "sorted"},
            {"Reverse Sorted",  "reverse"},
            {"Nearly Sorted",   "nearly"},
            {"Many Duplicates", "dupes"},
        };

        for (String[] typeInfo : types) {
            String label = typeInfo[0];
            System.out.println("\n================================================================");
            System.out.println("SORTING ALGORITHMS COMPARISON - " + label.toUpperCase() + " DATA");
            System.out.println("================================================================");
            System.out.printf("%-10s %-14s %12s %16s %12s%n",
                    "Size", "Algorithm", "Time (ms)", "Comparisons", "Swaps");

            for (int size : SIZES) {
                int[] base = generateData(size, typeInfo[1]);

                // Write sample datasets (only for size=1000 to keep file sizes small)
                if (size == 1_000) {
                    SortingDataGenerator.writeTxt(base,
                            "datasets/sort_" + typeInfo[1] + "_1000.txt");
                }

                for (Sorter sorter : SORTERS) {
                    long totalTime = 0, totalComp = 0, totalSwap = 0;
                    for (int r = 0; r < RUNS; r++) {
                        int[] data = Arrays.copyOf(base, base.length);
                        sorter.reset();
                        long t = System.nanoTime();
                        sorter.sort(data);
                        totalTime += System.nanoTime() - t;
                        totalComp += sorter.comparisons();
                        totalSwap += sorter.swaps();
                    }
                    System.out.printf("%-10d %-14s %12.3f %16d %12d%n",
                            size,
                            sorter.name(),
                            totalTime / (double) RUNS / 1_000_000.0,
                            totalComp / RUNS,
                            totalSwap / RUNS);
                }
            }
        }

        System.out.println("\n================================================================");
        System.out.println("CONCLUSIONS");
        System.out.println("================================================================");
        System.out.println("- QuickSort is fastest on average for random data");
        System.out.println("- InsertionSort is competitive only for n < 1,000");
        System.out.println("- MergeSort provides consistent performance across all data types");
        System.out.println("- QuickSort degrades on reverse-sorted data with naive pivot");
        System.out.println("  (randomised pivot used here to avoid O(n^2) worst case)");
    }

    static int[] generateData(int size, String type) {
        switch (type) {
            case "random":  return SortingDataGenerator.random(size);
            case "sorted":  return SortingDataGenerator.sorted(size);
            case "reverse": return SortingDataGenerator.reverseSorted(size);
            case "nearly":  return SortingDataGenerator.nearlySorted(size);
            case "dupes":   return SortingDataGenerator.manyDuplicates(size);
            default: throw new IllegalArgumentException("Unknown type: " + type);
        }
    }
}
