package edu.khnu.rbecs.programming;

public class UmlDemo {
}

interface I1 {}
interface I2 {}
interface I extends I1, I2 {}
abstract class A {
    interface I3 {}
    static class Inner {}

    private int x;
    String id;
    protected String name;
    public double field1;

    public static int staticField;
    public static void staticMethod() {}

    public String getFullName() {
        return name;
    }

    public void setFullName(String name) {
        this.name = name;
    }

    public A() {
    }

    public A(int x) {
        this.x = x;
    }

    public A(int x, String id, String name, double field1) {
        this.x = x;
        this.id = id;
        this.name = name;
        this.field1 = field1;
    }

    public void method1() {}
    protected void method2() {}
    void method3() {}
    private void method4() {}

    abstract void method5();
}
class B extends A implements I {
    public B() {
        super(1);
    }
    public void method5() {}
}