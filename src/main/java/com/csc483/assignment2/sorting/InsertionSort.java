package com.csc483.assignment2.sorting;

public class InsertionSort implements Sorter {

    private long comp, swap;

    @Override public String name()         { return "InsertionSort"; }
    @Override public long   comparisons()  { return comp; }
    @Override public long   swaps()        { return swap; }
    @Override public void   reset()        { comp = swap = 0; }

    @Override
    public void sort(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int key = a[i];
            int j   = i - 1;
            while (j >= 0 && a[j] > key) {
                comp++;
                swap++;
                a[j + 1] = a[j];
                j--;
            }
            comp++; // the comparison that broke the loop (or the initial check when j < 0)
            a[j + 1] = key;
        }
    }
}
