package observer;

import java.io.PrintWriter;

public class ConcerteObserver implements IObserver{
	
	private ISubject iSubject;
	private String myName;
	
	public ConcerteObserver(){
		
	}
	
	public ConcerteObserver(ISubject s) {
		this.iSubject = s;
		iSubject.registerObserver(this);
	}
	
	public void setName(String name)
	{
		this.myName = name;
	}

	public void updata() {
		// TODO Auto-generated method stub
		System.out.println("I am "+this.myName+" I have got answer..");
	}

}
