package priorityQueue;

// Interface for a generic priority queue ADT
public interface PriorityQueueADT<T extends Comparable<T>> {
    
    /**
     * Adds the specified element to the priority queue.
     * 
     * @param item The element to be added to the priority queue.
     */
    void add(T item);
    
    /**
     * Removes and returns the highest priority element from the priority queue.
     * 
     * @return The highest priority element removed from the priority queue.
     * @throws IllegalStateException if the priority queue is empty.
     */
    T remove();
    
    /**
     * Returns the highest priority element from the priority queue without removing it.
     * 
     * @return The highest priority element in the priority queue.
     * @throws IllegalStateException if the priority queue is empty.
     */
    T peek();
    
    /**
     * Checks if the priority queue is empty.
     * 
     * @return true if the priority queue is empty, false otherwise.
     */
    boolean isEmpty();
    
    /**
     * Returns the number of elements in the priority queue.
     * 
     * @return The number of elements in the priority queue.
     */
    int size();
}
