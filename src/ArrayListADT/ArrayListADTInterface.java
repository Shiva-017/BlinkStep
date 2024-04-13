package ArrayListADT;

public interface ArrayListADTInterface<T> {
	 void add(T item);
	 T get(int index);
//	 boolean remove(T item);
	 int size();
	T[] toArray(); 
}
