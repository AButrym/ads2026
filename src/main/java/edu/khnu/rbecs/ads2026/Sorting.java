package edu.khnu.rbecs.ads2026;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class Sorting {
    static final Random RND = new Random(42);

    public static int binarySearch(int key, int[] arr) {
        return binarySearch(key, arr, 0, arr.length - 1);
    }

    static int binarySearch(int key, int[] arr, int left, int right) {
        if (left > right) return -1;
//        int mid = (left + right) / 2;
        int mid = left + (right - left) / 2;
        if (arr[mid] == key) return mid;
        if (arr[mid] > key) return binarySearch(key, arr, left, mid - 1);
        return binarySearch(key, arr, mid + 1, right);
    }

    static int binarySearchLeft(int key, int[] arr) {
        int left = 0;
        int right = arr.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] < key) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    static void shuffle(int[] arr) {
        shuffle(arr, System.nanoTime());
    }

    static void shuffle(int[] arr, long seed) {
        var rnd = new Random(seed);
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int j = rnd.nextInt(i, n);
            swap(arr, i, j);
        }
    }

    static int inversionsBruteForce(int[] arr) {
        int n = arr.length;
        int nInversions = 0;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (arr[i] > arr[j]) {
                    nInversions++;
                }
            }
        }
        return nInversions;
    }

    public static int bubleSort(int[] arr) {
        int n = arr.length;
        int nInversions = 0;
        boolean swapped = true;
        for (int i = 0; i < n - 1 && swapped; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    swapped = true;
                    nInversions++;
                }
            }
        }
        return nInversions;
    }

    public static int insertionSort(int[] arr) {
        int n = arr.length;
        int nInversions = 0;
        for (int i = 1; i < n; i++) {
            for (int j = i; j > 0; j--) {
                if (arr[j] < arr[j - 1]) {
                    swap(arr, j, j - 1);
                    nInversions++;
                } else {
                    break;
                }
            }
        }
        return nInversions;
    }

    record SortResult(int[] sorted, long nInversions) {
    }

    static SortResult mergeSort(int[] arr) {
        AtomicLong nInversions = new AtomicLong();
        int[] sorted = mergeSort(arr, nInversions);
        return new SortResult(sorted, nInversions.get());
    }

    static int[] mergeSort(int[] arr, AtomicLong nInversions) {
        if (arr.length <= 1) return arr;
        int mid = arr.length / 2;
        int[] left = Arrays.copyOfRange(arr, 0, mid);
        int[] right = Arrays.copyOfRange(arr, mid, arr.length);
        return merge(mergeSort(left, nInversions),
                mergeSort(right, nInversions),
                nInversions);
    }

    static int[] merge(int[] arr1, int[] arr2, AtomicLong nInversions) {
        int[] res = new int[arr1.length + arr2.length];
        int i = 0, j = 0, k = 0;
        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] <= arr2[j]) {
                res[k++] = arr1[i++];
            } else {
                res[k++] = arr2[j++];
                nInversions.getAndAdd(arr1.length - i);
            }
        }
        while (i < arr1.length) {
            res[k++] = arr1[i++];
        }
        while (j < arr2.length) {
            res[k++] = arr2[j++];
        }
        return res;
    }

    static void quickSort(int[] arr) {
//        shuffle(arr);
        quickSort2(arr, 0, arr.length);
    }

    static void quickSort(int[] arr, int from, int toExclusive) {
        if (from >= toExclusive) return;
        int mid = twoWayPartition(arr, from, toExclusive);
        quickSort(arr, from, mid);
        quickSort(arr, mid + 1, toExclusive);
    }

    static void quickSort2(int[] arr, int from, int toExclusive) {
        if (from >= toExclusive) return;
        Range mid = threeWayPartition(arr, from, toExclusive);
        quickSort2(arr, from, mid.from);
        quickSort2(arr, mid.toExclusive, toExclusive);
    }

    record Range(int from, int toExclusive) {}

    static Range threeWayPartition(int[] arr, int from, int toExclusive) {
        if (from >= toExclusive) return new Range(from, from + 1);
        int pivotPos = RND.nextInt(from, toExclusive);
        swap(arr, pivotPos, from);
        int pivot = arr[from];
        int left = from;
        int mid = from + 1;
        int right = toExclusive - 1;
        while (mid <= right) {
            if (arr[mid] == pivot) mid++;
            else if (arr[mid] < pivot) {
                swap(arr, left, mid);
                left++;
                mid++;
            } else {
                swap(arr, mid, right);
                right--;
            }
        }
        return new Range(left, mid);
    }

    static int twoWayPartition(int[] arr, int from, int toExclusive) {
        if (from >= toExclusive) return from;
        int pivotPos = RND.nextInt(from, toExclusive);
        swap(arr, pivotPos, from);
        int pivot = arr[from];
        int left = from + 1;
        int right = toExclusive - 1;
        while (left <= right) {
            if (arr[left] <= pivot) left++;
            else {
                swap(arr, left, right);
                right--;
            }
        }
        swap(arr, from, right);
        return right;
    }

    public static int[] sortedByHeap(int[] arr) {
        MinHeap heap = MinBinaryHeap.heapify(arr);
        int[] sorted = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            sorted[i] = heap.removeMin();
        }
        return sorted;
    }

    static void main3() {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        shuffle(arr);
        System.out.println(Arrays.toString(arr));
//        int mid = twoWayPartition(arr, 0, arr.length);
        quickSort(arr);
        System.out.println(Arrays.toString(arr));
//        System.out.println("mid = " + mid);
    }

    static void main() {
        int n = 10_000_000;
        System.out.println("n = " + n);
        int[] arr = new int[n];
        for (int i = 0; i < n/2; i++) {
            arr[i] = i;
        }
        Arrays.fill(arr, n/2, n, n/2);
        int[] original = arr.clone();
        long t0 = System.nanoTime();
        shuffle(arr);
        long t1 = System.nanoTime();
        System.out.println("Shuffle time: " + (t1 - t0) / 1e6 + "ms");

        int[] arr1 = arr.clone();
        long t2 = System.nanoTime();
        quickSort(arr1);
        long t3 = System.nanoTime();
        System.out.println("QuickSort sort time: " + (t3 - t2) / 1e6 + "ms");
        System.out.println(Arrays.equals(original, arr1));

        int[] arr2 = arr.clone();
        long t4 = System.nanoTime();
        Arrays.sort(arr2);
        long t5 = System.nanoTime();
        System.out.println("Lib QuickSort sort time: " + (t5 - t4) / 1e6 + "ms");
        System.out.println(Arrays.equals(original, arr2));

        int[] arr3 = arr.clone();
        long t6 = System.nanoTime();
        var res = mergeSort(arr3);
        long t7 = System.nanoTime();
        long nInversions3 = res.nInversions;
        int[] sorted = res.sorted;
        System.out.println("Merge sort time: " + (t7 - t6) / 1e6 + "ms");
        System.out.println("Inversions: " + nInversions3);
        System.out.println(Arrays.equals(original, sorted));
    }

    void main1() {
        for (int i = 0; i < 5; i++) {
            // warmup
            int[] fake = new int[2000];
            shuffle(fake);
            bubleSort(fake);
            insertionSort(fake);
        }
        int n = 10_000;
        for (int iter = 0; iter < 4; iter++, n *= 2) {
            System.out.println("n = " + n);
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = i;
            }
            int[] original = arr.clone();

            long t0 = System.nanoTime();
            shuffle(arr);
            long t1 = System.nanoTime();
            System.out.println("Shuffle time: " + (t1 - t0) / 1e6 + "ms");

            int[] arr1 = arr.clone();
            long t2 = System.nanoTime();
            int nInversions1 = bubleSort(arr1);
            long t3 = System.nanoTime();
            System.out.println("Buble sort time: " + (t3 - t2) / 1e6 + "ms");

            int[] arr2 = arr.clone();
            long t4 = System.nanoTime();
            int nInversions2 = insertionSort(arr2);
            long t5 = System.nanoTime();
            System.out.println("Insertion sort time: " + (t5 - t4) / 1e6 + "ms");

            int[] arr3 = arr.clone();
            long t6 = System.nanoTime();
            var res = mergeSort(arr3);
            long t7 = System.nanoTime();
            long nInversions3 = res.nInversions;
            int[] sorted = res.sorted;
            System.out.println("Merge sort time: " + (t7 - t6) / 1e6 + "ms");

            System.out.println("Inversions: " + nInversions1 + ", " + nInversions2 + ", " + nInversions3);
            System.out.println(Arrays.equals(original, arr1));
            System.out.println(Arrays.equals(original, arr2));
            System.out.println(Arrays.equals(original, sorted));
        }
    }
}

class MergeSorter {
    private int nInversions = 0;
    private int[] res;

    public int getNInversions() {
        return nInversions;
    }

    public int[] sorted() {
        return res;
    }

    public MergeSorter(int[] arr) {
    }

    public void merge(int[] arr, int left, int mid, int right) {
    }
}