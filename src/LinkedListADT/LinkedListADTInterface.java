package LinkedListADT;

public interface LinkedListADTInterface<T> {
	 void add(T element);
	 int size();
	 java.util.List<T> toStandardList();
}
