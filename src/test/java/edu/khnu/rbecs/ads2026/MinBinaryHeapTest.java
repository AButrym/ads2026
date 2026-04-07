package edu.khnu.rbecs.ads2026;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class MinBinaryHeapTest {
    @Test
    void testAddAndGetMin() {
        MinHeap heap = new MinBinaryHeap();
        heap.add(10);
        heap.add(5);
        heap.add(15);
        assertEquals(5, heap.getMin());
    }

    @Test
    void testRemoveMin() {
        MinHeap heap = new MinBinaryHeap();
        heap.add(10);
        heap.add(5);
        heap.add(15);
        assertEquals(5, heap.removeMin());
        assertEquals(10, heap.getMin());
        assertEquals(10, heap.removeMin());
        assertEquals(15, heap.getMin());
    }

    @Test
    void testIsEmptyAndSize() {
        MinHeap heap = new MinBinaryHeap();
        assertTrue(heap.isEmpty());
        assertEquals(0, heap.getSize());

        heap.add(1);
        assertFalse(heap.isEmpty());
        assertEquals(1, heap.getSize());

        heap.removeMin();
        assertTrue(heap.isEmpty());
        assertEquals(0, heap.getSize());
    }

    @Test
    void testSortBehavior() {
        MinHeap heap = new MinBinaryHeap();
        int[] input = {20, 10, 50, 40, 30};
        for (int x : input) {
            heap.add(x);
        }

        int[] expected = {10, 20, 30, 40, 50};
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], heap.removeMin());
        }
        assertTrue(heap.isEmpty());
    }

    @Test
    void testExceptions() {
        MinHeap heap = new MinBinaryHeap();
        assertThrows(NoSuchElementException.class, heap::getMin);
        assertThrows(NoSuchElementException.class, heap::removeMin);
    }

    @Test
    void testHeapifySimple() {
        int[] arr = {10, 5, 15, 7, 2};
        MinHeap heap = MinBinaryHeap.heapify(arr);
        assertEquals(5, heap.getSize());
        assertEquals(2, heap.removeMin());
        assertEquals(5, heap.removeMin());
        assertEquals(7, heap.removeMin());
        assertEquals(10, heap.removeMin());
        assertEquals(15, heap.removeMin());
        assertTrue(heap.isEmpty());
    }

    @Test
    void testHeapifyAlreadySorted() {
        int[] arr = {1, 2, 3, 4, 5};
        MinHeap heap = MinBinaryHeap.heapify(arr);
        assertEquals(5, heap.getSize());
        for (int i = 1; i <= 5; i++) {
            assertEquals(i, heap.removeMin());
        }
    }

    @Test
    void testHeapifyReverseSorted() {
        int[] arr = {5, 4, 3, 2, 1};
        MinHeap heap = MinBinaryHeap.heapify(arr);
        assertEquals(5, heap.getSize());
        for (int i = 1; i <= 5; i++) {
            assertEquals(i, heap.removeMin());
        }
    }

}