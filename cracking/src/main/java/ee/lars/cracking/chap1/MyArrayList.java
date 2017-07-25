package ee.lars.cracking.chap1;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MyArrayList<E> implements List<E> {

    private Object[] elements;
    private int elementsCount;
    private int currentIndex;

    public MyArrayList() {
        this(5);
    }

    public MyArrayList(int size) {
        this.elements = new Object[size];
        this.elementsCount = 0;
    }

    @Override
    public int size() {
        return this.elementsCount;
    }

    @Override
    public boolean isEmpty() {
        return this.elementsCount == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (Object element : this.elements) {
            if (element != null && element.equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean add(Object o) {
        if (this.currentIndex == this.elements.length - 1) {
            Object[] largerArray = new Object[this.elements.length * 2];
            System.arraycopy(this.elements, 0, largerArray, 0, this.elements.length);
            this.elements = largerArray;
        }
        this.elementsCount++;
        this.elements[this.currentIndex++] = o;
        return false;
    }

    @Override
    public boolean remove(Object o) {
        for (Object element : this.elements) {
            if (element != null && element.equals(o)) {
                this.elementsCount--;
            }
        }
        return false;
    }

    @Override
    public boolean addAll(Collection collection) {
        for (Object newElement : collection) {
            this.add(newElement);
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E get(int index) {
        return (E) this.elements[index];
    }

    @Override
    public void clear() {
        for (int i = 0; i < this.elements.length; i++) {
            this.elements[i] = null;
        }
        this.elementsCount = 0;
    }

    @Override
    public boolean addAll(int index, Collection c) {
        return false;
    }

    @Override
    public void add(int index, Object element) {

    }

    @Override
    public Object set(int index, Object element) {
        return null;
    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public boolean removeAll(Collection c) {
        return false;
    }

    @Override
    public boolean containsAll(Collection c) {
        return false;
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public ListIterator listIterator() {
        return null;
    }

    @Override
    public ListIterator listIterator(int index) {
        return null;
    }

    @Override
    public boolean retainAll(Collection c) {
        return false;
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }
}
