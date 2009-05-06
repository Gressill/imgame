package CashSuper;


public class cashBack 
{
	protected double money = 0;
	
	public cashBack() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public double abate(double times)
	{
		//double money = 0;
		return money*times;
	}
	
	public double rebate(int tMoney,int bMoney)
	{
		//double money = 0;
		if(money>tMoney)
		{
			money = money - (money%tMoney)*bMoney;
		}
		return money;
	}
}
