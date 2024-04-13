package LinkedListADT;
import java.util.ArrayList;
import java.util.List;

public class LinkedListADT<T> implements LinkedListADTInterface<T> {
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

		public LinkedListADT() {
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
		    public List<T> toStandardList() {
		        List<T> list = new ArrayList<>();
		        Node<T> current = head;
		        while (current != null) {
		            list.add(current.data);
		            current = current.next;
		        }
		        return list;
		    }

}
