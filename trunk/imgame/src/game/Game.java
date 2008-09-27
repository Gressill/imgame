package game;

import agents.Agent;
import util.Constant;
import agents.Strategy;
import server.SocketServer;

public class Game implements Strategy,Runnable {

	private int price;
	
	private int turns; //turns

	//?
	private int ttrans = 0;

	//computer agents number
	private int tmax = 0;

	//2^16 means what?
	private int P = 1 << 16;
	
	private int[] historyChoise;

	private int[] currentChoise;

	private Thread gameThread;

	private Agent[] agent;

	//tTrans make nosence
	//
	public Game(Agent[] agents, int tTrans, int tMax) {
		this.agent = agents;
		ttrans = tTrans;
		tmax = tMax;
		currentChoise = new int[tmax];
		historyChoise = new int[tmax];
		historyChoise[0] = (int) (P * Math.random());
	}
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

	/**
	 * strategiesArray = new int[strategiesNum][historySize];<br>
     * then this we may base on virtualScores to determin param "strategiesNum",<br>
     * and we use historyChoise to determing param "historySize".<br>
     * for example:<br>
     * in t turns,strategy one's virtualScores is highter,so MGagent action with strategiesArray[1][historyChoise[t]]
     *  
	 * @param historyChoise: History choice array,we add that <br>
	 *  for example:<br>
     *     1     0        1 (historial fluctuation)<br>
     *   -----><br>
     *  1*2^2 + 0*2^1 + 1*2^0 (strategy's choose index)<br>
     *  
	 * @param currentChoise: this turns agents choice.
	 */
	private void updateHistory(int historyChoise[],int currentChoise[]) {
		
		int historySize = 1;
		historyChoise[turns] = ((2 * historyChoise[turns - 1]) + ((currentChoise[turns - 1] > 0) ? 1 : 0)) % P;

	}

	private void releaseHistory() {

	}

	public void gainAction() {

		lockHistory();
		updateHistory(historyChoise,currentChoise);
		releaseHistory();
	}
	
	public void run() {
		Thread me = Thread.currentThread();
		// game continues to play
		while (me == gameThread && Constant.playingFlagOfHumanPlayer) {
			//play();
		}

		me = null;

	}

	public void start() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	public void stop() {
		gameThread = null;
	}
	
	public void playGame() {
		
		//create sockets
		SocketServer mgSocket=new SocketServer();
		mgSocket.startServer(Constant.port);
		
		turns = 0;
		boolean keepPlaying = true;//when keeplay == false ,we end the game.
		
		while (keepPlaying) {
			currentChoise[turns] = 0;
			for (int i = 0; i < agent.length; ++i) {
//				 System.out.print("i:"+i);
//				 System.out.println(agent[i]);
				if (agent[i].agentAct(historyChoise[turns]) != true) { // Each agent acts. They
					// have the choice
					keepPlaying = false; // to return false and end the game.
					break;
				}
				// TODO As all the MGAgents' action are 0. so A[t] would be -1
				// or 1
				currentChoise[turns] += agent[i].getAction();
			}

			//t=1600 
			//System.out.println("[t]:"+t);
			 //System.out.println("A[t]:"+A[t]);

			if (!keepPlaying) // If any agent wanted out, we get out.
				break;

			//System.out.println("********************"+agent.length);
			for (int i = 0; i < agent.length; ++i) {
				agent[i].setGain(agent[i].getGain() - agent[i].getAction()
						* currentChoise[turns]);
				//System.out.println("getgain"+agent[i].getGain());
				if (agent[i].feedback(currentChoise[turns], historyChoise[turns]) != true) { // Each agent
					// gets
					// feedback.
					keepPlaying = false; // Again, they can return false and
					// end things!
					break;
				}
			}

			if ((++turns) < tmax) {
				updateHistory(historyChoise, currentChoise);
				//historyChoise[turns] = ((2 * historyChoise[turns - 1]) + ((currentChoise[turns - 1] > 0) ? 1 : 0)) % P; 
				// We update the history.
				if (turns == ttrans) {
					for (int i = 0; i < agent.length; ++i)
						agent[i].setGain(0); // If we've finished the
					// transient period, we set
					// agents' gain to 0.
				}
			} else
				keepPlaying = false;
		}
		
		
		
	}
	
	public int[] getCurrentChoise() {
		return currentChoise;
	}

}