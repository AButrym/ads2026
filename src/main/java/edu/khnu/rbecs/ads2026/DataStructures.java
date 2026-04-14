package edu.khnu.rbecs.ads2026;

import java.util.Arrays;

public class DataStructures {
    static void main() {

    }
}

interface StringIterable {
    StringIterator iterator();
}

interface StringIterator {
    boolean hasNext();
    String next();
}

interface StringList extends StringIterable {
    // CRUD - Create, Read, Update, Delete
    void add(String s);
    String get(int index);
    String set(int index, String s);
    String removeAt(int index);
    int indexOf(String s);
    int size();

    default boolean isEmpty() {
        return size() == 0;
    }
    default boolean contains(String s) {
        return indexOf(s) != -1;
    }
    default boolean remove(String s) {
        int index = indexOf(s);
        if (index == -1) return false;
        removeAt(index);
        return true;
    }
}

abstract class AbstractStringList implements StringList {
    @Override
    public int indexOf(String s) {
        for (int i = 0; i < size(); i++) {
            if (get(i).equals(s)) return i;
        }
        return -1;
    }
    protected void checkIndex(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for size " + size());
        }
    }
}

class LinkedListStringList extends AbstractStringList {
    private static class Node {
        String value;
        Node next;
        Node(String value) {
            this.value = value;
        }
    }

    @Override
    public StringIterator iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements StringIterator {
        private Node current = head;
        @Override
        public boolean hasNext() {
            return current != null;
        }
        @Override
        public String next() {
            String value = current.value;
            current = current.next;
            return value;
        }
    }

    private Node head;
    private Node tail;
    private int size;
    @Override
    public int size() {
        return size;
    }
    public void add(String s) {
        Node node = new Node(s);
        if (tail == null) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    private Node atIndex(int index) {
        checkIndex(index);
        Node node = head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    public String get(int index) {
        return atIndex(index).value;
    }

    public String set(int index, String s) {
        Node node = atIndex(index);
        String old = node.value;
        node.value = s;
        return old;
    }
    public String removeAt(int index) {
        checkIndex(index);
        if (size == 1) {
            var old = head.value;
            head = tail = null;
            size--;
            return old;
        }
        if (index == 0) {
            var old = head.value;
            head = head.next;
            size--;
            return old;
        }
        Node prev = atIndex(index - 1);
        var old = prev.next.value;
        prev.next = prev.next.next;
        size--;
        return old;
    }
}

class ArrayStringList extends AbstractStringList {
    private static final int INITIAL_SIZE = 8;
    private static final float GROW_FACTOR = 1.5f;
    private String[] data = new String[INITIAL_SIZE];
    private int size = 0;

    @Override
    public StringIterator iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements StringIterator {
        private int index = 0;
        @Override
        public boolean hasNext() {
            return index < size;
        }
        @Override
        public String next() {
            return data[index++];
        }
    }

    @Override
    public int size() {
        return size;
    }
    public void add(String s) {
        if (size == data.length) {
            data = Arrays.copyOf(data, (int)(data.length * GROW_FACTOR));
        }
        data[size++] = s;
    }
    public String get(int index) {
        checkIndex(index);
        return data[index];
    }

    public String set(int index, String s) {
        checkIndex(index);
        String old = data[index];
        data[index] = s;
        return old;
    }

    public String removeAt(int index) {
        checkIndex(index);
        String old = data[index];
        System.arraycopy(data, index + 1, data, index, size - index - 1);
        data[--size] = null; // Let GC do its work, prevent memory leak
        return old;
    }
}
