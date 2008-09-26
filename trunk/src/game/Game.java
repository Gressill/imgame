package game;

import agent.Strategy;

public class Game implements Strategy {

	private int price;

	/*
	 * loading history price
	 */
	public void loadHistory() {

	}

	/**
	 * Initialize the agent strategy
	 */
	public Strategy initStrategy() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * lock the history
	 */
	private void lockHistory() {

	}

	private void updateHistory() {

	}

	private void releaseHistory() {

	}

	public void gainAction() {

		lockHistory();
		updateHistory();
		releaseHistory();
	}

}