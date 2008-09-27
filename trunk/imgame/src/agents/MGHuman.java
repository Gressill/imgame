package agents;

import util.Constant;

public class MGHuman extends Agent {

	private Agent[] agents;

	//private Agent agent[] = new Agent[number];

	public MGHuman(Agent[] agent) {

		this.agents = agent;

	}
	
	/**
	 * 
	 */
	public boolean agentAct(int mu) {

		while (!Constant.isInterfaceToGame()) {
			// DO NOTHING
		}

		Constant.setInterfaceToGame(false);

		if (Constant.interfaceToGameAction == Constant.HUMAN_PLAYER_EXIT) {
			action = 0;
			return false;
		} else {
			action = Constant.interfaceToGameAction;
			return true;
		}
	}

	/**
	 * 
	 * @param agent
	 * @return
	 */
	public boolean feedback(Agent[] agent) {

		double agentsMaxGain = agents[0].getGain();
		double agentsMinGain = agents[0].getGain();
		double agentsAvgGain = agents[0].getGain();

		for (int i = 1; i < (agent.length - 1); i++) {
			if (agents[i].getGain() < agentsMinGain) {

				agentsMinGain = agents[i].getGain();

			}

			if (agents[i].getGain() > agentsMaxGain) {

				agentsMaxGain = agents[i].getGain();
			}
			
			agentsAvgGain = agentsAvgGain + agents[i].getGain();
		}
		
		agentsAvgGain = agentsAvgGain/(agent.length-1);
		
		Constant.agentGainMax = agentsMaxGain;
		Constant.agentGainMin = agentsMinGain;
		Constant.agentGainAv = agentsAvgGain;

		Constant.setGameToInterface(true);

		return true;
	}

}