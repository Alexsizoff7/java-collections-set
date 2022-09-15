package com.endava.internship.collections;

import java.util.*;

public class StudentSet implements Set<Student> {

    private int capacity;
    private List <LinkedList<Student>> buckets;
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 16;

    public StudentSet (int capacity) {
        this.capacity = capacity;
        buckets = new ArrayList <>(capacity);
        for(int i=0; i < capacity;i++) {
            buckets.add(new LinkedList<>());
        } 
    }

    public StudentSet () {
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
        if (o instanceof Student) {
            Student student = (Student) o;
            int index = calculatedIndex(student);
            LinkedList <Student> bucket = buckets.get(index);
            for (Student st : bucket) {
                if (student.hashCode() == st.hashCode() && student.equals(st)) {
                    return true;
                }
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
        Object [] array = new Object[size];
        int counter = 0;
        for (List <Student> bucket : buckets) {
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
        for (List <Student> bucket : buckets) {
            for (Student student : bucket) {
                ts[counter] = (T) student;
                counter++;
            }
        }
            for (int i = counter; counter<ts.length-1; i++) {
                ts[i] = null;
            }

        // counter < ts.len-1 then ts[counter+1, ..., len-1] = null
        return ts;
    }

    @Override
    public boolean add(Student student) {
        resizeCheck();
        int index = calculatedIndex(student);
        LinkedList <Student> bucket = buckets.get(index);
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
        if (o instanceof Student) {
            Student student = (Student) o;
            int index = calculatedIndex(student);
            LinkedList <Student> bucket = buckets.get(index);
            for (Student st : bucket) {
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

        List <LinkedList<Student>> clearedBuckets;
        clearedBuckets = new ArrayList <>(capacity);
        for(int i=0; i < capacity; i++) {
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

    private int calculatedIndex (Student student) {
        int hashCode = student.hashCode();
        return Math.abs(hashCode % capacity);
    }

    void resizeCheck () {
        if (size>(capacity*0.75)) {
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

    private class StudentIterator implements Iterator <Student> {

        // init cursor 1,2 -> curr bucket, current index
        // toArray() and store its result

        private int current;
        private int size;
        Object [] elements;

        public StudentIterator () {
            size = size();
            elements = toArray();
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
            Student nextStudent = null;

            nextStudent = (Student) elements[elementIndex];
            current++;
            return nextStudent;
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


