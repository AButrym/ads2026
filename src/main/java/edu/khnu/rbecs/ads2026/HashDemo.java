package edu.khnu.rbecs.ads2026;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashDemo {
    static void main() {

    }
}

interface MyMap<K, V> extends HasSize, Iterable<MyMap.Entry<K, V>> {
    V put(K key, V value);
    V get(K key);
//    V remove(K key);

    public interface Entry<K, V> {
        K getKey();
        V getValue();
        V setValue(V value);
    }
}

class MyHashMap<K, V> implements MyMap<K, V> {
    private static final int INITIAL_CAPACITY = 8;
    private int size = 0;
    private Node<K, V>[] data = new Node[INITIAL_CAPACITY];

    private static class Node<K1, V1> implements Entry<K1, V1> {
        K1 key;
        V1 value;
        int hash;
        Node<K1, V1> next;

        Node(K1 key, V1 value, Node<K1, V1> next) {
            this.key = key;
            this.value = value;
            this.next = next;
            this.hash = key.hashCode();
        }

        @Override
        public K1 getKey() {
            return key;
        }

        @Override
        public V1 getValue() {
            return value;
        }

        @Override
        public V1 setValue(V1 value) {
            var old = this.value;
            this.value = value;
            return old;
        }
    }

    private int ix(int hash) {
        return Math.abs(hash) % data.length;
    }

    public Iterator<Entry<K,V>> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<Entry<K,V>> {
        int i = 0;
        int iBin = 0;
        Node<K, V> cur = data[iBin];

        @Override
        public boolean hasNext() {
            return i < size;
        }

        @Override
        public Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            while (cur == null) {
                iBin++;
                cur = data[iBin];
            }
            var res = cur;
            cur = cur.next;
            i++;
            return res;
        }
    }

    @Override
    public V put(K key, V value) {
        if (value == null) throw new NullPointerException();
        int hash = key.hashCode();
        int bin = ix(hash);
        Node<K, V> cur = data[bin];
        while (cur != null) {
            if (cur.hash == hash && cur.key.equals(key)) {
                return cur.setValue(value);
            }
            cur = cur.next;
        }
        data[bin] = new Node<>(key, value, data[bin]);
        size++;
        return null;
    }

    @Override
    public V get(K key) {
        int hash = key.hashCode();
        int bin = ix(hash);
        Node<K, V> cur = data[bin];
        while (cur != null) {
            if (cur.hash == hash && cur.key.equals(key)) return cur.value;
            cur = cur.next;
        }
        return null;
    }

//    @Override
//    public V remove(K key) {
//        return null;
//    }

    @Override
    public int size() {
        return size;
    }
}