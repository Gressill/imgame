package agents;

public abstract class Agent {

	protected double action = 0;//
	protected double gain = 0;//
	protected int[][] determining;//

	public void setGain(double gain) {
		
		this.gain = gain;
	}

	public double getGain() {
		
		return gain;
	}

	public void setDetermin(int[][] det) {
		
		this.determining = det;

	}

	public int[][] getDetermin() {

		return determining;
	}

	public boolean agentAct(int historyChoise) {
		return false;
	}
	
	public double getAction() {
		
		return action;
	}

	/**
	 * 
	 * receive feedback message such as maxprice,maxgain,minprice,mingain,avgprice,avgain
	 * 
	 */

	public boolean feedback(int historyChoise,int thisTurnPrice,int i) {

		return true;
	}

}
