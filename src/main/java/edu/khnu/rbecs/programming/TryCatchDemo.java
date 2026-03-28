package edu.khnu.rbecs.programming;

import java.util.Scanner;

// Throwable(checked)
// Error(unchecked)    Exception(checked)
//             IOException(checked)  RuntimeException(unchecked)

// checked: try/catch or declare with throws

public class TryCatchDemo {
    static void main() {
        try {
            foo();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static void foo() throws Exception {
        Exception exception = new Exception();
        throw exception;
    }


    static void main1() {
        Scanner scan = new Scanner(System.in);
        int a = 0;
        int[] c = {1, 2, 3};
        try {
            int i = Integer.parseInt(scan.next());
            int b = c[i] / a;
            System.out.println("a = " + a + ", b = " + b);
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
            throw e;
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Finally block");
        }
        System.out.println("Happy end");
    }
}
