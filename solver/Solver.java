package solver;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

import gridspace.GridLiaison;
import gridspace.GridSpace;

import utility.Direction;
import utility.Grid;

public class Solver
{
	private Queue<GameState> openQueue;
	private Set<GameState> closedSet;
	
	private int maxMoves;
	
	private int lastMoves;
	private int maxArt;
	private int count;
	
	public Solver(Grid<GridSpace> grid, int row, int col, int max)
	{
		GridLiaison.checkGrid(grid);
		int gridSize = grid.size();
		this.openQueue = new LinkedList<>();
		this.closedSet = new HashSet<>((int)Math.pow(gridSize, 3));
		this.addState(new GameState(grid, row, col));
		this.maxMoves = max;
		
		this.lastMoves = 0;
		this.maxArt = this.openQueue.peek().countArt();
		this.count = 0;
	}
	
	public Solver(GridLiaison liaison, int max)
	{
		this(liaison.copyGrid(), liaison.roryRow(), liaison.roryCol(), max);
	}
	
	
	private boolean addState(GameState state)
	{
		if (!this.closedSet.contains(state))
		{
			this.openQueue.offer(state);
			this.closedSet.add(state);
			return true;
		}
		return false;
	}
	
	public Direction[] solve(int limit)
	{
		for (int i = 0; (limit <= 0 || i < limit) && !this.openQueue.isEmpty(); i++)
		{
			GameState first = this.openQueue.poll();
			int moves = first.getDirections().length;
			if (moves > this.lastMoves)
			{
				if (this.count == 0)
				{
					this.closedSet.removeIf((GameState state) -> state.countArt() >= this.maxArt);
					this.maxArt--;
				}
				this.lastMoves = moves;
				this.count = 0;
			}
			
			for (GameState state : first.createResultingGameStates())
			{
				if (state.stillAlive(this.maxMoves))
				{
					if (state.success()) return state.getDirections();
					
					this.addState(state);
					
					if (state.countArt() >= maxArt) this.count++;
				}
			}
		}
		return this.openQueue.isEmpty() ? new Direction[0] : new Direction[]{ Direction.NONE };
	}
	
	
	public String sizes()
	{
		return (new StringBuilder("Open: ")).append(this.openQueue.size()).append(", Closed: ").append(this.closedSet.size()).toString();
	}
	
	public static String solution(Direction[] dirs)
	{
		return (new StringBuilder(dirsToString(dirs))).append(' ').append(dirs.length).toString();
	}
	
	public static String dirsToString(Direction[] dirs)
	{
		return Arrays.toString(dirs);//Arrays.stream(dirs).map(Object::toString).toArray());
	}
	
	public Queue<GameState> getOpenQueue()
	{
		return this.openQueue;
	}
	
	public GameState first()
	{
		return this.openQueue.peek();
	}
	
	public Set<GameState> getClosedSet()
	{
		return this.closedSet;
	}
}
