package hashSet;

import java.util.Iterator;

public interface HashSetADT<T> {
	 /**
     * Adds an element to the set if it is not already present.
     * 
     * @param element the element to add
     * @return true if the set did not already contain the specified element
     */
    void add(T element);

    /**
     * Removes the specified element from this set if it is present.
     * 
     * @param element the element to remove
     * @return true if the set contained the specified element
     */
    boolean remove(T element);

    /**
     * Returns true if this set contains the specified element.
     * 
     * @param element the element whose presence in this set is to be tested
     * @return true if this set contains the specified element
     */
    boolean contains(T element);

    /**
     * Returns the number of elements in this set (its cardinality).
     * 
     * @return the number of elements in this set
     */
    int size();

    /**
     * Returns an iterator over the elements in this set.
     * 
     * @return an Iterator over the elements in this set
     */
    Iterator<T> iterator();

    /**
     * Removes all of the elements from this set.
     */
    void clear();

	Object[] toArray();
}
