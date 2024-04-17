package queue;

// Interface for a generic queue ADT
public interface QueueADT<T> {
    
    // Adds the specified element to the rear of the queue
    // @param newEntry The object to be added as a new entry.
    // @return True if the addition is successful, or false if not.
    boolean enqueue(T newEntry);
    
    // Removes and returns the element at the front of the queue
    // Throws an exception if the queue is empty
    // @return The element removed from the front of the queue.
    T dequeue();
    
    // Returns the element at the front of the queue without removing it
    // Throws an exception if the queue is empty
    // @return The element at the front of the queue.
    T peek();
    
    // Checks if the queue is empty
    // @return True if the queue is empty, false otherwise.
    boolean isEmpty();
    
    // Returns the number of elements in the queue
    // @return The number of elements in the queue.
    int size();
}

