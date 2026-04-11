package simulator.model;

public interface Observable<T> {
	void addOberserver(T o);
	void removeObserver(T o);
}
