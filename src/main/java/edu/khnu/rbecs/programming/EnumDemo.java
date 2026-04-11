package edu.khnu.rbecs.programming;

public class EnumDemo {
    static void main() {
        TimeUnit.DAY.foo();
        System.out.println(TimeUnit.DAY.toSeconds());
        System.out.println(TimeUnit.DAY.toMinutes());
        TimeUnit t = TimeUnit.SECOND;
        t.foo();
    }
}

class A3 {}
interface I3 {}
record R3(int x) {}

enum E3 {
    A, B, C, SOME_ENUM_VALUE;

    @Override
    public String toString() {
        return "\"" + name() + "\"";
    }
}

enum TimeUnit {
    DAY(24*60*60) {
        @Override
        void foo() {
            System.out.println("foo");
        }
    },
    HOUR(60*60) {
        @Override
        void foo() {
            System.out.println("bar");
        }
    },
    MINUTE(60) {
        @Override
        void foo() {
            System.out.println("baz");
        }
    },
    SECOND(1) {
        @Override
        void foo() {
            System.out.println("qux");
        }
    };

    public final int seconds;

    TimeUnit(int seconds) {
        this.seconds = seconds;
    }

    public int toSeconds() {
        return seconds;
    }

    public int toMinutes() {
        return seconds / MINUTE.seconds;
    }

    abstract void foo();
}
