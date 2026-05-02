package edu.khnu.rbecs.programming;

import java.time.LocalDate;
import java.util.Map;

public class GenericsDemo {
    static void main() {
        Wrapper<String> wrapper1 = new Wrapper<>("Hello");
        Wrapper<LocalDate> wrapper2 = new Wrapper<>(LocalDate.now());
        Wrapper<Integer> wrapper3 = new Wrapper<>(12);
        wrapper1.setValue("Goodbye");
        wrapper2.setValue(null);
        Map.Entry<String, LocalDate> entry = Map.entry("Hello", LocalDate.now());
    }

    static <T, S> T print(T t, T t2, S s1, S s2) {
        return t;
    }
}

class Wrapper<T> {
    private T value;

    public Wrapper(T value) {
        if (value == null) throw new NullPointerException();
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        if (value == null) throw new NullPointerException();
        this.value = value;
    }
}


interface GenericStack<T> {
    T peek();
    T pop();
    void push(T item);
}

//class GenericStackImpl<T> implements GenericStack<T> {
//}
