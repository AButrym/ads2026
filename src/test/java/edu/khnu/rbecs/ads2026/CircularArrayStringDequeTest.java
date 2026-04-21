package edu.khnu.rbecs.ads2026;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class CircularArrayStringDequeTest {
    private CircularArrayStringDeque deque;

    @BeforeEach
    void setUp() {
        deque = new CircularArrayStringDeque();
    }

    @Test
    void testAddLast() {
        deque.addLast("1");
        deque.addLast("2");
        assertEquals(2, deque.size());
        assertEquals("1", deque.peekFirst());
        assertEquals("2", deque.peekLast());
    }

    @Test
    void testAddFirst() {
        deque.addFirst("1");
        deque.addFirst("2");
        assertEquals(2, deque.size());
        assertEquals("2", deque.peekFirst());
        assertEquals("1", deque.peekLast());
    }

    @Test
    void testRemoveFirst() {
        deque.addLast("1");
        deque.addLast("2");
        assertEquals("1", deque.removeFirst());
        assertEquals(1, deque.size());
        assertEquals("2", deque.peekFirst());
    }

    @Test
    void testRemoveLast() {
        deque.addLast("1");
        deque.addLast("2");
        assertEquals("2", deque.removeLast());
        assertEquals(1, deque.size());
        assertEquals("1", deque.peekLast());
    }

    @Test
    void testCircularBehavior() {
        // Initial capacity is 8
        for (int i = 0; i < 8; i++) {
            deque.addLast(String.valueOf(i));
        }
        // Deque is full: [0, 1, 2, 3, 4, 5, 6, 7], front=0, rear=0 (after wrap) or 8
        // Let's check removeFirst to move front
        assertEquals("0", deque.removeFirst());
        assertEquals("1", deque.removeFirst());
        // front=2, size=6, rear=8 (or 0)
        deque.addLast("8");
        deque.addLast("9");
        // rear should wrap: data[0]="8", data[1]="9"
        assertEquals(8, deque.size());
        assertEquals("2", deque.get(0));
        assertEquals("9", deque.get(7));
    }

    @Test
    void testResizeWithWrap() {
        // Initial capacity 8
        for (int i = 0; i < 4; i++) {
            deque.addLast(String.valueOf(i));
        }
        // [0, 1, 2, 3, null, null, null, null], front=0, rear=4
        for (int i = 0; i < 2; i++) {
            deque.removeFirst();
        }
        // [null, null, 2, 3, null, null, null, null], front=2, rear=4
        for (int i = 4; i < 10; i++) {
            deque.addLast(String.valueOf(i));
        }
        // Should trigger resize during this loop
        assertEquals(8, deque.size());
        assertEquals("2", deque.get(0));
        assertEquals("9", deque.get(7));
    }

    @Test
    void testIterator() {
        deque.addLast("A");
        deque.addFirst("B");
        deque.addLast("C");
        // B, A, C
        StringIterator it = deque.iterator();
        assertTrue(it.hasNext());
        assertEquals("B", it.next());
        assertTrue(it.hasNext());
        assertEquals("A", it.next());
        assertTrue(it.hasNext());
        assertEquals("C", it.next());
        assertFalse(it.hasNext());
    }

    @Test
    void testListOperations() {
        deque.addLast("A");
        deque.addLast("B");
        deque.addLast("C");
        assertEquals("B", deque.get(1));
        deque.set(1, "X");
        assertEquals("X", deque.get(1));
        assertEquals(1, deque.indexOf("X"));
        assertTrue(deque.contains("A"));
        assertFalse(deque.contains("B"));
    }

    @Test
    void testEmptyDeque() {
        assertTrue(deque.isEmpty());
        assertThrows(NoSuchElementException.class, deque::removeFirst);
        assertThrows(NoSuchElementException.class, deque::removeLast);
        assertThrows(NoSuchElementException.class, deque::peekFirst);
        assertThrows(NoSuchElementException.class, deque::peekLast);
    }
    @Test
    void testRemoveAt() {
        deque.addLast("A");
        deque.addLast("B");
        deque.addLast("C");
        deque.addLast("D");
        // [A, B, C, D]
        assertEquals("B", deque.removeAt(1));
        // [A, C, D]
        assertEquals(3, deque.size());
        assertEquals("A", deque.get(0));
        assertEquals("C", deque.get(1));
        assertEquals("D", deque.get(2));

        // Test removeAt with wrap
        deque.removeFirst(); // C, D
        deque.addLast("E");
        deque.addLast("F");
        deque.addLast("G");
        deque.addLast("H");
        deque.addLast("I");
        deque.addLast("J");
        // front should be 1, size 8, capacity 8. data[1]=C ... data[0]=J
        assertEquals("I", deque.removeAt(6)); // J is at 7
        assertEquals(7, deque.size());
        assertEquals("J", deque.get(6));
    }
}
