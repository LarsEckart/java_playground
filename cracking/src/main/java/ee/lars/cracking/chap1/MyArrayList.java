package ee.lars.cracking.chap1;

import java.util.*;

public class MyArrayList<E> extends AbstractList<E> {

public static final int DEFAULT_INITIAL_CAPACITY = 5;
private Object[] elements;
    private int size;
    private int currentIndex;

    public MyArrayList() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public MyArrayList(int initialCapacity) {
        this.elements = new Object[initialCapacity];
        this.size = 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
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
            expand();
        }
        this.size++;
        this.elements[this.currentIndex++] = o;
        return false;
    }

    private void expand() {
        Object[] largerArray = new Object[this.elements.length * 2];
        System.arraycopy(this.elements, 0, largerArray, 0, this.elements.length);
        this.elements = largerArray;
    }

    @Override
    public boolean remove(Object o) {
        for (Object element : this.elements) {
            if (element != null && element.equals(o)) {
                this.size--;
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
        this.size = 0;
    }

    @Override
    public void add(int index, Object element) {
        if (this.elements[this.elements.length - 1] != null || index > this.elements.length - 1) {
            expand();
            // TODO: expanding once might not be enough
        }
        for (int i = this.elements.length - 1; i > index; i--) {
            this.elements[i] = this.elements[i - 1];
            this.elements[i - 1] = null;
        }
        this.elements[index] = element;
    }

    @Override
    public boolean addAll(int index, Collection c) {
        for (Object o : c) {

        }
        return false;
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
    public Object[] toArray() {
        return new Object[0];
    }
}
