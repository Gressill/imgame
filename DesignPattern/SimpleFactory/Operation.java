package SimpleFactory;

public class Operation 
{
	protected double dNumberA;
	protected double dNumberB;
	
	public Operation()
	{
	}
	
	public Operation(double numA,double numB)
	{
		dNumberA = numA;
		dNumberB = numB;
	}
	
	public double getResult()
	{
		double result = 0;
		return result;
	}

}

class addOperation extends Operation
{	
	public double getResult()
	{
		return dNumberA + dNumberB;
	}
}

class subOperation extends Operation
{
	public double getResult()
	{
		return dNumberA - dNumberB;
	}
}

class mulOperation extends Operation
{
	public double getResult()
	{
		return dNumberA * dNumberB;
	}
}

class divOperation extends Operation
{
	public double getResult()
	{
//		if (dNumberB == 0) 
//		{
//			;
//		}
//		else 
//		{
//			return dNumberA / dNumberB;
//		}
		try 
		{
			return dNumberA / dNumberB;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return 0;
			// TODO: handle exception
		}
	}
}

