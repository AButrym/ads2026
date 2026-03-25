package edu.khnu.rbecs.programming;

import java.util.Scanner;

public class TryCatchDemo {
    static void main() {
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
