package agents;

import java.net.Socket;

import util.Constant;

public class MGHuman extends Agent
{

	private Agent[] agents;

	private int memoryNum;

	private int strategyNum;

	private int agentNum;
	
	private Socket socket;

	// private Agent agent[] = new Agent[number];

	public MGHuman(int m, int s, int n)
	{

		// this.agents = agent;

		this.memoryNum = m;
		this.strategyNum = s;
		this.agentNum = n;
	}
	
	public void setSocket(Socket socket)
	{
		this.socket = socket;
	}
	
	public Socket getSocket()
	{
		return socket;
	}

	public void setMemoryNum(int m)
	{
		this.memoryNum = m;
	}

	public int getMemoryNum()
	{
		return memoryNum;
	}

	public void setStrategyNum(int s)
	{
		this.strategyNum = s;
	}

	public int getStrategyNum()
	{
		return strategyNum;
	}

	public void seAgentNum(int n)
	{
		this.agentNum = n;
	}

	public int getAgentNum()
	{
		return agentNum;
	}

	/**
	 * compare two mghunmen have the same m,n,s,ormaybe could extend to compare they are from the same place..
	 * @param mghuman
	 * @return
	 */
	public boolean isTheSame(MGHuman mgHuman)
	{
		if (!(mgHuman instanceof MGHuman))
		{
			return false;
		} else if ((this.agentNum == mgHuman.getAgentNum())
				&& (this.strategyNum == mgHuman.getStrategyNum())
				&& (this.memoryNum == mgHuman.getMemoryNum()))
		{
			return true;
		} else
		{
			return false;
		}
	}

	/**
	 * 
	 */
	public boolean agentAct(int mu)
	{

		while (!Constant.isInterfaceToGame())
		{
			// DO NOTHING
		}

		Constant.setInterfaceToGame(false);

		if (Constant.interfaceToGameAction == Constant.HUMAN_PLAYER_EXIT)
		{
			action = 0;
			return false;
		} else
		{
			action = Constant.interfaceToGameAction;
			return true;
		}
	}

	/**
	 * 
	 * @param agent
	 * @return
	 */
	public boolean feedback(Agent[] agent)
	{

		double agentsMaxGain = agents[0].getGain();
		double agentsMinGain = agents[0].getGain();
		double agentsAvgGain = agents[0].getGain();

		for (int i = 1; i < (agent.length - 1); i++)
		{
			if (agents[i].getGain() < agentsMinGain)
			{

				agentsMinGain = agents[i].getGain();

			}

			if (agents[i].getGain() > agentsMaxGain)
			{

				agentsMaxGain = agents[i].getGain();
			}

			agentsAvgGain = agentsAvgGain + agents[i].getGain();
		}

		agentsAvgGain = agentsAvgGain / (agent.length - 1);

		Constant.agentGainMax = agentsMaxGain;
		Constant.agentGainMin = agentsMinGain;
		Constant.agentGainAv = agentsAvgGain;

		Constant.setGameToInterface(true);

		return true;
	}

}