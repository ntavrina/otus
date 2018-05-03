package otus.hw3;

import java.util.*;

public class MyArrayList<T> extends AbstractList<T> implements List<T> {

    private int size;

    Object[] elements = {};

    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    public MyArrayList() {
        this.elements = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    private T[] increase(int count) {
        Object[] o = new Object[size + count];
        System.arraycopy(elements, 0, o, 0, size);
        elements = o;
        return (T[])elements;
    }

    public boolean add(T e) {
        if(elements.length >= size)
            elements = increase(1);
        elements[size] = e;
        ++size;
        return true;
    }

    public T get(int index) {
        if(index < 0 || index >= size)
            throw new ArrayIndexOutOfBoundsException();
        return (T)elements[index];
    }

    public int size() {
        return size;
    }

    public String toString() {
        String s = "";
        for(Object e : elements)
            s += ", "+e.toString();
        if(!s.isEmpty()) s = s.substring(1).trim();
        return s;
    }

    public T set(int index, T element) {
        elements[index] = element;
        return element;
    }
}
