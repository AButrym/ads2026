package edu.khnu.rbecs.programming;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;

public class ComparatorDemo {
    static void main() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("John", 20));
        students.add(new Student("Alice", 22));
        students.add(new Student("Bob", 23));
        students.add(new Student("Eve", 22));
        System.out.println(students);
        students.sort(null);
        System.out.println(students);
        students.sort(new StudentByAgeComparator());
        System.out.println(students);
        students.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                var res = Integer.compare(o2.getAge(), o1.getAge());
                if (res != 0) return res;
                return o2.getName().compareTo(o1.getName());
            }
        });
        System.out.println(students);
        students.sort((o1, o2) -> {
            var res = -Integer.compare(o2.getAge(), o1.getAge());
            if (res != 0) return res;
            return o2.getName().compareTo(o1.getName());
        });
        System.out.println(students);
        students.sort(
                comparing(Student::getAge, reverseOrder())
                .thenComparing(Student::getName)
        );
        System.out.println(students);
    }
}

class StudentByAgeComparator implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        return Integer.compare(o1.getAge(), o2.getAge());
    }
}

class StudentByAgeDescAndByNameDescComparator implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        var res = Integer.compare(o2.getAge(), o1.getAge());
        if (res != 0) return res;
        return o2.getName().compareTo(o1.getName());
    }
}

// POJO - Plain Old Java Object
class Student implements Comparable<Student> {
    private String name;
    private int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int compareTo(Student o) {
        return name.compareTo(o.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return name + "(" + age + ')';
    }
}
