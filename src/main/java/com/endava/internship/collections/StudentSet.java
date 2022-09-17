package com.endava.internship.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public class StudentSet implements Set<Student> {

    private static final int DEFAULT_CAPACITY = 16;
    private int capacity;
    private List<LinkedList<Student>> buckets;
    private int size = 0;

    public StudentSet(int capacity) {
        this.capacity = capacity;
        buckets = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            buckets.add(new LinkedList<>());
        }
    }

    public StudentSet() {
        this(DEFAULT_CAPACITY);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (o instanceof Student || o == null) {
            Student student = (Student) o;
            int index = calculateIndex(student);
            LinkedList<Student> bucket = buckets.get(index);
            for (Student st : bucket) {
                if (o == null && st == null) return true;
                if (o != null && st == null) return false;
                if (st.equals(student)) return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<Student> iterator() {
        return new StudentIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int counter = 0;
        for (List<Student> bucket : buckets) {
            for (Student student : bucket) {
                array[counter] = student;
                counter++;
            }
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        if (ts.length < size) {
            ts = (T[]) java.lang.reflect.Array
                    .newInstance(ts.getClass().getComponentType(), size);
        }

        int counter = 0;
        for (List<Student> bucket : buckets) {
            for (Student student : bucket) {
                ts[counter] = (T) student;
                counter++;
            }
        }
        for (int i = counter; i < ts.length - 1; i++) {
            ts[i] = null;
        }
        return ts;
    }

    private boolean hasNull() {
        for (LinkedList<Student> bucket : buckets) {
            for (Student st : bucket) {
                if (st == null) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean add(Student student) {
        resizeCheck();
        int index = calculateIndex(student);
        LinkedList<Student> bucket = buckets.get(index);
        if (student == null && hasNull()) {
            return false;
        }
        if (student == null) {
            bucket.add(student);
            size++;
            return true;
        }
        for (Student st : bucket) {
            if (student.hashCode() == st.hashCode() && student.equals(st)) {
                return false;
            }
        }
        bucket.add(student);
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o instanceof Student || o == null) {
            Student student = (Student) o;
            int index = calculateIndex(student);
            LinkedList<Student> bucket = buckets.get(index);
            for (Student st : bucket) {
                if (o == null && st != null) continue;
                if (o == null && st == null) {
                    bucket.remove(student);
                    size--;
                    return true;
                }
                if (o != null && st == null) continue;
                if (student.hashCode() == st.hashCode() && student.equals(st)) {
                    bucket.remove(student);
                    size--;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void clear() {

        List<LinkedList<Student>> clearedBuckets;
        clearedBuckets = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            clearedBuckets.add(new LinkedList<>());
        }
        buckets = clearedBuckets;
        size = 0;
    }

    @Override
    public boolean addAll(Collection<? extends Student> collection) {
        boolean modified = false;
        for (Student student : collection) {
            if (this.add(student)) {
                modified = true;
            }
        }
        return modified;
    }

    private int calculateIndex(Student student) {
        int hashCode = student == null ? 0 : student.hashCode();
        return Math.abs(hashCode % capacity);
    }

    private void resizeCheck() {
        if (size > (capacity * 0.75)) {
            capacity *= 2;
            size = 0;
            List<LinkedList<Student>> oldBuckets = buckets;
            buckets = new ArrayList<>(capacity);
            for (int i = 0; i < capacity; i++) {
                buckets.add(new LinkedList<>());
            }
            for (LinkedList<Student> oldBucket : oldBuckets) {
                this.addAll(oldBucket);
            }
        }
    }


    @Override
    public String toString() {
        return "StudentSet{" +
                "capacity=" + capacity +
                ", buckets=" + buckets +
                ", size=" + size +
                '}';
    }

    private class StudentIterator implements Iterator<Student> {

        private int current;
        private final Student[] elements;

        public StudentIterator() {
            elements = toArray(new Student[size]);
        }

        @Override
        public boolean hasNext() {
            return current != size;
        }

        @Override
        public Student next() {
            int elementIndex = current;
            if (elementIndex >= size) {
                throw new NoSuchElementException();
            }

            current++;
            return elements[elementIndex];
        }
    }


    @Override
    public boolean containsAll(Collection<?> collection) {
        //Ignore this for homework
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        //Ignore this for homework
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        //Ignore this for homework
        throw new UnsupportedOperationException();
    }
}


