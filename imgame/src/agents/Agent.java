package agents;

public abstract class Agent {

	protected int action;//
	protected double gain = 0;//
	protected int[][] determin;//

	public void setGain(double gain) {
		
		this.gain = gain;
	}

	public double getGain() {
		
		return gain;
	}

	public void setDetermin(int[][] det) {
		
		this.determin = det;

	}

	public int[][] getDetermin() {

		return determin;
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
	public boolean feedback(int currentChoise,int historyChoise) {

		return true;
	}

}
