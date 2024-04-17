package arrayList;
import java.util.ArrayList;

public interface ArrayListADTInterface<T> {
	 void add(T item);
	 void addAll(ArrayList<T> items);
	 T get(int index);
//	 boolean remove(T item);
	 T remove(int n);
	 int size();
	T[] toArray(); 
}
