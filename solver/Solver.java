package solver;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.HashSet;

import gridspace.*;
import utility.*;

public class Solver
{
	private LinkedList<GameState> openQueue = new LinkedList<>();
	private HashSet<GameState> closedSet = new HashSet<>(150);
	
	private boolean addState(GameState state)
	{
		if (!this.closedSet.contains(state))
		{
			this.openQueue.add(state);
			this.closedSet.add(state);
			return true;
		}
		return false;
	}
	
	private boolean addStates(GameState[] states)
	{
		boolean added = false;
		for (int s = 0, len = states.length; s < len; s++)
			added = added | this.addState(states[s]);
		return added;
	}
	
	private boolean processFirstState()
	{
		if (this.openQueue.size() > 0)
		{
			GameState state = this.openQueue.pollFirst();
			return this.addStates(state.createResultingGameStates());
		}
		return false;
	}
	
	private String sizes()
	{
		return (new StringBuilder("Open: ")).append(this.openQueue.size()).append(", Closed: ").append(this.closedSet.size()).toString();
	}
	
	private static String solution(int[] dirs)
	{
		return (new StringBuilder(GameState.dirsToString(dirs))).append(' ').append(dirs.length).toString();
	}
}
