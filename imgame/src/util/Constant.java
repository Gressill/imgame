package util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 
 * @author zico this is the Constant file
 */
public class Constant {

	final public static int timeStepNumber = 50;
	// final public static String levelTitle = "Guru(Level 4)";

	public static int actionChooseNumber = 3;// we have buy,sell,hold

	public static int agentNumber = 94;

	public static int memorySize = 3;

	public static int strategySize = 2;

	public static int differetnHumNum = 0;

	public static boolean keepPlaying = true;

	public static int userChoise = 0;

	public static int buyChoise = 1;
	public static int sellChoise = -1;
	public static int holdChoise = 0;

	public static int dVolatility = 5;

	public static String LOG_PATH;

	final public static String HISTORY_PRICE_FILE = "src/xmlfile/history.xml";

	final public static String AGENT_INFO_FILE = "src/xmlfile/AgentInfo.xml";

	final public static int HUMAN_PLAYER_EXIT = 999;

	final public static int maxHeightOfDraw = 1000;

	final public static boolean playingFlagOfHumanPlayer = true;

	// the configuration text of the Apple

	public static String waitingMsg = "We are playing.... please not disturb";

	public static String numberOfTurnsShouldPlay = "You mush play 200+ turns to challenge for the hiscore.";

	public static final String CORSS_DOMAIN = "<?xml version=\"1.0\"?><!DOCTYPE cross-domain-policy SYSTEM \"http://www.macromedia.com/xml/dtds/cross-domain-policy.dtd\"><cross-domain-policy><allow-access-from domain=\"*\" to-ports=\"*\" /></cross-domain-policy>";

	public static String betMsg = "Bet on next price move";

	public static int buyers = 0;

	public static int sellers = 0;

	// database string
	public static String DB_DATABASE;
	public static String DB_USER_NAME;
	public static String DB_PASSWORD;
	public static int port;
	public static int PRICE_BUFFFER_SIZE = 200;
	public static String DB_IP;

	// public final static String DB_USER_NAME = "root";
	// public final static String DB_PASSWORD = "uestc";

	/**
	 * this function use to read the config.xml file to initial the game
	 * parameter,all this parameter in Constant file
	 * 
	 */
	public static boolean initGameFromXml() {

		ArrayList<Double> parameterList = null;
		// long lasting = System.currentTimeMillis();
		try {
			//get path
			String userdirString = System.getProperty("user.dir");
			File configfile = new File(userdirString+"/config.xml");
			SAXReader reader = new SAXReader();
			Document doc = reader.read(configfile);
			Element root = doc.getRootElement();
			port = Integer.parseInt(root.element("port").getTextTrim());
			DB_DATABASE = root.element("database").getTextTrim();
			DB_USER_NAME = root.element("databaseusername").getTextTrim();
			DB_PASSWORD = root.element("databasepassword").getTextTrim();
			DB_IP = root.element("databaseip").getTextTrim();
			LOG_PATH = root.element("logpath").getTextTrim();
			//System.out.println("port:" + port + "\ndatabase" + DB__DATABASE+ "\nusername:" + DB_USER_NAME + "\npassword:" + DB_PASSWORD);
			System.out.println("System Msgs: Load config file succeed. The config argument is: ");
			System.out.println("DATABASE: "+DB_DATABASE+"\nDB_USER_NAME: "+DB_USER_NAME+"\nDB_PASSWORD: "+DB_PASSWORD+"\nport: "+port+"\ndb_ip: "+DB_IP);
			return true;
		} catch (Exception e) {
			System.out.println("System Msgs: Load config file error:"+e.getMessage());
			return false;
		}
	}

}
