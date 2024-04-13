package ArrayListADT;

public class ArrayListADT<T> implements ArrayListADTInterface<T> {
	private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 10;

    public ArrayListADT() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    @Override
    public void add(T item) {
        ensureCapacity();
        elements[size++] = item;
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (T) elements[index];
    }

//    @Override
//    public boolean remove(T item) {
//        for (int i = 0; i < size; i++) {
//            if (item.equals(elements[i])) {
//                int numMoved = size - i - 1;
//                if (numMoved > 0) {
//                    System.arraycopy(elements, i + 1, elements, i, numMoved);
//                }
//                elements[--size] = null; // clear to let GC do its work
//                return true;
//            }
//        }
//        return false;
//    }
    
    public T[] toArray() {
        T[] newArray = (T[]) new Object[size]; // This cast is unchecked, warning can be suppressed
        System.arraycopy(elements, 0, newArray, 0, size);
        return newArray;
    }
    
    @Override
    public int size() {
        return size;
    }

    private void ensureCapacity() {
        if (size == elements.length) {
            Object[] newBiggerArray = new Object[elements.length * 2];
            System.arraycopy(elements, 0, newBiggerArray, 0, elements.length);
            elements = newBiggerArray;
        }
    }

}
