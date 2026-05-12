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
        MyHashMap<String, Integer> counter1 = new MyHashMap<>();
        for (var word : words) {
            var entry = counter1.getEntry(word);
            if (entry == null) {
                counter1.unsafeAdd(word, 1);
            } else {
                entry.setValue(entry.getValue() + 1);
            }
        }
        var t1 = System.nanoTime();
        System.out.println("MyHashMap time = " + (t1 - t0) / 1e6 + "ms");
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
        System.out.println("HashMap time = " + (t3 - t2) / 1e6 + "ms");
        System.out.println("unique words: " + counter2.size());

        var t4 = System.nanoTime();
        Bag<String> counter3 = new MyCounter<>();
        for (var word : words) {
            counter3.add(word);
        }
        var t5 = System.nanoTime();
        System.out.println("MyCounter time = " + (t5 - t4) / 1e6 + "ms");
        System.out.println("unique words: " + counter3.size());

        System.out.println(counter1.get("moby"));
        System.out.println(counter2.get("moby"));
        System.out.println(counter3.get("moby"));

        List<Map.Entry<String, Integer>> sorted = new ArrayList<>(counter2.entrySet());
        sorted.sort(Map.Entry.comparingByValue(Collections.reverseOrder()));
        sorted.stream()
                .filter(e -> e.getKey().length() > 4)
                .limit(10)
                .forEach(System.out::println);
    }
}

interface MyMap<K, V> extends HasSize, Iterable<MyMap.Entry<K, V>> {
    V put(K key, V value);
    default V get(K key) {
        var entry = getEntry(key);
        return entry == null ? null : entry.getValue();
    }
    Entry<K, V> getEntry(K key);

    interface Entry<K, V> {
        K getKey();
        V getValue();
        V setValue(V value);
    }
}

class MyHashMap<K, V> implements MyMap<K, V> {
    private static final int INITIAL_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.5f;
    private static final float GROW_FACTOR = 2f;

    private int size = 0;
    private Node<K, V>[] data = new Node[INITIAL_CAPACITY];

    private static class Node<K1, V1> implements Entry<K1, V1> {
        final K1 key;
        V1 value;
        final int hash;
        Node<K1, V1> next;

        Node(K1 key, V1 value) {
            this.key = key;
            this.value = value;
            this.next = null;
            this.hash = Objects.hashCode(key);
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
//        return Math.abs(hash) % data.length; // фейлиться, якщо hash = Integer.MIN_VALUE
        return (hash & 0x7fffffff) % data.length; // прибираємо знаковий біт
//        return hash & (data.length - 1); // якщо довжина завжди є степенем двійки
    }

    @Override
    public Iterator<Entry<K,V>> iterator() {
        return this.new MyIterator();
    }

