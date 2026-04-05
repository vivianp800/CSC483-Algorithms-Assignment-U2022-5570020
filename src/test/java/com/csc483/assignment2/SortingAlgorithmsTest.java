package com.csc483.assignment2;

import com.csc483.assignment2.sorting.*;
import com.csc483.test.Assert;
import com.csc483.test.TestRunner;

import java.util.Arrays;

public class SortingAlgorithmsTest extends TestRunner {

    public static void main(String[] args) {
        new SortingAlgorithmsTest().run();
    }

    // Helper: asserts array is sorted in non-descending order
    private static void assertSorted(int[] a, String label) {
        for (int i = 0; i < a.length - 1; i++) {
            Assert.assertTrue(a[i] <= a[i + 1],
                    label + ": not sorted at index " + i + " (" + a[i] + " > " + a[i + 1] + ")");
        }
    }

    // Helper: runs the same check across all three sorters
    private void forAllSorters(int[] original, String label) {
        Sorter[] sorters = {new InsertionSort(), new MergeSort(), new QuickSort()};
        for (Sorter sorter : sorters) {
            int[] data = Arrays.copyOf(original, original.length);
            sorter.sort(data);
            assertSorted(data, sorter.name() + " / " + label);
        }
    }

    // ------------------------------------------------------------------ Correctness
    public void testSortRandomSmallArray() {
        forAllSorters(new int[]{5, 3, 8, 1, 9, 2, 7, 4, 6, 0}, "random small");
    }

    public void testSortAlreadySorted() {
        forAllSorters(new int[]{1, 2, 3, 4, 5}, "already sorted");
    }

    public void testSortReverseSorted() {
        forAllSorters(new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1}, "reverse sorted");
    }

    public void testSortAllDuplicates() {
        forAllSorters(new int[]{5, 5, 5, 5, 5}, "all duplicates");
    }

    public void testSortSingleElement() {
        forAllSorters(new int[]{42}, "single element");
    }

    public void testSortEmptyArray() {
        forAllSorters(new int[]{}, "empty array");
    }

    public void testSortTwoElements() {
        forAllSorters(new int[]{9, 1}, "two elements");
    }

    public void testSortNegativeNumbers() {
        forAllSorters(new int[]{-3, -1, -7, 0, 2, -5}, "negative numbers");
    }

    public void testSortLargeRandom() {
        forAllSorters(SortingDataGenerator.random(10_000), "large random");
    }

    public void testSortManyDuplicates() {
        forAllSorters(SortingDataGenerator.manyDuplicates(5_000), "many duplicates");
    }

    public void testSortNearlySorted() {
        forAllSorters(SortingDataGenerator.nearlySorted(5_000), "nearly sorted");
    }

    // Result must match Arrays.sort as the reference
    public void testMatchesJavaBuiltin() {
        Sorter[] sorters = {new InsertionSort(), new MergeSort(), new QuickSort()};
        int[] original = SortingDataGenerator.random(500);
        int[] expected = Arrays.copyOf(original, original.length);
        Arrays.sort(expected);

        for (Sorter sorter : sorters) {
            int[] actual = Arrays.copyOf(original, original.length);
            sorter.sort(actual);
            Assert.assertArrayEquals(expected, actual,
                    sorter.name() + " should match Arrays.sort");
        }
    }

    // ------------------------------------------------------------------ Counter behaviour
    public void testCountersAreZeroAfterReset() {
        Sorter[] sorters = {new InsertionSort(), new MergeSort(), new QuickSort()};
        for (Sorter sorter : sorters) {
            sorter.sort(new int[]{3, 1, 2});
            sorter.reset();
            Assert.assertEquals(0L, sorter.comparisons(), sorter.name() + " comparisons after reset");
            Assert.assertEquals(0L, sorter.swaps(),       sorter.name() + " swaps after reset");
        }
    }

    public void testCountersIncrementDuringSort() {
        Sorter[] sorters = {new InsertionSort(), new MergeSort(), new QuickSort()};
        for (Sorter sorter : sorters) {
            sorter.reset();
            sorter.sort(new int[]{5, 3, 8, 1, 9});
            Assert.assertTrue(sorter.comparisons() > 0,
                    sorter.name() + " should record at least one comparison");
        }
    }

    public void testFewerSwapsOnSortedInput() {
        Sorter[] sorters = {new InsertionSort(), new MergeSort(), new QuickSort()};
        int[] random = SortingDataGenerator.random(1_000);
        int[] sorted = SortingDataGenerator.sorted(1_000);

        for (Sorter sorter : sorters) {
            sorter.reset();
            sorter.sort(Arrays.copyOf(random, random.length));
            long swapsRandom = sorter.swaps();

            sorter.reset();
            sorter.sort(Arrays.copyOf(sorted, sorted.length));
            long swapsSorted = sorter.swaps();

            Assert.assertTrue(swapsSorted <= swapsRandom,
                    sorter.name() + ": sorted input should not require more swaps than random"
                            + " (sorted=" + swapsSorted + ", random=" + swapsRandom + ")");
        }
    }

    // ------------------------------------------------------------------ Data generator
    public void testDataGeneratorRandomHasCorrectSize() {
        Assert.assertEquals(500, SortingDataGenerator.random(500).length, "random size");
    }

    public void testDataGeneratorSortedIsAscending() {
        assertSorted(SortingDataGenerator.sorted(100), "sorted generator");
    }

    public void testDataGeneratorReverseSortedIsDescending() {
        int[] data = SortingDataGenerator.reverseSorted(100);
        for (int i = 0; i < data.length - 1; i++) {
            Assert.assertTrue(data[i] >= data[i + 1],
                    "reverse sorted: not descending at index " + i);
        }
    }

    public void testDataGeneratorManyDuplicatesHasFewDistinctValues() {
        int[] data = SortingDataGenerator.manyDuplicates(1_000);
        long distinct = Arrays.stream(data).distinct().count();
        Assert.assertTrue(distinct <= 10,
                "manyDuplicates should have at most 10 distinct values, got " + distinct);
    }

    public void testDataGeneratorNearlySortedHasCorrectSize() {
        Assert.assertEquals(200, SortingDataGenerator.nearlySorted(200).length, "nearly sorted size");
    }
}
