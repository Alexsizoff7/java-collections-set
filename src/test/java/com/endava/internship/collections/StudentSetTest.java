package com.endava.internship.collections;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class StudentSetTest {

    private StudentSet set;
    Student st1 = new Student("Mark", LocalDate.of(1990, 1, 20), "Employee");
    Student st2 = new Student("Alex", LocalDate.of(1995, 3, 18), "Intern");
    Student st3 = new Student("John", LocalDate.of(1993, 4, 22), "Intern");
    Student st4 = new Student("Marie", LocalDate.of(1996, 7, 4), "Intern");
    Student st5 = new Student("James", LocalDate.of(1992, 11, 12), "Mentor");
    Student st6 = new Student("Rob", LocalDate.of(1991, 2, 6), "Line Manager");
    Student st7 = new Student("Brad", LocalDate.of(1999, 5, 28), "Intern");
    Student st8 = new Student("Dup", LocalDate.of(2000, 1, 1), "Intern");

    @BeforeEach
    void setUp() {
        set = new StudentSet();
    }

    @AfterEach
    void setClear() {
        set.clear();
    }

    @Test
    void addAStudentToSet_shouldBeTrueAndContainStudent() {
        set.add(st2);

        assertTrue(set.contains(st2));
    }

    @Test
    void addDuplicatedStudentToSet_shouldContainOnlyOneStudent() {
        set.add(st3);
        set.add(st3);

        assertThat(set).containsOnlyOnce(st3);
    }

    @Test
    void addNullToSet_shouldAllowASingleNullElement() {
        set.add(null);

        assertThat(set).containsNull();
    }

    @Test
    void addDuplicatedNullToSet_shouldAllowASingleNullElement() {
        set.add(null);

        assertFalse(set.add(null));
    }

    @Test
    void removeExistingElementFromSet_shouldBeTrue() {
        set.add(st7);

        assertTrue(set.remove(st7));
        assertThat(set).doesNotContain(st7);
    }

    @Test
    void removeExistingNullFromSet_shouldBeTrue() {
        set.add(st1);
        set.add(null);

        assertTrue(set.remove(null));
        assertThat(set).containsExactly(st1);
    }

    @Test
    void removeElementThatDoesntExistsInASet_shouldBeFalse() {
        set.add(st6);
        set.add(null);

        assertFalse(set.remove(st8));
    }

    @Test
    void removeNullThatDoesntExistsInASet_shouldBeFalse() {
        set.add(st5);

        assertFalse(set.remove(null));
    }

    @Test
    void removeMethodCompilesWithNonStudentArgument_shouldCompileAndReturnFalse() {
        set.add(st4);
        String s = "string";

        assertFalse(set.remove(s));
    }

    @Test
    void setContainsAnElement_shouldBeTrue() {
        set.add(st4);
        set.add(st5);
        set.add(null);

        assertThat(set).containsExactlyInAnyOrder(st4, st5, null);
    }

    @Test
    void setDoesNotContainAnElement_shouldBeTrue() {
        set.add(st2);
        set.add(st3);

        assertThat(set).doesNotContain(st4);
    }

    @Test
    void containMethodCompilesWithNonStudentArgument_shouldCompileAndReturnFalse() {
        set.add(st4);
        String s = "string";

        assertFalse(set.contains(s));
    }

    @Test
    void sizeCountsCorrectlyAfterSeveralAddAndRemoveMethods() {
        set.add(st1);
        set.add(st2);
        set.add(null);
        set.add(st2);
        set.remove(st1);
        set.add(st8);

        assertEquals(3, set.size());
    }

    @Test
    void setClearsToSizeZero() {
        set.add(st5);
        set.add(null);
        set.clear();

        assertThat(set).isEmpty();
    }

    @Test
    void setResizesAfterLoadFactorIncreasesToItsBoundary_shouldResizeAndKeepAllElementsAdded() {
        StudentSet set2 = new StudentSet(5);
        set2.add(st1);
        set2.add(st2);
        set2.add(st3);
        set2.add(st4);
        set2.add(st5);
        set2.add(st6);

        assertThat(set2).containsExactlyInAnyOrder(st1, st2, st3, st4, st5, st6);
    }

    @Test
    void addingAllElementsFromOneSetToAnother_shouldAddElementsAndIncreaseSetCapacity() {
        StudentSet set2 = new StudentSet(5);
        set.add(st1);
        set.add(st2);
        set.add(st3);
        set.add(st4);
        set.add(st5);
        set.add(st6);
        set2.add(st6);
        set2.add(st7);
        set2.add(null);

        set2.addAll(set);

        assertThat(set2).containsExactlyInAnyOrder(st1, st2, st3, st4, st5, st6, st7, null);
    }

    @Test
    void setIsEmptyIfContainsZeroElements() {
        assertThat(set).isEmpty();
    }

    @Test
    void addingStudentsToArray_shouldContainSameStudents() {
        set.add(st4);
        set.add(st5);
        List<Object> expectedArray = new ArrayList<>();
        expectedArray.add(st4);
        expectedArray.add(st5);

        Object[] result = set.toArray();

        assertThat(result).containsExactlyInAnyOrderElementsOf(expectedArray);
    }

    @Test
    void addingStudentsToArrayWithLessSpace_shouldCopyAnArrayWithAllStudentsAndIncreaseItsSize() {
        set.add(st1);
        set.add(st2);
        set.add(st3);
        set.add(st4);
        set.add(st5);
        List<Student> expectedArray = new ArrayList<>();
        expectedArray.add(st1);
        expectedArray.add(st2);
        expectedArray.add(st3);
        expectedArray.add(st4);
        expectedArray.add(st5);

        Student[] students = set.toArray(new Student[3]);

        assertThat(students).containsExactlyInAnyOrderElementsOf(expectedArray);
    }

    @Test
    void addingStudentsToArrayWithHigherSize_shouldFillEmptyElementsWithNulls() {
        set.add(st1);
        set.add(st2);
        set.add(st3);
        List<Student> expectedArray = new ArrayList<>();
        expectedArray.add(st1);
        expectedArray.add(st2);
        expectedArray.add(st3);
        expectedArray.add(null);
        expectedArray.add(null);
        expectedArray.add(null);

        Student[] students = set.toArray(new Student[6]);

        assertThat(students).containsExactlyInAnyOrderElementsOf(expectedArray);
    }

    @Test
    void removeAllMethodIsNotImplemented_shouldThrowException() {
        assertThrows(UnsupportedOperationException.class, () ->
                set.removeAll(new ArrayList<Student>()));
    }

    @Test
    void retainAllMethodIsNotImplemented_shouldThrowException() {
        assertThrows(UnsupportedOperationException.class, () ->
                set.retainAll(new ArrayList<Student>()));

    }

    @Test
    void containsAllMethodIsNotImplemented_shouldThrowException() {
        assertThrows(UnsupportedOperationException.class, () ->
                set.containsAll(new ArrayList<Student>()));

    }

}