package edu.khnu.rbecs.programming;

import java.util.Iterator;
import java.util.Optional;

import static java.lang.Double.NaN;
import static java.lang.Double.isNaN;

public class ExceptionsDemo {
    static void main() {
//        long[] huge = new long[Integer.MAX_VALUE]; // OutOfMemoryError
//        main(); // StackOverflowError
//        int a = 12;
//        assert a < 11 : "a is too big";
//        if (a >= 11) throw new IllegalStateException("a is too big");
//        double log = Math.log(-1);
//        double inf = -1 / 0.0;
//        if (log == NaN || isNaN(log))
//        System.out.println(log);
//        System.out.println(inf);
//        Integer[] arr = new Integer[10];
//        Object[] arr1 = arr;
//        arr1[0] = 1;
//        arr1[1] = "2"; // java.lang.ArrayStoreException
//        Object o = 12;
//        if (o instanceof Integer i) {
//            System.out.println(i.intValue());
//        }
//        Double d = (Double) o; // java.lang.ClassCastException
//        int[] arr = new int[1];
//        System.out.println(arr[3]); // java.lang.ArrayIndexOutOfBoundsException
//        String s = null;
//        System.out.println(s.length()); // NPE
//        Optional<String> opt = Optional.ofNullable(s);
//        System.out.println(opt.orElse(""));
        System.out.println("Happy end");
    }
    static double myLog(double d) {
        if (d < 0) throw new IllegalArgumentException("d < 0");
        return Math.log(d);
    }
}