    private class MyIterator implements Iterator<Entry<K,V>> {
        int i = 0;
        int iBin = 0;
        Node<K, V> cur = MyHashMap.this.data[0];

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
                cur = data[++iBin];
            }
            var res = cur;
            cur = cur.next;
            i++;
            return res;
        }
    }

    // use only when you know that key is not in the map
    void unsafeAdd(K key, V value) {
        if (value == null) throw new NullPointerException();
        var node = new Node<>(key, value);
        int bin = ix(node.hash);
        node.next = data[bin];
        data[bin] = node;
        size++;
        resizeIfNeeded();
    }

    @Override
    public V put(K key, V value) {
        if (value == null) throw new NullPointerException();
        int hash = Objects.hashCode(key);
        int bin = ix(hash);
        var entry = findInBin(bin, key, hash);
        if (entry != null) {
            return entry.setValue(value);
        } else {
            data[bin] = new Node<>(key, value, data[bin], hash);
            size++;
            resizeIfNeeded();
            return null;
        }
    }

    private Node<K,V> findInBin(int bin, K key, int hash) {
        for (var cur = data[bin]; cur != null; cur = cur.next) {
            if (hash == cur.hash && Objects.equals(cur.key, key)) return cur;
        }
        return null;
    }

    private void resizeIfNeeded() {
        if (size <= data.length * LOAD_FACTOR) return;
        var oldData = data;
        data = new Node[(int) (oldData.length * GROW_FACTOR)];
        for (var head : oldData) {
            var cur = head;
            while (cur != null) {
                var next = cur.next;
                int bin = ix(cur.hash);
                cur.next = data[bin];
                data[bin] = cur;
                cur = next;
            }
        }
    }

    @Override
    public Entry<K, V> getEntry(K key) {
        int hash = Objects.hashCode(key);
        int bin = ix(hash);
        return findInBin(bin, key, hash);
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

interface Bag<T> extends HasSize, Iterable<Bag.Entry<T>> {
    long add(T item, long count); // returns number of occurrences after adding
    default long add(T item) {
        return add(item, 1);
    }
    long get(T item); // returns number of occurrences
    default boolean contains(T item) {
        return get(item) > 0;
    }
    interface Entry<T> {
        T getItem();
        long getCount();
    }
}

class MyCounter<K> implements Bag<K> {
    private static final int INITIAL_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;
    private static final float GROW_FACTOR = 2f;

    private int size = 0;
    private Node<K>[] data = new Node[INITIAL_CAPACITY];

    private static class Node<K1> implements Bag.Entry<K1> {
        final K1 key;
        long count;
        final int hash;
        Node<K1> next;

        Node(K1 key, long count, Node<K1> next, int hash) {
            this.key = key;
            this.count = count;
            this.next = next;
            this.hash = hash;
        }

        @Override
        public K1 getItem() {
            return key;
        }

        @Override
        public long getCount() {
            return count;
        }
    }

    private int ix(int hash) {
//        return Math.abs(hash) % data.length; // фейлиться, якщо hash = Integer.MIN_VALUE
        return (hash & 0x7fffffff) % data.length; // прибираємо знаковий біт
//        return hash & (data.length - 1); // якщо довжина завжди є степенем двійки
    }

    @Override
    public Iterator<Entry<K>> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<Entry<K>> {
        int i = 0;
        int iBin = 0;
        Node<K> cur = data[0];

        @Override
        public boolean hasNext() {
            return i < size;
        }

        @Override
        public Entry<K> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            while (cur == null) {
                cur = data[++iBin];
            }
            var res = cur;
            cur = cur.next;
            i++;
            return res;
        }
    }

    // use only when you know that key is not in the map
    void unsafeAdd(K key, long count) {
        var node = new Node<>(key, count, null, Objects.hashCode(key));
        int bin = ix(node.hash);
        node.next = data[bin];
        data[bin] = node;
        size++;
        resizeIfNeeded();
    }

    @Override
    public long add(K key, long count) {
        int hash = Objects.hashCode(key);
        int bin = ix(hash);
        var entry = findInBin(bin, key, hash);
        if (entry != null) {
            return entry.count += count;
        } else {
            data[bin] = new Node<>(key, count, data[bin], hash);
            size++;
            resizeIfNeeded();
            return count;
        }
    }

    private Node<K> findInBin(int bin, K key, int hash) {
        for (var cur = data[bin]; cur != null; cur = cur.next) {
            if (hash == cur.hash && Objects.equals(cur.key, key)) return cur;
        }
        return null;
    }

    private void resizeIfNeeded() {
        if (size <= data.length * LOAD_FACTOR) return;
        var oldData = data;
        data = new Node[(int) (oldData.length * GROW_FACTOR)];
        for (var head : oldData) {
            var cur = head;
            while (cur != null) {
                var next = cur.next;
                int bin = ix(cur.hash);
                cur.next = data[bin];
                data[bin] = cur;
                cur = next;
            }
        }
    }

    @Override
    public long get(K key) {
        int hash = Objects.hashCode(key);
        int bin = ix(hash);
        var entry = findInBin(bin, key, hash);
        return entry != null ? entry.count : 0;
    }

    @Override
    public int size() {
        return size;
    }
}