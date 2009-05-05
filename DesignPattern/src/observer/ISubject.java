package observer;

public interface ISubject {
	public void registerObserver(IObserver o);
	
	public void notifyObservers();
	
	public void removeObserver(IObserver o);
	
}
