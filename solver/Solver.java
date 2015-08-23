package solver;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.HashSet;

import gridspace.*;
import utility.*;

public class Solver
{
	private LinkedList<GameState> openQueue;
	private HashSet<GameState> closedSet;
	
	private int maxMoves;
	
	public Solver(Grid<GridSpace> grid, int row, int col, int max)
	{
		int gridSize = grid.size();
		this.openQueue = new LinkedList<>();
		this.closedSet = new HashSet<>((int)Math.pow(gridSize, 3));
		this.addState(new GameState(grid, row, col));
		this.maxMoves = max;
	}
	
	private boolean addState(GameState state)
	{
		if (!this.closedSet.contains(state))
		{
			this.openQueue.addLast(state);
			this.closedSet.add(state);
			return true;
		}
		return false;
	}
	
	public int[] solve()
	{
		while (!this.openQueue.isEmpty())
		{
			GameState[] states = this.openQueue.removeFirst().createResultingGameStates();
			for (int s = 0, len = states.length; s < len; s++)
			{
				GameState state = states[s];
				if (state.stillAlive(this.maxMoves))
				{
					if (state.success()) return state.getDirections();
					
					this.addState(state);
				}
			}
		}
		return new int[0];
	}
	
	public String sizes()
	{
		return (new StringBuilder("Open: ")).append(this.openQueue.size()).append(", Closed: ").append(this.closedSet.size()).toString();
	}
	
	public static String solution(int[] dirs)
	{
		return (new StringBuilder(GameState.dirsToString(dirs))).append(' ').append(dirs.length).toString();
	}
}
