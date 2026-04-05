package com.csc483.assignment2.sorting;

import java.util.Random;

public class QuickSort implements Sorter {

    private long comp, swap;
    private static final Random RAND = new Random(42);

    @Override public String name()         { return "QuickSort"; }
    @Override public long   comparisons()  { return comp; }
    @Override public long   swaps()        { return swap; }
    @Override public void   reset()        { comp = swap = 0; }

    @Override
    public void sort(int[] a) {
        quickSort(a, 0, a.length - 1);
    }

    private void quickSort(int[] a, int lo, int hi) {
        if (lo >= hi) return;
        int p = partition(a, lo, hi);
        quickSort(a, lo,    p - 1);
        quickSort(a, p + 1, hi);
    }

    // Randomised pivot avoids O(n^2) worst case on sorted/reverse-sorted input.
    private int partition(int[] a, int lo, int hi) {
        int pivotIdx = lo + RAND.nextInt(hi - lo + 1);
        swap(a, pivotIdx, hi);
        int pivot = a[hi];
        int i     = lo - 1;

        for (int j = lo; j < hi; j++) {
            comp++;
            if (a[j] <= pivot) {
                i++;
                swap(a, i, j);
            }
        }
        swap(a, i + 1, hi);
        return i + 1;
    }

    private void swap(int[] a, int i, int j) {
        if (i == j) return;
        int tmp = a[i]; a[i] = a[j]; a[j] = tmp;
        swap++;
    }
}
