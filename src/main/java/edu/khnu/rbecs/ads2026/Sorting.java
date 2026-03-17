package edu.khnu.rbecs.ads2026;

import java.util.Arrays;
import java.util.Random;

public class Sorting {

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

    void main() {
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
            int[] arr2 = arr.clone();
            long t2 = System.nanoTime();
            int nInversions1 = bubleSort(arr);
            long t3 = System.nanoTime();
            System.out.println("Buble sort time: " + (t3 - t2) / 1e6 + "ms");
            long t4 = System.nanoTime();
            int nInversions2 = insertionSort(arr2);
            long t5 = System.nanoTime();
            System.out.println("Insertion sort time: " + (t5 - t4) / 1e6 + "ms");
            System.out.println("Inversions: " + nInversions1 + ", " + nInversions2);
            System.out.println(Arrays.equals(original, arr));
            System.out.println(Arrays.equals(original, arr2));
        }
    }
}
