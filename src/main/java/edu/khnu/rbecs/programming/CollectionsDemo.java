package edu.khnu.rbecs.programming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CollectionsDemo {
    static void main() {
        List<Integer> list1 = List.of(1, 2, 3);
//        list1.add(1);
//        list1.set(1, 22);
        List<Integer> list2 = Arrays.asList(1, 2, 3);
//        list2.add(1);
        list2.set(1, 22);
        List<Integer> list3 = new ArrayList<>(List.of(1, 2, 3));
        list3.add(1);
        list3.set(1, 22);

        List<Integer> list4 = new ArrayList<>();
        list4.add(1);
        list4.add(2);
        list4.add(3);

        List<Integer> list5 = new ArrayList<>() {{
            add(1);
            add(2);
            add(3);
        }};
    }
}
