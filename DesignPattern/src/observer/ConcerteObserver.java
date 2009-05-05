package observer;

import java.io.PrintWriter;

public class ConcerteObserver implements IObserver{
	
	public ConcerteObserver(ISubject s) {
		s.registerObserver(this);
	}

	public void updata() {
		// TODO Auto-generated method stub
		System.out.println("got it,i have finished..");
	}

}
