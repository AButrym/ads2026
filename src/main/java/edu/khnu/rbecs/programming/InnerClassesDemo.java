package edu.khnu.rbecs.programming;

public class InnerClassesDemo {
    static void main() {
        A4.A a1 = null;
        A a2;
        A5.B2 b2 = new A5.B2();
        A4.B b3 = new A4.B();
        A6 a6 = new A6();
        A6.B1 b4 = a6.new B1();
    }
}

// class; interface; abstract class; enum; record

enum E1 {
    A, B, C;
    protected interface A {}
    private class B1 {
        public static class B2 {}
    }
    public static class B2 {}
}

record R1(int x, String y) {
    protected interface A {}
    private class B1 {}
    public static class B2 {}
}

interface A4 {
    interface A {}
    /*static*/ class B {}
}

abstract class A5 {
    private interface A {
    }
    protected class B1 {
        B2 foo() { return null; }
    }
    static class B2 {}

    B1 foo() {
//        return this.new B1();
        return new B1();
    }
}

class A6 extends A5 {}

class A7 {
    private int i = 12;
    class A {
        void foo() {
            System.out.println("i = " + i);
            System.out.println("B.i = " + B.i);
        }
    }
    private static class B {
        private static int i = 13;
    }
}
