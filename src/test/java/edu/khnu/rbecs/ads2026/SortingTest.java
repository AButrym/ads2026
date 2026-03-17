package edu.khnu.rbecs.ads2026;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static edu.khnu.rbecs.ads2026.Sorting.*;
import static org.junit.jupiter.api.Assertions.*;

class SortingTest {
    static class BinarySearchTests {

        @Test
        @DisplayName("Binary search finds existing element in middle position")
        void test01() {
            // AAA = Arrange, Act, Assert
            // Given When Then
            // Arrange Given
            int[] arr = {1, 3, 7, 31, 201, 800, 1003};
            int key = 31;
            int expectedPosition = 3;
            // Act     When
            int ix = Sorting.binarySearch(key, arr);
            // Assert  Then
            assertEquals(expectedPosition, ix);
        }

        @Test
        @DisplayName("Binary search finds existing element in last position")
        void test02() {
            // AAA = Arrange, Act, Assert
            // Arrange
            int[] arr = {1, 3, 7, 31, 201, 800, 1003};
            int key = 1003;
            int expectedPosition = 6;
            // Act
            int ix = Sorting.binarySearch(key, arr);
            // Assert
            assertEquals(expectedPosition, ix);
        }

        @Test
        @DisplayName("Binary search finds existing element in first position")
        void test03() {
            // AAA = Arrange, Act, Assert
            // Arrange
            int[] arr = {1, 3, 7, 31, 201, 800, 1003};
            int key = 1;
            int expectedPosition = 0;
            // Act
            int ix = Sorting.binarySearch(key, arr);
            // Assert
            assertEquals(expectedPosition, ix);
        }

        @Test
        @DisplayName("Binary search doesn't finds absent element in middle position")
        void test04() {
            // AAA = Arrange, Act, Assert
            // Arrange
            int[] arr = {1, 3, 7, 31, 201, 800, 1003};
            int key = 8;
            // Act
            int ix = Sorting.binarySearch(key, arr);
            // Assert
            assertTrue(ix < 0);
        }

        @Test
        @DisplayName("binarySearchLeft finds left boundary for existing elements")
        void binarySearchLeft01() {
            int[] sortedArr = {1, 2, 3, 3, 3, 4, 5};
            int key = 3;
            int expectedPosition = 2;

            int ix = Sorting.binarySearchLeft(key, sortedArr);

            assertEquals(expectedPosition, ix);
        }

        @Test
        @DisplayName("binarySearchLeft finds left boundary for non existing elements")
        void binarySearchLeft02() {
            int[] sortedArr = {1, 2, 4, 5};
            int key = 3;
            int expectedPosition = 2;

            int ix = Sorting.binarySearchLeft(key, sortedArr);

            assertEquals(expectedPosition, ix);
        }
    }

    static class BubbleSortTests {

        @Test
        @DisplayName("insertionSort sorts an already sorted array and returns 0 inversions")
        void insertionSortAlreadySortedArray() {
            int[] sortedArr = {1, 2, 3, 4, 5};
            int expectedInversions = 0;
            int[] expected = {1, 2, 3, 4, 5};

            int inversions = insertionSort(sortedArr);

            assertArrayEquals(expected, sortedArr);
            assertEquals(expectedInversions, inversions);
        }

        @Test
        @DisplayName("insertionSort sorts a reverse-sorted array and counts inversions")
        void insertionSortReverseSortedArray() {
            int[] reverseSortedArr = {5, 4, 3, 2, 1};
            int expectedInversions = 10;
            int[] expected = {1, 2, 3, 4, 5};

            int inversions = insertionSort(reverseSortedArr);

            assertArrayEquals(expected, reverseSortedArr);
            assertEquals(expectedInversions, inversions);
        }

        @Test
        @DisplayName("insertionSort sorts an unsorted array correctly")
        void insertionSortUnsortedArray() {
            int[] unsortedArr = {3, 5, 1, 4, 2};
            int expectedInversions = 6;
            int[] expected = {1, 2, 3, 4, 5};

            int inversions = insertionSort(unsortedArr);

            assertArrayEquals(expected, unsortedArr);
            assertEquals(expectedInversions, inversions);
        }

