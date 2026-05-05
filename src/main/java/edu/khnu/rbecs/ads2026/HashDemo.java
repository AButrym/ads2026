package edu.khnu.rbecs.ads2026;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class HashDemo {
    static void main() throws IOException {
        var path = Path.of("c:/tmp/pg2701.txt");
        List<String> lines = Files.readAllLines(path);
        List<String> words = new ArrayList<>();
        for (var line : lines) {
            if (line.isEmpty()) continue;
            line = line.toLowerCase();
            String[] lineWords = line.split("[^a-z-']+");
            for (var word : lineWords) {
                if (word.isEmpty()) continue;
                words.add(word);
            }
        }
        System.out.println("words = " + words.size());
        System.out.println(words.subList(0, 20));

        var t0 = System.nanoTime();
        MyHashMap<String, Integer> counter1 = new MyHashMap<String, Integer>();
        for (var word : words) {
            var entry = counter1.getEntry(word);
            if (entry == null) {
                counter1.unsafeAdd(word, 1);
            } else {
                entry.setValue(entry.getValue() + 1);
            }
        }
        var t1 = System.nanoTime();
        System.out.println("time = " + (t1 - t0) / 1e6 + "ms");
        System.out.println("unique words: " + counter1.size());

        var t2 = System.nanoTime();
        Map<String, Integer> counter2 = new HashMap<>();
        for (var word : words) {
//            if (counter2.containsKey(word)) {
//                counter2.put(word, counter2.get(word) + 1);
//            } else {
//                counter2.put(word, 1);
//            }
            counter2.merge(word, 1, Integer::sum);
        }
        var t3 = System.nanoTime();
        System.out.println("time = " + (t3 - t2) / 1e6 + "ms");
        System.out.println("unique words: " + counter2.size());
    }
}

interface MyMap<K, V> extends HasSize, Iterable<MyMap.Entry<K, V>> {
    V put(K key, V value);
    default V get(K key) {
        var entry = getEntry(key);
        if (entry == null) return null;
        return entry.getValue();
    }
    Entry<K, V> getEntry(K key);

    public interface Entry<K, V> {
        K getKey();
        V getValue();
        V setValue(V value);
    }
}

class MyHashMap<K, V> implements MyMap<K, V> {
    private static final int INITIAL_CAPACITY = 8;
    private static final float LOAD_FACTOR = 0.75f;
    private static final float GROW_FACTOR = 2f;

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
        Node(K1 key, V1 value, Node<K1, V1> next, int hash) {
            this.key = key;
            this.value = value;
            this.next = next;
            this.hash = hash;
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

    void unsafeAdd(K key, V value) {
        var node = new Node<K, V>(key, value, null);
        int bin = ix(node.hash);
        node.next = data[bin];
        data[bin] = node;
        size++;
        resizeIfNeeded();
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
        data[bin] = new Node<>(key, value, data[bin], hash);
        size++;
        resizeIfNeeded();
        return null;
    }

    private void resizeIfNeeded() {
        if (size <= data.length * LOAD_FACTOR) return;
        var oldData = data;
        data = new Node[(int) (oldData.length * GROW_FACTOR)];
        int iBin = 0;
        Node<K, V> cur = oldData[0];
        for (int i = 0; i < size; i++) {
            while (cur == null) {
                cur = oldData[++iBin];
            }
            var res = cur;
            cur = cur.next;
            int newBin = ix(res.hash);
            res.next = data[newBin];
            data[newBin] = res;
        }
    }

    @Override
    public Entry<K, V> getEntry(K key) {
        int hash = key.hashCode();
        int bin = ix(hash);
        Node<K, V> cur = data[bin];
        while (cur != null) {
            if (cur.hash == hash && cur.key.equals(key)) return cur;
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