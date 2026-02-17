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
}
