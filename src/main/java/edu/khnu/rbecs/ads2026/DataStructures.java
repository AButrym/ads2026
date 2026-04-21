package edu.khnu.rbecs.ads2026;

import java.util.Arrays;
import java.util.NoSuchElementException;

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

// Trait
interface HasSize {
    int size();
    default boolean isEmpty() {
        return size() == 0;
    }
}
// LIFO -- Last In First Out
interface StringStack extends HasSize, StringIterable {
    void push(String s);
    String pop();
    String peek();
}
// FIFO -- First In First Out
interface StringQueue extends HasSize, StringIterable {
    void enqueue(String s);
    String dequeue();
    String peek();
}

interface StringDeque extends StringStack, StringQueue {
    void addFirst(String s);
    String removeFirst();
    String peekFirst();
    void addLast(String s);
    String removeLast();
    String peekLast();

    default String peek() {
        return peekFirst();
    }
    default String pop() {
        return removeFirst();
    }
    default void push(String s) {
        addFirst(s);
    }
    default void enqueue(String s) {
        addLast(s);
    }
    default String dequeue() {
        return removeFirst();
    }
}

interface StringList extends StringIterable, HasSize {
    // CRUD - Create, Read, Update, Delete
    void add(String s);
    String get(int index);
    String set(int index, String s);
    String removeAt(int index);
    int indexOf(String s);

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

class LinkedListStringList extends AbstractStringList implements StringStack, StringQueue {
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

    @Override
    public void push(String s) {
        Node node = new Node(s);
        if (head == null) {
            head = tail = node;
        } else {
            node.next = head;
            head = node;
        }
        size++;
    }

    @Override
    public String pop() {
        if (head == null) throw new NoSuchElementException();
        var res = head.value;
        if (head == tail) { tail = null; }
        head = head.next;
        size--;
        return res;
    }

    @Override
    public String peek() {
        if (head != null) return head.value;
        throw new NoSuchElementException();
    }

    @Override
    public void enqueue(String s) {
        add(s);
    }

    @Override
    public String dequeue() {
        return pop();
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
        if (prev.next == tail) {
            tail = prev;
        }
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

class CircularArrayStringDeque extends AbstractStringList implements StringDeque {
    private static final int INITIAL_SIZE = 8;
    private static final float GROW_FACTOR = 1.5f;
    private String[] data = new String[INITIAL_SIZE];
    private int size = 0;
    private int front = 0;
    private int rear = 0; // exclusive

    @Override
    public StringIterator iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements StringIterator {
        private int index = front;
        @Override
        public boolean hasNext() {
            return index != rear;
        }
        @Override
        public String next() {
            if (!hasNext()) throw new NoSuchElementException();
            var res = data[index++];
            if (index == data.length) index = 0;
            return res;
        }
    }

    @Override
    public int size() {
        return size;
    }

    private void ensureSize() {
        if (size == data.length) {
            var newSize = (int)(data.length * GROW_FACTOR);
            var newData = new String[newSize];
            if (front < rear) {
                System.arraycopy(data, front, newData, 0, size);
            } else {
                System.arraycopy(data, front, newData, 0, data.length - front);
                System.arraycopy(data, 0, newData, data.length - front, rear);
            }
            front = 0;
            rear = size;
            data = newData;
        }
    }

    public void addLast(String s) {
        ensureSize();
        if (rear == data.length) rear = 0;
        data[rear++] = s;
        size++;
    }

    public void addFirst(String s) {
        ensureSize();
        if (front == 0) front = data.length;
        data[--front] = s;
        size++;
    }

    public String removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        var res = data[front];
        data[front] = null;
        if (front == data.length - 1) front = 0;
        else front++;
        size--;
        return res;
    }

    public String removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        if (rear == 0) rear = data.length;
        var res = data[--rear];
        data[rear] = null;
        size--;
        return res;
    }

    @Override
    public String peekFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        return data[front];
    }

    @Override
    public String peekLast() {
        if (isEmpty()) throw new NoSuchElementException();
        return data[rear == 0 ? data.length - 1 : rear - 1];
    }

    @Override
    public void add(String s) {
        addLast(s);
    }

    public String get(int index) {
        checkIndex(index);
        return data[ix(index)];
    }

    private int ix(int i) {
        return (front + i) % data.length;
    }

    public String set(int index, String s) {
        checkIndex(index);
        int ix = ix(index);
        String old = data[ix];
        data[ix] = s;
        return old;
    }

    public String removeAt(int index) {
        checkIndex(index);
        int ix = ix(index);
        String old = data[ix];

        // Move elements after index one position to the left
        for (int i = index; i < size - 1; i++) {
            data[ix(i)] = data[ix(i + 1)];
        }
        
        int lastIx = ix(size - 1);
        data[lastIx] = null;
        size--;
        rear = lastIx;
        return old;
    }
}