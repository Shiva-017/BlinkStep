package hashSet;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import hashMap.HashMap.Entry;

public class HashSet<T> implements HashSetADT<T>,Iterable<T> {
	private LinkedList<T>[] buckets;
    private int capacity=10;  // Total number of buckets
    private int size;      // Total number of elements in the hash set

    @SuppressWarnings("unchecked")
    public HashSet(int capacity) {
        this.capacity = capacity;
        this.buckets = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            buckets[i] = new LinkedList<>();
        }
    }
    
    public HashSet() {
        this.buckets = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    private int getBucketIndex(T key) {
        int hashCode = key.hashCode();
        return Math.abs(hashCode) % capacity;
    }

    public void add(T key) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<T> bucket = buckets[bucketIndex];
        if (!bucket.contains(key)) {
            bucket.add(key);
            size++;
        }
    }

    public boolean contains(T key) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<T> bucket = buckets[bucketIndex];
        return bucket.contains(key);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int currentBucket = 0;
            private Iterator<T> bucketIterator = (buckets[currentBucket] != null) ? buckets[currentBucket].iterator() : Collections.<T>emptyIterator();

            @Override
            public boolean hasNext() {
                // Ensures that we move to the next non-empty bucket
                while ((bucketIterator == null || !bucketIterator.hasNext()) && currentBucket < capacity - 1) {
                    currentBucket++;
                    if (buckets[currentBucket] != null) {
                        bucketIterator = buckets[currentBucket].iterator();
                    }
                }
                return bucketIterator != null && bucketIterator.hasNext();
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return bucketIterator.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Remove operation is not supported");
            }
        };
    }
        
	@Override
	public boolean remove(T element) {
		// TODO Auto-generated method stub
        int bucketIndex = getBucketIndex(element);
        LinkedList<T> bucket = buckets[bucketIndex];
        boolean wasPresent = bucket.remove(element);
        if (wasPresent) {
            size--;
        }
        return wasPresent;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		 return size;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
        for (int i = 0; i < capacity; i++) {
            buckets[i].clear();
        }
        size = 0;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
        Object[] array = new Object[size];
        int index = 0;
        for (LinkedList<T> bucket : buckets) {
            for (T element : bucket) {
                array[index++] = element;
            }
        }
        return array;
	}

	public void addAll(LinkedList<T> newElements) {
        for (T element : newElements) {
            add(element);
        }
		
	}
}
