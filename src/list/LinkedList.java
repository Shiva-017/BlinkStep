package list;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<T> implements ListADT<T> {
	 private Node<T> head;
	    private Node<T> tail;
	    private int size = 0;

	    private static class Node<T> {
	        T data;
	        Node<T> next;

	        Node(T data) {
	            this.data = data;
	            this.next = null;
	        }
	    }

		public LinkedList() {
			// TODO Auto-generated constructor stub
		}

		@Override
	    public void add(T data) {
	        Node<T> newNode = new Node<>(data);
	        if (head == null) {
	            head = newNode;
	            tail = newNode;
	        } else {
	            tail.next = newNode;
	            tail = newNode;
	        }
	        size++;
	    }
		
		 @Override
		    public int size() { return size; }

		@Override
	    public void addAll(ArrayList<T> items) {
	        for (int i = 0; i < items.size(); i++) {
	            this.add(items.get(i));
	        }
	    }

		@Override
	    public T get(int index) {
	        if (index < 0 || index >= size) {
	            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
	        }
	        Node<T> current = head;
	        for (int i = 0; i < index; i++) {
	            current = current.next;
	        }
	        return current.data;
	    }

		@Override
	    public T remove(int index) {
	        if (index < 0 || index >= size) {
	            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
	        }
	        Node<T> prev = null;
	        Node<T> current = head;
	        if (index == 0) {
	            head = head.next;
	            if (head == null) {
	                tail = null;
	            }
	        } else {
	            for (int i = 0; i < index; i++) {
	                prev = current;
	                current = current.next;
	            }
	            prev.next = current.next;
	            if (prev.next == null) {
	                tail = prev;
	            }
	        }
	        size--;
	        return current.data;
	    }

		@Override
	    public T[] toArray() {
	        @SuppressWarnings("unchecked")
	        T[] array = (T[])new Object[size];
	        Node<T> current = head;
	        int index = 0;
	        while (current != null) {
	            array[index++] = current.data;
	            current = current.next;
	        }
	        return array;
	    }

	    @Override
	    public ListADT<T> toStandardList() {
	    	ListADT<T> list = new ArrayList<>();
	        Node<T> current = head;
	        while (current != null) {
	            list.add(current.data);
	            current = current.next;
	        }
	        return list;
	    }

		@Override
	    public Iterator<T> iterator() {
	        return new Iterator<T>() {
	            private Node<T> current = head;

	            @Override
	            public boolean hasNext() {
	                return current != null;
	            }

	            @Override
	            public T next() {
	                if (!hasNext()) {
	                    throw new NoSuchElementException();
	                }
	                T data = current.data;
	                current = current.next;
	                return data;
	            }
	        };
	    }
		
		public boolean contains(T data) {
		    Node<T> current = head;
		    while (current != null) {
		        if (current.data.equals(data)) {
		            return true;
		        }
		        current = current.next;
		    }
		    return false;
		}
		
		public boolean remove(T data) {
	        if (head == null) return false; 

	        if (head.data.equals(data)) {
	            head = head.next;
	            if (head == null) {
	                tail = null; 
	            }
	            size--;
	            return true;
	        }

	        Node<T> current = head;
	        Node<T> prev = null;

	        while (current != null && !current.data.equals(data)) {
	            prev = current;
	            current = current.next;
	        }

	        if (current == null) {
	            return false; 
	        }

	        // Remove the element
	        prev.next = current.next;
	        if (prev.next == null) {
	            tail = prev; 
	        }
	        size--;

	        return true;
	    }

		public void clear() {
			// TODO Auto-generated method stub
			
		}

}
