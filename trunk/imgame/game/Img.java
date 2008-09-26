package game;

import java.applet.Applet;
import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Event;
import java.awt.Frame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import util.Constant;
import game.Game;
import agent.Agent;
import agent.MGAgent;
import agent.MGHuman;

public class Img extends Applet {

	Game game;

	Agent[] agents;

	private int[] initCurrentChoise;

	public void init() {

		setLayout(null);
		try {
			//用户没填m,s,n的时候用默认数据3，2，95
			Constant.memorySize = Integer
					.parseInt((this.getParameter("memory") == null) ? String
							.valueOf(Constant.memorySize) : this
							.getParameter("memory"));
			Constant.strategySize = Integer.parseInt((this
					.getParameter("strategy") == null) ? String
					.valueOf(Constant.strategySize) : this
					.getParameter("strategy"));
			Constant.agentNumber = Integer.parseInt((this
					.getParameter("agents") == null) ? String
					.valueOf(Constant.agentNumber) : this
					.getParameter("agents"));
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
		game = new Game(agents, 0, 200 * (1 << 3));
		game.playGame();
		double[] price = new double[Constant.timeStepNumber + 1];
		initCurrentChoise = game.getCurrentChoise();

		
		price[0] = 0;
		//
		for (int i = 0; i < price.length - 1; ++i) {
			price[i + 1] = price[i]
					+ initCurrentChoise[(initCurrentChoise.length - price.length - 1) + i];
		}

		price = null;
		initCurrentChoise = null;

		agents[agents.length - 1] = new MGHuman(agents);

		for (int i = 0; i < agents.length; ++i) {
			agents[i].setGain(0);
		}

		game = new Game(agents, 0, 1000);

	}

	public void destroy() {
		initCurrentChoise = null;

		agents = null;
		game = null;

	}

	public void start() {
		game.start();
	}

	public void stop() {
		game.stop();
	}

	public void processEvent(AWTEvent e) {
		if (e.getID() == Event.WINDOW_DESTROY) {
			System.exit(0);
		}
	}

	public static void main(String args[]) {
		Frame f = new Frame("ImgTest");
		Img ImgTest = new Img();

		ImgTest.init();
		ImgTest.start();

		f.add("Center", ImgTest);
		f.setSize(1000, 800);
		f.show();
	}

	public String getAppletInfo() {
		return "Title: Interactive Minority Game \nAuthor: <f>";
	}

}