        @Test
        @DisplayName("insertionSort handles an array with duplicate values")
        void insertionSortArrayWithDuplicates() {
            int[] arrWithDuplicates = {4, 3, 3, 2, 1};
            int expectedInversions = 9;
            int[] expected = {1, 2, 3, 3, 4};

            int inversions = insertionSort(arrWithDuplicates);

            assertArrayEquals(expected, arrWithDuplicates);
            assertEquals(expectedInversions, inversions);
        }

        @Test
        @DisplayName("insertionSort handles an empty array")
        void insertionSortEmptyArray() {
            int[] emptyArr = {};
            int expectedInversions = 0;
            int[] expected = {};

            int inversions = insertionSort(emptyArr);

            assertArrayEquals(expected, emptyArr);
            assertEquals(expectedInversions, inversions);
        }

        @RepeatedTest(100)
        void randomizedBubleSort() {
            int n = 20;
            int[] arr = new int[n];
            for (int i = 1; i < n; i++) {
                arr[i] = i;
            }
            int[] expected = arr.clone();
            int[] arrCopy = arr.clone();
            Sorting.shuffle(arr);
            int expectedInversions = inversionsBruteForce(arr);

            int inversions = bubleSort(arr);

            assertArrayEquals(expected, arr, () -> Arrays.toString(arrCopy));
            assertEquals(expectedInversions, inversions, () -> Arrays.toString(arrCopy));
        }
    }

    static class InsertionSortTests {

        @Test
        @DisplayName("insertionSort sorts an already sorted array and returns 0 inversions")
        void insertionSortAlreadySortedArray() {
            int[] sortedArr = {1, 2, 3, 4, 5};
            int expectedInversions = 0;
            int[] expected = {1, 2, 3, 4, 5};

            int inversions = insertionSort(sortedArr);

            assertArrayEquals(expected, sortedArr);
            assertEquals(expectedInversions, inversions);
        }

        @Test
        @DisplayName("insertionSort sorts a reverse-sorted array and counts inversions")
        void test1_insertionSort() {
            int[] reverseSortedArr = {5, 4, 3, 2, 1};
            int expectedInversions = 10;
            int[] expected = {1, 2, 3, 4, 5};

            int inversions = insertionSort(reverseSortedArr);

            assertArrayEquals(expected, reverseSortedArr);
            assertEquals(expectedInversions, inversions);
        }

        @Test
        @DisplayName("insertionSort sorts an unsorted array correctly")
        void test2_insertionSort() {
            int[] unsortedArr = {3, 5, 1, 4, 2};
            int expectedInversions = 6;
            int[] expected = {1, 2, 3, 4, 5};

            int inversions = insertionSort(unsortedArr);

            assertArrayEquals(expected, unsortedArr);
            assertEquals(expectedInversions, inversions);
        }

        @Test
        @DisplayName("insertionSort handles an array with duplicate values")
        void test3_insertionSort() {
            int[] arrWithDuplicates = {4, 3, 3, 2, 1};
            int expectedInversions = 9;
            int[] expected = {1, 2, 3, 3, 4};

            int inversions = insertionSort(arrWithDuplicates);

            assertArrayEquals(expected, arrWithDuplicates);
            assertEquals(expectedInversions, inversions);
        }

        @Test
        @DisplayName("insertionSort handles an empty array")
        void test4_insertionSort() {
            int[] emptyArr = {};
            int expectedInversions = 0;
            int[] expected = {};

            int inversions = insertionSort(emptyArr);

            assertArrayEquals(expected, emptyArr);
            assertEquals(expectedInversions, inversions);
        }

        @RepeatedTest(100)
        void randomizedInsertionSort() {
            int n = 20;
            int[] arr = new int[n];
            for (int i = 1; i < n; i++) {
                arr[i] = i;
            }
            int[] expected = arr.clone();
            int[] arrCopy = arr.clone();
            Sorting.shuffle(arr);
            int expectedInversions = inversionsBruteForce(arr);

            int inversions = insertionSort(arr);

            assertArrayEquals(expected, arr, () -> Arrays.toString(arrCopy));
            assertEquals(expectedInversions, inversions, () -> Arrays.toString(arrCopy));
        }
    }
}