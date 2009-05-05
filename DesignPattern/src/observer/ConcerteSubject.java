package observer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ConcerteSubject implements ISubject{
	
	private boolean isFinish = false;
	
	//private Map<String, Integer> cheetMap;
	private HashSet<IObserver> cheetSet;
	
	
	public ConcerteSubject() {
		cheetSet = new HashSet<IObserver>();
	}

	public void notifyObservers() {
		// TODO Auto-generated method stub
		for (IObserver ob : cheetSet) {
			ob.updata();
		}
	}

	public void registerObserver(IObserver o) {
		System.out.println(o.getClass().toString());
		try {
			cheetSet.add(o);
		} catch (Exception e) {
			System.out.println("exceptiong is:"+e.getLocalizedMessage());
		}
	}

	public void removeObserver(IObserver o) {
		if (cheetSet.isEmpty()) {
			System.out.println("no observer at all...");
		}else {
			cheetSet.remove(o);
		}
	}
	
	public void haveFinshed(boolean finish)
	{
		this.isFinish = finish;
		if (this.isFinish) {
			notifyObservers();
		}
	}
}
