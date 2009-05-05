package observer;

import java.util.Map;
import java.util.Set;

public class ConcerteSubject implements ISubject{
	
	private boolean isFinish = false;
	
	//private Map<String, Integer> cheetMap;
	private Set<IObserver> cheetSet;
	
	
	public ConcerteSubject() {
		
	}

	public void notifyObservers() {
		// TODO Auto-generated method stub
		for (IObserver ob : cheetSet) {
			ob.updata();
		}
	}

	public void registerObserver(IObserver o) {
		cheetSet.add(o);
	}

	public void removeObserver(IObserver o) {
		cheetSet.remove(o);
		
	}
}
