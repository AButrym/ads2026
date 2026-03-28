package edu.khnu.rbecs.programming;

import java.io.FileNotFoundException;
import java.io.IOException;

public class OverridingThrowsDemo {
    static void main() {
        A2 a1 = new A2();
//        try {
            a1.m();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }
}

class A1 {
    public void m() throws IOException {}
}

class A2 extends A1 {
    @Override
    public void m() throws RuntimeException {}
}