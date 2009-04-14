package agents;

import java.net.Socket;
import java.text.DecimalFormat;

import util.Constant;

public class MGHuman extends Agent {

	private Agent[] agents;

	private int memoryNum;

	private int strategyNum;

	private int agentNum;

	private Socket socket;

	private double mgHumanScore;

	private double lastScore = 0;

	private boolean canWriteDadabase = true;

	private double thisTurnPrice = 0;
	
	private int humanAction = 0;
	
	private double[] humanScoreInfo = new double[3];
	
	private int turns = 0;
	
	private double totalPrice = 0;

	// private Agent agent[] = new Agent[number];

	public MGHuman(int m, int s, int n) {

		// this.agents = agent;

		this.memoryNum = m;
		this.strategyNum = s;
		this.agentNum = n;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setMemoryNum(int m) {
		this.memoryNum = m;
	}

	public int getMemoryNum() {
		return memoryNum;
	}

	public void setStrategyNum(int s) {
		this.strategyNum = s;
	}

	public int getStrategyNum() {
		return strategyNum;
	}

	public void seAgentNum(int n) {
		this.agentNum = n;
	}

	public int getAgentNum() {
		return agentNum;
	}

	/**
	 * if can ,return true,can't return false
	 * @return
	 */
	public boolean canWriteDatabase() {
		return canWriteDadabase;
	}

	public void setCanWriteDataBase(boolean b) {
		this.canWriteDadabase = b;
	}
	
	public void setHumanAction(int n) {
		this.humanAction = n;
	}
	
	public int getAction()
	{
		return this.humanAction;
	}

	public double[] getHumanScoreInfo() {

		DecimalFormat daDecimalFormat = new DecimalFormat("########.00");
		//Àƒ…·ŒÂ»Î
		humanScoreInfo[0] = Double.parseDouble(daDecimalFormat.format(humanScoreInfo[0]));
		humanScoreInfo[1] = Double.parseDouble(daDecimalFormat.format(humanScoreInfo[1]));
		humanScoreInfo[2] = Double.parseDouble(daDecimalFormat.format(humanScoreInfo[2]));
		return humanScoreInfo;
	}
	
	/**
	 * compare two mghunmen have the same m,n,s,ormaybe could extend to compare they are from the same place..
	 * @param mghuman
	 * @return
	 */
	public boolean isTheSame(MGHuman mgHuman) {
		if (!(mgHuman instanceof MGHuman)) {
			return false;
		} else if ((this.agentNum == mgHuman.getAgentNum())
				&& (this.strategyNum == mgHuman.getStrategyNum())
				&& (this.memoryNum == mgHuman.getMemoryNum())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 */
	public boolean agentAct(int mu) {

		while (!Constant.isInterfaceToGame()) {
			// DO NOTHING
		}

		Constant.setInterfaceToGame(false);

		if (Constant.interfaceToGameAction == Constant.HUMAN_PLAYER_EXIT) {
			action = 0;
			return false;
		} else {
			action = Constant.interfaceToGameAction;
			return true;
		}
	}

	/**
	 * 
	 * @param agent
	 * @return
	 */
	public boolean feedback(int thisTurnPrice) {
		//double worseHumanScore = 0;
		//double avgHumanScore = 0;
		//double bestHumanScore = 0;
		turns++;
		if (humanAction == -1) {
				mgHumanScore = mgHumanScore + thisTurnPrice;
		}else if (humanAction == 1) {
			mgHumanScore = mgHumanScore - thisTurnPrice;
		}
		if (mgHumanScore < this.humanScoreInfo[0]) {

			this.humanScoreInfo[0] = mgHumanScore;
		}

		if (mgHumanScore > this.humanScoreInfo[2]) {

			this.humanScoreInfo[2] = mgHumanScore;
		}

		this.totalPrice = this.totalPrice + mgHumanScore;

		//this.humanScoreInfo[0] = worseHumanScore;
		this.humanScoreInfo[1] = this.totalPrice / turns;
		//this.humanScoreInfo[2] = bestHumanScore;
		System.out.println("human:--best is:"+this.humanScoreInfo[2]+"avg is:"+this.humanScoreInfo[1]+"worse is:"+this.humanScoreInfo[0]);
		return true;
	}

	public void caculateScore(int thisturnscore) {
		mgHumanScore = mgHumanScore + thisturnscore;
	}

	public double getScore() {
		// TODO Auto-generated method stub
		return mgHumanScore;
	}

}