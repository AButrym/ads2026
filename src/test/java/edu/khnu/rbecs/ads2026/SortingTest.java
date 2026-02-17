package edu.khnu.rbecs.ads2026;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SortingTest {
    @Test
    @DisplayName("Binary search finds existing element in middle position")
    void test01() {
        // AAA = Arrange, Act, Assert
        // Arrange
        int[] arr = {1, 3, 7, 31, 201, 800, 1003};
        int key = 31;
        int expectedPosition = 3;
        // Act
        int ix = Sorting.binarySearch(key, arr);
        // Assert
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
    /*
*     void main() {
        int[] arr = {1, 3, 7, 31, 201, 800, 1003};
        int key = 31;
        int ix = binarySearch(key, arr);
        if (ix < 0) {
            System.out.println("Not found");
        } else {
            System.out.println("Found at index " + ix + " : " + arr[ix]);
        }
    }*/
}