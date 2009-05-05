package client;

import observer.ConcerteObserver;
import observer.ConcerteSubject;

public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ConcerteSubject concerteSubject = new ConcerteSubject();
		ConcerteObserver wang = new ConcerteObserver(concerteSubject);
		wang.setName("Wang");
		ConcerteObserver li = new ConcerteObserver(concerteSubject);
		li.setName("Li");
		concerteSubject.haveFinshed(true);
		concerteSubject.removeObserver(wang);
		concerteSubject.haveFinshed(true);
	}

}
