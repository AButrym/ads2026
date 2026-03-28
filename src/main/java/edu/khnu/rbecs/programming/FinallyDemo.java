package edu.khnu.rbecs.programming;

public class FinallyDemo {
    // final finally finalize()
    static void main() {
        System.out.println(greeting());
    }

    static String greeting() {
        block:
        {
            try {
                // return throw break continue
                System.out.println("Trying ...");
                if (true) throw new RuntimeException("Huston we have a problem!");
            } finally {
                if (true) break block;
            }
            System.out.println("After finally");
        }
        System.out.println("After block");
        return "It's fine!";
    }
}
