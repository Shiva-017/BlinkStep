package hashMap;

import java.util.Collection;
import java.util.LinkedList;

import hashSet.HashSet;

public class HashMap<K,V> implements HashMapADT<K,V> {

	private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private LinkedList<Entry<K, V>>[] table;
    private int size = 0;
    private int capacity;
    private final float loadFactor;

    public HashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public HashMap(int initialCapacity, float loadFactor) {
        this.capacity = initialCapacity;
        this.loadFactor = loadFactor;
        table = new LinkedList[capacity];
    }

    public static class Entry<K, V> {
        final K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }

		public V getValue() {
			return value;
		}

		public K getKey() {
			return key;
		}
        
        
    }

    private int hash(K key) {
        return (key == null) ? 0 : Math.abs(key.hashCode() % capacity);
    }

    public void put(K key, V value) {
        int index = hash(key);
        if (table[index] == null) {
            table[index] = new LinkedList<>();
        }

        for (Entry<K, V> entry : table[index]) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }

        table[index].add(new Entry<>(key, value));
        size++;
        if (size > capacity * loadFactor) {
            resize();
        }
        
        
    }

    public V get(K key) {
        int index = hash(key);
        LinkedList<Entry<K, V>> bucket = table[index];
        if (bucket != null) {
            for (Entry<K, V> entry : bucket) {
                if (entry.key.equals(key)) {
                    return entry.value;
                }
            }
        }
        return null;
    }

    public boolean containsKey(K key) {
        int index = hash(key);
        LinkedList<Entry<K, V>> bucket = table[index];
        if (bucket != null) {
            for (Entry<K, V> entry : bucket) {
                if (entry.key.equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean containsValue(V value) {
        for (LinkedList<Entry<K, V>> bucket : table) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) {
                    if (entry.value.equals(value)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public HashSet<Entry<K, V>> entrySet() {
    	HashSet<Entry<K, V>> entrySet = new HashSet<>();
        for (LinkedList<Entry<K, V>> bucket : table) {
            if (bucket != null) {
                entrySet.addAll(bucket);
            }
        }
        return entrySet;
    }

    public HashSet<K> keySet() {
    	HashSet<K> keySet = new HashSet<>();
        for (LinkedList<Entry<K, V>> bucket : table) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) {
                    keySet.add(entry.key);
                }
            }
        }
        return keySet;
    }
    
    public Collection<K> keySetCollect() {
    	HashSet<K> keySet = new HashSet<>();
        for (LinkedList<Entry<K, V>> bucket : table) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) {
                    keySet.add(entry.key);
                }
            }
        }
        return  (Collection<K>) keySet;
    }

    private void resize() {
        capacity *= 2;
        LinkedList<Entry<K, V>>[] newTable = new LinkedList[capacity];
        for (LinkedList<Entry<K, V>> bucket : table) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) {
                    int index = hash(entry.key);
                    if (newTable[index] == null) {
                        newTable[index] = new LinkedList<>();
                    }
                    newTable[index].add(entry);
                }
            }
        }
        table = newTable;
    }

	@Override
	public V remove(K key) {
		// TODO Auto-generated method stub
        int index = hash(key);
        LinkedList<Entry<K, V>> bucket = table[index];
        if (bucket != null) {
            for (Entry<K, V> entry : bucket) {
                if (entry.key.equals(key)) {
                    V value = entry.value;
                    bucket.remove(entry);
                    size--;
                    return value;
                }
            }
        }
        return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		 return size == 0;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) {
                table[i].clear();
            }
        }
        size = 0;
	}


}