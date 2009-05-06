package SimpleFactory;

public class OperationFactory 
{
	public static Operation creatOperation(String operate)
	{
		Operation oper = null;
		char cOperate = operate.charAt(0);
		
		switch(cOperate)
		{
		case '+':
			oper = new addOperation();
			break;
		case '-':
			oper = new subOperation();
			break;
		case '*':
			oper = new mulOperation();
			break;
		case '/':
			oper = new divOperation();
			break;
		}
		return oper;
	}
}
