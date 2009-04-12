package util;

import java.util.Queue;

/**
 * 
 * @author zico this is the Constant file
 */
public class Constant {

	final public static int timeStepNumber = 50;
//	final public static String levelTitle = "Guru(Level 4)";
	
	public static int actionChooseNumber = 3;//we have buy,sell,hold

	public static int agentNumber = 94;
	
	public static int memorySize = 3;
	
	public static int strategySize = 2;
	
	public static int differetnHumNum = 0;
	
	public static int port = 8888;
	
	public static boolean keepPlaying = true;
	
	public static int userChoise = 0;
	
	public static int buyChoise = 1;
	public static int sellChoise = -1;
	public static int holdChoise = 0;
	
	public static int dVolatility = 5;
	
	final public static String HISTORY_PRICE_FILE = "src/xmlfile/history.xml";
	
	final public static String AGENT_INFO_FILE = "src/xmlfile/AgentInfo.xml";

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

	// the configuration text of the Apple

	public static String waitingMsg = "We are playing.... please not disturb";

	public static String numberOfTurnsShouldPlay = "You mush play 200+ turns to challenge for the hiscore.";
	
	public static final String CORSS_DOMAIN = "<?xml version=\"1.0\"?><!DOCTYPE cross-domain-policy SYSTEM \"http://www.macromedia.com/xml/dtds/cross-domain-policy.dtd\"><cross-domain-policy><allow-access-from domain=\"*\" to-ports=\"*\" /></cross-domain-policy>";

	public static String betMsg = "Bet on next price move";
	
	public static int buyers = 0;

	public static int sellers = 0;
	
	


}
