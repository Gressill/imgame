package agent;

public abstract class Agent {

	protected int action;//
	protected double gain = 0;//
	protected int[][] determin;//

	protected void setGain(int gain) {
		
		this.gain = gain;
	}

	protected double getGain() {
		
		return gain;
	}

	public void setDetermin(int[][] det) {
		
		this.determin = det;

	}

	public int[][] getDetermin() {

		return determin;
	}

	private void agentAct() {
		//
	}

	/**
	 * 
	 * receive feedback message such as maxprice,maxgain,minprice,mingain,avgprice,avgain
	 * 
	 */
	protected boolean feedBack() {

		return true;
	}

}
