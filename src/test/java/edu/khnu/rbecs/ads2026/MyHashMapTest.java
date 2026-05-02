package edu.khnu.rbecs.ads2026;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class MyHashMapTest {
    private MyMap<String, Integer> map;

    @BeforeEach
    void setUp() {
        map = new MyHashMap<>();
    }

    @Test
    void testPutAndGet() {
        assertNull(map.put("one", 1));
        assertEquals(1, map.get("one"));
        assertEquals(1, map.size());
    }

    @Test
    void testPutUpdate() {
        map.put("one", 1);
        assertEquals(1, map.put("one", 11));
        assertEquals(11, map.get("one"));
        assertEquals(1, map.size());
    }

    @Test
    void testGetNonExistent() {
        assertNull(map.get("two"));
    }

    @Test
    void testPutNullValue() {
        assertThrows(NullPointerException.class, () -> map.put("key", null));
    }

    @Test
    void testSize() {
        assertEquals(0, map.size());
        map.put("a", 1);
        map.put("b", 2);
        assertEquals(2, map.size());
    }

    @Test
    void testIterator() {
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);

        int count = 0;
        boolean foundA = false, foundB = false, foundC = false;

        for (MyMap.Entry<String, Integer> entry : map) {
            count++;
            if (entry.getKey().equals("a")) {
                assertEquals(1, entry.getValue());
                foundA = true;
            } else if (entry.getKey().equals("b")) {
                assertEquals(2, entry.getValue());
                foundB = true;
            } else if (entry.getKey().equals("c")) {
                assertEquals(3, entry.getValue());
                foundC = true;
            }
        }

        assertEquals(3, count);
        assertTrue(foundA);
        assertTrue(foundB);
        assertTrue(foundC);
    }

    @Test
    void testIteratorNoSuchElementException() {
        Iterator<MyMap.Entry<String, Integer>> it = map.iterator();
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, it::next);
    }

    @Test
    void testCollision() {
        // "Aa" and "BB" have the same hashCode in Java
        String key1 = "Aa";
        String key2 = "BB";
        assertEquals(key1.hashCode(), key2.hashCode());

        map.put(key1, 1);
        map.put(key2, 2);

        assertEquals(1, map.get(key1));
        assertEquals(2, map.get(key2));
        assertEquals(2, map.size());
    }
}
