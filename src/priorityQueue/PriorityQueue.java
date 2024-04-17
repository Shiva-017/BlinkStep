package priorityQueue;

import java.util.ArrayList;
import java.util.List;

// Implementation of PriorityQueueADT using a max heap
public class PriorityQueue<T extends Comparable<T>> implements PriorityQueueADT<T> {
    private List<T> heap;

    // Constructs an empty priority queue
    public PriorityQueue() {
        heap = new ArrayList<>();
    }

    /**
     * Adds the specified element to the priority queue.
     * 
     * @param item The element to be added to the priority queue.
     */
    @Override
    public void add(T item) {
        heap.add(item);
        bubbleUp(heap.size() - 1);
    }

    /**
     * Removes and returns the highest priority element from the priority queue.
     * 
     * @return The highest priority element removed from the priority queue.
     * @throws IllegalStateException if the priority queue is empty.
     */
    @Override
    public T remove() {
        if (isEmpty()) {
            throw new IllegalStateException("Priority queue is empty");
        }
        T removedItem = heap.get(0);
        int lastIndex = heap.size() - 1;
        heap.set(0, heap.get(lastIndex));
        heap.remove(lastIndex);
        if (!isEmpty()) {
            trickleDown(0);
        }
        return removedItem;
    }

    /**
     * Returns the highest priority element from the priority queue without removing it.
     * 
     * @return The highest priority element in the priority queue.
     * @throws IllegalStateException if the priority queue is empty.
     */
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Priority queue is empty");
        }
        return heap.get(0);
    }

    /**
     * Checks if the priority queue is empty.
     * 
     * @return true if the priority queue is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    /**
     * Returns the number of elements in the priority queue.
     * 
     * @return The number of elements in the priority queue.
     */
    @Override
    public int size() {
        return heap.size();
    }

    // Helper method to maintain heap property by bubbling up
    private void bubbleUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap.get(index).compareTo(heap.get(parentIndex)) <= 0) {
                break;
            }
            swap(index, parentIndex);
            index = parentIndex;
        }
    }

    // Helper method to maintain heap property by trickling down
    private void trickleDown(int index) {
        int leftChildIndex = index * 2 + 1;
        int rightChildIndex = index * 2 + 2;
        int maxIndex = index;

        if (leftChildIndex < heap.size() && heap.get(leftChildIndex).compareTo(heap.get(maxIndex)) > 0) {
            maxIndex = leftChildIndex;
        }
        if (rightChildIndex < heap.size() && heap.get(rightChildIndex).compareTo(heap.get(maxIndex)) > 0) {
            maxIndex = rightChildIndex;
        }
        if (maxIndex != index) {
            swap(index, maxIndex);
            trickleDown(maxIndex);
        }
    }

    // Helper method to swap elements at two indices in the heap
    private void swap(int index1, int index2) {
        T temp = heap.get(index1);
        heap.set(index1, heap.get(index2));
        heap.set(index2, temp);
    }
}
