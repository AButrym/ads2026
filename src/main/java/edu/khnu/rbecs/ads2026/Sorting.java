package edu.khnu.rbecs.ads2026;

public class Sorting {

    void main() {
        int[] arr = {1, 3, 7, 31, 201, 800, 1003};
        int key = 31;
        int ix = binarySearch(key, arr);
        if (ix < 0) {
            System.out.println("Not found");
        } else {
            System.out.println("Found at index " + ix + " : " + arr[ix]);
        }
    }

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
        // TODO
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

    public static int bubbleSort(int[] arr) {
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
}
