package game;

import util.Constant;
import game.Game;
import agents.Agent;
import agents.MGAgent;
import agents.MGHuman;

public class Img {

	Game game;

	Agent[] agents;

	private int[] initCurrentChoise;
	
	private String getParameter(String x) {
		return "test";
	}

	public void init() {

		try {
			//用户没填m,s,n的时候用默认数据3，2，95
//			Constant.memorySize = Integer
//					.parseInt((this.getParameter("memory") == null) ? String
//							.valueOf(Constant.memorySize) : this
//							.getParameter("memory"));			
			Constant.memorySize = Integer.parseInt(String.valueOf(Constant.memorySize) );
			Constant.strategySize = Integer.parseInt( String.valueOf(Constant.strategySize));
			Constant.agentNumber = Integer.parseInt(String.valueOf(Constant.agentNumber));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		// TODO change the agentNumber to configured value
		agents = new Agent[Constant.agentNumber + 1]; // number of
		// computer-controlled
		// agents PLUS human

		//初始化agent
		for (int i = 0; i < agents.length; i++) {
			agents[i] = new MGAgent(Constant.memorySize, Constant.strategySize);
		}
		
		// game plays once to initialize the Graph
		//game = new Game(agents, 0, 200 * (1 << 3));
		game = new Game(agents, 0, 100);//third pram will be the number of agents
		game.playGame();
		
		
		double[] price = new double[Constant.timeStepNumber + 1];
		initCurrentChoise = game.getCurrentChoise();

		
		price[0] = 0;
		//
		for (int i = 0; i < price.length - 1; ++i) {
			price[i + 1] = price[i] + initCurrentChoise[(initCurrentChoise.length - price.length - 1) + i];
			//System.out.println("price["+i+"]"+price[i]);
			//System.out.println((initCurrentChoise.length - price.length - 1) + i);
		}

		price = null;
		initCurrentChoise = null;

		agents[agents.length - 1] = new MGHuman(agents);

		for (int i = 0; i < agents.length; ++i) {
			//agents[i].setGain(0);
		}

		game = new Game(agents, 0, 100);

	}

	public void destroy() {
		initCurrentChoise = null;

		agents = null;
		game = null;

	}

	public String getAppletInfo() {
		return "Title: Interactive Minority Game \nAuthor: <f>";
	}

	


	public static void main(String[] args) {

		Img ImgTest = new Img();
	
		ImgTest.init();
	}
}
