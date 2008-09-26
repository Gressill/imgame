package agent;

import java.lang.Math;
import util.Constant;

public class MGAgent extends Agent {
	
	private int historySize; // Size of history space = (actionChooseNumber^memorySize) where agent looks default as 27
	// at last M time steps

	private int strategiesNum; // Number of strategies default as 2

	private int[][] strategiesArray; // The strategies: an (strategiesNum*historySize) array

	private double[] virtualScores; // The strategies' virtual scores

	private int[] determining; // Array determining active strategy

	public MGAgent(int MEMORY, int STRATEGIES) {

		historySize = 1 << MEMORY; // Size of history space
		strategiesNum = STRATEGIES;

		strategiesArray = new int[strategiesNum][historySize]; // The strategies: an (strategiesNum*historySize) array

		virtualScores = new double[strategiesNum];
		determining = new int[strategiesNum];
		
		this.InitStrategy();
	}
	
	/**
	 * init strategies
	 * 
	 * return strategies array
	 */
	public int[][] InitStrategy() {
		
		for (int i = 0; i < strategiesNum; ++i) {
			// virtualScores[i] = 0;
			for (int j = 0; j < historySize; ++j) {
				// this is how the game works,use random num to simulate the
				// people's select & init strategies
				strategiesArray[i][j] = (Math.random() < 0.5) ? -1 : 1;
			}
		}
		
		return strategiesArray;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see agent.Agent#act(int)
	 */
	public boolean agentAct(int mu) {
		//
		action = strategiesArray[determining[0]][mu % historySize];
		return true;
	}

	/**
	 * update virtualscores and decide determining
	 * 
	 * @see agent.Agent#feedback(double, int)
	 */

	public boolean feedback(double A, int mu) {

		for (int i = 0; i < strategiesNum; ++i) {

			virtualScores[i] = virtualScores[i] - strategiesArray[i][mu % historySize] * A;

		}

		int ns = 1;

		// 根据历史虚分值选择策略
		for (int i = 0; i < strategiesNum; ++i) {

			if (virtualScores[i] > virtualScores[determining[0]]) {

				determining[0] = i;
				ns = 1;

			} else if ((virtualScores[i] == virtualScores[determining[0]])
					&& (i != determining[0])) {

				determining[ns] = i;
				++ns;

			}
		}

		if (ns > 1) {
			
			determining[0] = determining[(int) (ns * Math.random())];
		}
		return true;
		// Math.random() //this will produce a random number between 0 and 1
	}

	public double getAction() {
		return this.action;
	}
}
