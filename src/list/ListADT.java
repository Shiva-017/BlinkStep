package list;


public interface ListADT<T> extends Iterable<T> {
	 void add(T element);
	 
	 int size();

	 ListADT<T> toStandardList();
	
	 void addAll(ArrayList<T> items);
	 
	 T get(int index);

	 //	 boolean remove(T item);
	 
	 T remove(int n);
	 
	 T[] toArray(); 
	 
}
