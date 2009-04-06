package game;

import java.util.ArrayList;

import agents.MGHuman;

public class GameList
{
	private static MGHuman[] humanlist;

	private static ArrayList<ArrayList<MGHuman>> humanAgentList = new ArrayList<ArrayList<MGHuman>>();

	private static GameList gameList = new GameList();

	private GameList()
	{

	}

	public static GameList getInstance()
	{
		return gameList;
	}

	/**
	 * add human agent to the human agent list
	 * 
	 * @param mgHuman
	 */
	public void addHumenAgent(MGHuman mgHuman)
	{
		if (mgHuman instanceof MGHuman)
		{
			int[] tempCheckStatue = new int[2];
			tempCheckStatue = isExist(mgHuman);
			// add human agent to the human list
			if (tempCheckStatue[0] == 0)
			{
				humanAgentList.get(tempCheckStatue[1]).add(mgHuman);
			} else if (tempCheckStatue[0] == 1)
			{
				ArrayList<MGHuman> tempMGHumanList = new ArrayList<MGHuman>();
				tempMGHumanList.add(mgHuman);
				humanAgentList.add(tempMGHumanList);
			}
		} else
		{
			System.out.println("some thing wrong with mghuman:"+mgHuman.hashCode());
		}
	}

	public int size()
	{
		return gameList.humanAgentList.size();
	}

	/**
	 * check the param weather exist
	 * 
	 * @param mgHuman
	 * @return array[0] could be 0 or 1,0 means is exist and 1 means not,if
	 *         exist then array[1] is the index of that list
	 */
	private int[] isExist(MGHuman mgHuman)
	{
		ArrayList<MGHuman> tempHumanList = new ArrayList<MGHuman>();
		int[] existStatue = new int[2];
		existStatue[0] = 0;
		existStatue[1] = 0;
		if (mgHuman instanceof MGHuman)
		{
			for (int i = 0, size = humanAgentList.size(); i < size; i++)
			{
				tempHumanList = humanAgentList.get(i);
				// check if exist
				if (tempHumanList.get(0).isTheSame(mgHuman))
				{
					existStatue[0] = 1;
					existStatue[1] = i;
				}
			}
			// for (Iterator iterator = humanAgentList.iterator();
			// iterator.hasNext();) {
			// tempHumanList = (ArrayList<MGHuman>)iterator.next();
			// //check if exist
			// if(tempHumanList.get(0).isTheSame(mgHuman))
			// {
			// return true;
			// }
			// }
		}
		return existStatue;
	}
}
