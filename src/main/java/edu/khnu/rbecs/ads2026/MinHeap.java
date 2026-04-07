package edu.khnu.rbecs.ads2026;

import java.util.Arrays;
import java.util.NoSuchElementException;

public interface MinHeap {
    int getSize();
    int getMin();
    void add(int key);
    int removeMin();
    default boolean isEmpty() {
        return getSize() == 0;
    }
}

class MinBinaryHeap implements MinHeap {
    private static final int INITIAL_SIZE = 8;
    int[] data = new int[INITIAL_SIZE];
    int size = 0;

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        return data[1];
    }

    private static int nextPow2(int n) {
        return 1 << (32 - Integer.numberOfLeadingZeros(n - 1));
    }

    public static MinHeap heapify(int[] arr) { // O(N)
        MinBinaryHeap heap = new MinBinaryHeap();
        int n = nextPow2(arr.length + 1);
        heap.data = new int[n];
        System.arraycopy(arr, 0, heap.data, 1, arr.length);
        heap.size = arr.length;
        for (int i = heap.size / 2; i >= 1; i--) {
            heap.down(i);
        }
        return heap;
    }

    @Override
    public void add(int key) {
        checkSize();
        data[++size] = key;
        up(size);
    }

    private void checkSize() {
        if (size == data.length - 1) {
            data = Arrays.copyOf(data, data.length * 2);
        }
    }

    private void up(int ix) {
        while (ix > 1) {
            int parent = ixParent(ix);
            if (data[ix] >= data[parent]) break;
            swap(parent, ix);
            ix = parent;
        }
    }

    @Override
    public int removeMin() {
        int res = getMin();
        swap(1, size--);
        down(1);
        return res;
    }

    private void down(int ix) {
        while (true) {
            int left = ixLeftChild(ix);
            if (left > size) break;
            int right = ixRightChild(ix);
            int minChild = left;
            if (right <= size && data[right] < data[minChild]) {
                minChild = right;
            }
            if (data[ix] <= data[minChild]) break;
            swap(ix, minChild);
            ix = minChild;
        }
    }

    private int ixParent(int ix) {
        return ix / 2;
    }

    private int ixLeftChild(int ix) {
        return 2 * ix;
    }

    private int ixRightChild(int ix) {
        return 2 * ix + 1;
    }

    private void swap(int i, int j) {
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }
}
