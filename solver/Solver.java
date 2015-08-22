package solver;

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
		if (this.openQueue.size() > 0) this.addStates(this.openQueue.pollFirst().createResultingGameStates()); return true;
	}
	
	private String sizes()
	{
		return (new StringBuilder("Open: ")).append(this.openQueue.size()).append(", Closed: ").append(this.closedSet.size()).toString();
	}
	
	public static void main(String[] args)
	{
		String[] board = {
			"WWWWWW",
			"WWR WW",
			"WW  WW",
			"W   aW",
			"Wa  aW",
			"Wa   W",
			"WW  WW",
			"WWWWWW"
		};
		Grid<GridSpace> gameBoard = new Grid<>(board.length, board[0].length(), (Integer x, Integer y) -> board[x].charAt(y) == 'W' ? GridSpace.getWall() : GridSpace.getSpace());
		
		int roryRow = 2;
		int roryCol = 2;
		
		for (int x = 0; x < board.length; x++)
		{
			for (int y = 0; y < board[0].length(); y++)
			{
				if (board[x].charAt(y) == 'R')
				{
					roryRow = x;
					roryCol = y;
				}
				if (board[x].charAt(y) == 'a')
				{
					gameBoard.get(x, y).setArt();
				}
			}
		}
		Solver solver = new Solver();
		solver.addState(new GameState(gameBoard, roryRow, roryCol));
		for (int i = 0; i < 10; i++){
			solver.processFirstState();
		System.out.println(solver.sizes());}
		//solver.closedSet.forEach((GameState gs) -> System.out.print(gs.success() ? "true\n" : ""));
		//solver.openQueue.forEach((GameState gs) -> System.out.println(gs.dirsToString(gs.getPossibleDirections())));
	}
}
