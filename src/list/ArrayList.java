<<<<<<<< HEAD:src/arrayList/ArrayListADT.java
package arrayList;
========
package list;
>>>>>>>> 44ae520ab66793224ac93d108bd572cef25ee7a9:src/list/ArrayList.java

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayList<T> implements ListADT<T> {
	private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 10;

    public ArrayList() {
        elements = new Object[DEFAULT_CAPACITY];
    }
    
    public ArrayList(int size) {
        elements = new Object[size];
    }
    
    public ArrayList(ListADT<T> items) {
        elements = new Object[DEFAULT_CAPACITY];
        for(int i=0; i<items.size(); i++) {
        	this.add(items.get(i));
        }
    }

    @Override
    public void add(T item) {
        ensureCapacity();
        elements[size++] = item;
    }
    
    @Override
    public void addAll(ArrayList<T> items) {
    	for(int i=0; i<items.size(); i++) {
    		this.add(items.get(i));
    	}
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (T) elements[index];
    }
    
    @Override
    public T remove(int n) {
    	for (int i = 0; i<size; i++) {
    		if(i==n) {
    			int numMoved = size - i - 1;
              if (numMoved > 0) {
                  System.arraycopy(elements, i + 1, elements, i, numMoved);
              }
              @SuppressWarnings("unchecked")
			T element = (T) elements[i];
              elements[--size] = null;
              return element;
    		}
    	}
    	return null;
    }
    
    public T[] toArray() {
        @SuppressWarnings("unchecked")
		T[] newArray = (T[]) new Object[size];
        System.arraycopy(elements, 0, newArray, 0, size);
        return newArray;
    }
    
    @SuppressWarnings("unchecked")
	public ArrayList<T> toArrayList() {
        ArrayList<T> newArray = new ArrayList<>(size); 
        for (int i = 0; i < size; i++) {
            newArray.add((T) elements[i]); 
        }
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

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
        return new Iterator<T>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (T) elements[currentIndex++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Remove not supported");
            }
        };
	}

	@Override
	public ListADT<T> toStandardList() {
		// TODO Auto-generated method stub
        ListADT<T> standardList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            standardList.add(this.get(i));
        }
        return standardList;
	}


}
