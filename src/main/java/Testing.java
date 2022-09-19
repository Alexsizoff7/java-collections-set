import com.endava.internship.collections.Student;
import com.endava.internship.collections.StudentSet;

import java.time.LocalDate;
import java.util.*;

public class Testing {
    public static void main(String[] args) {
//        List<Student> st = List.of(new Student("name", LocalDate.now(), "details1" ),
//                new Student("name2", LocalDate.now(), "details2" ));
//
//        st.toArray(new Student[0]);


        StudentSet test = new StudentSet(5);
        Student st1 = new Student("Mark", LocalDate.of(1990, 01, 20), "Employee");
        Student st2 = new Student("Alex", LocalDate.of(1995, 03, 18), "Intern");
        Student st3 = new Student("John", LocalDate.of(1993, 04, 22), "Intern");
        Student st4 = new Student("Marie", LocalDate.of(1996, 07, 4), "Intern");
        Student st5 = new Student("James", LocalDate.of(1992, 11, 12), "Mentor");
        Student st6 = new Student("Rob", LocalDate.of(1991, 02, 6), "Line Manager");
        Student st7 = new Student("Brad", LocalDate.of(1999, 05, 28), "Intern");
        Student st8 = new Student("Dup", LocalDate.of(2000, 01, 1), "Intern");
        StudentSet test2 = new StudentSet();


//        System.out.println(test.size());
//        System.out.println(test.add(st1));
//        System.out.println(test.add(st3));
//        System.out.println(test.add(st3));
//        System.out.println(test.add(st5));
//        System.out.println(test.size());
//        System.out.println(test.remove(st1));
//        System.out.println(test.size());
//        System.out.println(test.remove(st1));
//        System.out.println(test.size());
        test.add(st1);
        test.add(st2);
        test.add(st3);
        System.out.println(test.add(st4));
        System.out.println(test.add(st4));
        System.out.println(test.size());
        System.out.println("Adding null: " + test.add(null));
        System.out.println(test.size());
        System.out.println("Adding null 2nd time: " + test.add(null));
        System.out.println("In the set (null): " + test.contains(null));
        System.out.println("In the set: " + test.contains(st2));
        System.out.println("In the set: " + test.contains(st3));
        System.out.println("Not in the set: " + test.contains(st8));
//        test.add(null);
//        test.add(null);
//        test.add(null);
//        test.add(null);
        System.out.println(test);
        test.add(st5);
        System.out.println(test);
        test.add(st6);
        test.add(st7);
        test.add(st1);
        System.out.println(test);

        System.out.println("\nIterator starts from here");
        Iterator<Student> itr = test.iterator();

        while (itr.hasNext()) {
            System.out.println(itr.next());
        }

        System.out.println(test.size());
        System.out.println(test.remove(st7));
        System.out.println(test.remove(st7));
        System.out.println(test.remove(st1));
        System.out.println(test.remove(st2));
        System.out.println(test.remove(st3));
        System.out.println(test.remove(st4));
        System.out.println(test.remove(st5));
        System.out.println(test.remove(st6));
        System.out.println(test.size());
        System.out.println("Removing null first time " + test.remove(null));
        System.out.println("Removing null second time " + test.remove(null));
        System.out.println(test.remove(st1));
        System.out.println(test.remove(st2));
        System.out.println(test.remove(st3));
        System.out.println(test.remove(st4));
        System.out.println(test.remove(st5));
        System.out.println(test.remove(st6));
        System.out.println(test.size());
//        System.out.println(Arrays.toString(test.toArray(new ABC[0])));

//        Set<Student> sts = Set.of(st1, st2, st3);
//        sts.toArray(new ABC[0]);




    }
}

class ABC {

}