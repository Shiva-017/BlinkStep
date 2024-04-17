package hashSet;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class HashSetADT<T> implements HashSetADTInterface<T>,Iterable<T> {
	private LinkedList<T>[] buckets;
    private int capacity=10;  // Total number of buckets
    private int size;      // Total number of elements in the hash set

    @SuppressWarnings("unchecked")
    public HashSetADT(int capacity) {
        this.capacity = capacity;
        this.buckets = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            buckets[i] = new LinkedList<>();
        }
    }
    
    public HashSetADT() {
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
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
}
