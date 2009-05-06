package Decorator;

public class Decorator 
{
	public void person()
	{}

}

class tie extends Decorator
{
	public tie()
	{}
	
	public void person()
	{
		System.out.println("tie!");
	}
}

class shirt extends Decorator
{
	public void person()
	{
		System.out.println("shirt!");
	}
}

class shoes extends Decorator
{
	public void person()
	{
		System.out.println("shoes!");
	}
}


