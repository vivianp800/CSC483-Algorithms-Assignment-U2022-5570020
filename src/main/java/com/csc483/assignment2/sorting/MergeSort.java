package com.csc483.assignment2.sorting;

import java.util.Arrays;

public class MergeSort implements Sorter {

    private long comp, swap;

    @Override public String name()         { return "MergeSort"; }
    @Override public long   comparisons()  { return comp; }
    @Override public long   swaps()        { return swap; }
    @Override public void   reset()        { comp = swap = 0; }

    @Override
    public void sort(int[] a) {
        mergeSort(a, 0, a.length - 1);
    }

    private void mergeSort(int[] a, int left, int right) {
        if (left >= right) return;
        int mid = left + (right - left) / 2;
        mergeSort(a, left,    mid);
        mergeSort(a, mid + 1, right);
        merge(a, left, mid, right);
    }

    private void merge(int[] a, int left, int mid, int right) {
        int[] tmp = Arrays.copyOfRange(a, left, right + 1);
        int i = 0, j = mid - left + 1, k = left;

        while (i <= mid - left && j <= right - left) {
            comp++;
            if (tmp[i] <= tmp[j]) {
                a[k++] = tmp[i++];
            } else {
                a[k++] = tmp[j++];
                swap++;
            }
        }
        while (i <= mid - left)  a[k++] = tmp[i++];
        while (j <= right - left) a[k++] = tmp[j++];
    }
}
