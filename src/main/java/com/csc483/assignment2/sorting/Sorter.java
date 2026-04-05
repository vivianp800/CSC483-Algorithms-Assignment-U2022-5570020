package com.csc483.assignment2.sorting;

public interface Sorter {
    void sort(int[] data);
    long comparisons();
    long swaps();
    void reset();
    String name();
}
