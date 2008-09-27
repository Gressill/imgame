package util;

import java.util.Queue;

/**
 * 
 * @author zico this is the Constant file
 */
public class Constant {

	final public static int timeStepNumber = 50;
//
//	final public static int heightStepNumber = 10;
//
//	final public static int heightWordOffset = 45;
//
//	final public static int heightRightWordOffset = 20;
//
//	final public static int heightLineOffset = 10;
//
//	final public static String lineRedDesription = "Last price move";
//	
//	final public static String lineGreenDesription = "Price history";
//
//	final public static String chartTitle = "PRICE CHART";
//
//	final public static String levelTitle = "Guru(Level 4)";
	
	public static int actionChooseNumber = 3;//we have buy,sell,hold

	public static int agentNumber = 94;
	
	public static int memorySize = 3;
	
	public static int strategySize = 2;
	
	public static int port = 8888;
//
//	final public static double postionWidthStartMarginScale = 0.05;
//
//	final public static double postionWidthEndMarginScale = 0.25;
//
//	final public static double postionHeightStartMarginScale = 0.10;
//
//	final public static double postionHeightEndMarginScale = 0.40;

	final public static int HUMAN_PLAYER_EXIT = 999;

	final public static int maxHeightOfDraw = 1000;

	final public static boolean playingFlagOfHumanPlayer = true;

	private static boolean interfaceToGame = false;

	private static boolean gameToInterface = false;

	public static int interfaceToGameAction = 0;

	public static double gameToInterfaceA = 0;

	public static double gameToInterfaceGain = 0;
	
	public static double agentGainMax = 0;
	public static double agentGainMin = 0;
	public static double agentGainAv = 0;

	public static synchronized boolean isInterfaceToGame() {
		return interfaceToGame;
	}

	public static synchronized void setInterfaceToGame(boolean interfaceToGame) {
		Constant.interfaceToGame = interfaceToGame;
	}

	public static synchronized boolean isGameToInterface() {
		return gameToInterface;
	}

	public static synchronized void setGameToInterface(boolean gameToInterface) {
		Constant.gameToInterface = gameToInterface;
	}

	// the configuration text of the Applet

	public static String score = "SCORE";

	public static String perturn = "per turn";

	public static String agentsComparison = "agents(" + agentNumber
			+ ") comparison:";

	public static String agentsComparisonBest = "best:";

	public static String agentsComparisonAvg = "avg :";

	public static String agentsComparisonWorst = "worst:";

	public static String playedTurns = "turns";

	public static String stopGame = "STOP GAME";

	public static String startGame = "START GAME";

	public static String waitingMsg = "We are playing.... please not disturb";

	public static String numberOfTurnsShouldPlay = "You mush play 200+ turns to challenge for the hiscore.";

	public static String betMsg = "Bet on next price move";
	
	public static int buyers = 0;

	public static int sellers = 0;
	
	


}
