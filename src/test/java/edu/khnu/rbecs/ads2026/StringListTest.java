package edu.khnu.rbecs.ads2026;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StringListTest {
    @Nested
    class ArrayStringListTest {
        private StringList list;

        @BeforeEach
        void setUp() {
            list = new ArrayStringList();
        }

        @Test
        void testAddAndSize() {
            list.add("one");
            list.add("two");
            assertEquals(2, list.size());
        }

        @Test
        void testGet() {
            list.add("first");
            list.add("second");
            assertEquals("first", list.get(0));
            assertEquals("second", list.get(1));
        }

        @Test
        void testSet() {
            list.add("old");
            String old = list.set(0, "new");
            assertEquals("old", old);
            assertEquals("new", list.get(0));
        }

        @Test
        void testRemoveAt() {
            list.add("a");
            list.add("b");
            list.add("c");
            String removed = list.removeAt(1);
            assertEquals("b", removed);
            assertEquals(2, list.size());
            assertEquals("c", list.get(1));
        }

        @Test
        void testIndexOf() {
            list.add("apple");
            list.add("banana");
            assertEquals(0, list.indexOf("apple"));
            assertEquals(1, list.indexOf("banana"));
            assertEquals(-1, list.indexOf("cherry"));
        }

        @Test
        void testIsEmpty() {
            assertTrue(list.isEmpty());
            list.add("something");
            assertFalse(list.isEmpty());
        }

        @Test
        void testContains() {
            list.add("hello");
            assertTrue(list.contains("hello"));
            assertFalse(list.contains("world"));
        }

        @Test
        void testRemoveByValue() {
            list.add("to remove");
            list.add("keep");
            assertTrue(list.remove("to remove"));
            assertFalse(list.remove("not present"));
            assertEquals(1, list.size());
            assertEquals("keep", list.get(0));
        }

        @Test
        void testOutOfBoundsException() {
            list.add("only");
            assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
            assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
            assertThrows(IndexOutOfBoundsException.class, () -> list.removeAt(1));
        }

        @Test
        void testGrowCapacity() {
            for (int i = 0; i < 20; i++) {
                list.add("item" + i);
            }
            assertEquals(20, list.size());
            assertEquals("item0", list.get(0));
            assertEquals("item19", list.get(19));
        }

        @Test
        void testIterator() {
            list.add("one");
            list.add("two");
            list.add("three");

            StringIterator iterator = list.iterator();
            assertTrue(iterator.hasNext());
            assertEquals("one", iterator.next());
            assertTrue(iterator.hasNext());
            assertEquals("two", iterator.next());
            assertTrue(iterator.hasNext());
            assertEquals("three", iterator.next());
            assertFalse(iterator.hasNext());
        }

        @Test
        void testEmptyListIterator() {
            StringIterator iterator = list.iterator();
            assertFalse(iterator.hasNext());
        }
    }

    @Nested
    class LinkedListStringListTest {
        private StringList list;

        @BeforeEach
        void setUp() {
            list = new LinkedListStringList();
        }

        @Test
        void testAddAndSize() {
            list.add("one");
            list.add("two");
            assertEquals(2, list.size());
        }

        @Test
        void testGet() {
            list.add("first");
            list.add("second");
            assertEquals("first", list.get(0));
            assertEquals("second", list.get(1));
        }

        @Test
        void testSet() {
            list.add("old");
            String old = list.set(0, "new");
            assertEquals("old", old);
            assertEquals("new", list.get(0));
        }

        @Test
        void testRemoveAt() {
            list.add("a");
            list.add("b");
            list.add("c");
            String removed = list.removeAt(1);
            assertEquals("b", removed);
            assertEquals(2, list.size());
            assertEquals("c", list.get(1));
        }

        @Test
        void testIndexOf() {
            list.add("apple");
            list.add("banana");
            assertEquals(0, list.indexOf("apple"));
            assertEquals(1, list.indexOf("banana"));
            assertEquals(-1, list.indexOf("cherry"));
        }

        @Test
        void testIsEmpty() {
            assertTrue(list.isEmpty());
            list.add("something");
            assertFalse(list.isEmpty());
        }

        @Test
        void testContains() {
            list.add("hello");
            assertTrue(list.contains("hello"));
            assertFalse(list.contains("world"));
        }

        @Test
        void testRemoveByValue() {
            list.add("to remove");
            list.add("keep");
            assertTrue(list.remove("to remove"));
            assertFalse(list.remove("not present"));
            assertEquals(1, list.size());
            assertEquals("keep", list.get(0));
        }

        @Test
        void testOutOfBoundsException() {
            list.add("only");
            assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
            assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
            assertThrows(IndexOutOfBoundsException.class, () -> list.removeAt(1));
        }

        @Test
        void testGrowCapacity() {
            for (int i = 0; i < 20; i++) {
                list.add("item" + i);
            }
            assertEquals(20, list.size());
            assertEquals("item0", list.get(0));
            assertEquals("item19", list.get(19));
        }

        @Test
        void testIterator() {
            list.add("one");
            list.add("two");
            list.add("three");

            StringIterator iterator = list.iterator();
            assertTrue(iterator.hasNext());
            assertEquals("one", iterator.next());
            assertTrue(iterator.hasNext());
            assertEquals("two", iterator.next());
            assertTrue(iterator.hasNext());
            assertEquals("three", iterator.next());
            assertFalse(iterator.hasNext());
        }

        @Test
        void testEmptyListIterator() {
            StringIterator iterator = list.iterator();
            assertFalse(iterator.hasNext());
        }
    }
}