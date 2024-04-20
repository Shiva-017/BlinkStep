package queue;

// Implementation of QueueADT using a linked list
public class Queue<T> implements QueueADT<T> {
    private Node<T> front;
    private Node<T> rear;
    private int size;

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    public Queue() {
        front = null;
        rear = null;
        size = 0;
    }

    // Adds the specified element to the rear of the queue
    // Parameters:
    //   item: the element to be added to the queue
    @Override
    public boolean enqueue(T item) {
        Node<T> newNode = new Node<>(item);
        if (isEmpty()) {
            front = newNode;
        } else {
            rear.next = newNode;
        }
        rear = newNode;
        size++;
        return true;
    }


    // Removes and returns the element at the front of the queue
    // Throws an exception if the queue is empty
    // Returns:
    //   the element removed from the front of the queue
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        T data = front.data;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        size--;
        return data;
    }

    // Returns the element at the front of the queue without removing it
    // Throws an exception if the queue is empty
    // Returns:
    //   the element at the front of the queue
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return front.data;
    }

    // Checks if the queue is empty
    // Returns:
    //   true if the queue is empty, false otherwise
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // Returns the number of elements in the queue
    // Returns:
    //   the number of elements in the queue
    @Override
    public int size() {
        return size;
    }
    
    // Retrieves all entries in the queue and returns them in a newly allocated array
    // Returns:
    //   a newly allocated array containing all the entries in the queue
    public T[] toArray() {
        @SuppressWarnings("unchecked")
        T[] result = (T[]) new Object[size];
        Node<T> current = front;
        int index = 0;
        while (current != null) {
            result[index++] = current.data;
            current = current.next;
        }
        return result;
    }

    // Returns true if the queue is full, or false if not
    // Returns:
    //   true if the queue is full, false otherwise
    private boolean isArrayFull() {
        return false;
    }
}
