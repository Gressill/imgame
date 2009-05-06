package Decorator;

public class DecFactory 
{
	public Decorator creatDecorator(char dec)
	{
		Decorator myDec =  new Decorator();
		//myDec = dec;
		//Enum<myDec> = {"tie","shoes","shirt"};
		switch (dec) 
		{
		case '0':
			
			break;

		default:
			break;
		}
		return myDec;
	}
}
