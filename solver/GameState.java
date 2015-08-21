package solver;

import java.util.LinkedList;

import gridspace.GridSpace;
import utility.Directions;
import utility.Grid;

public class GameState
{
	public Grid<GridSpace> gameBoard;
	public int roryRow;
	public int roryCol;
	public int[] directions;
	public int direction;
	
	public boolean alive = true;
	
	public GameState(Grid<GridSpace> gb, int row, int col)
	{
		this.gameBoard = gb;
		this.roryRow = row;
		this.roryCol = col;
		this.directions = new int[0];
		this.direction = Directions.NONE;
	}
	
	private GameState(Grid<GridSpace> gb, int row, int col, int[] dirs, int dir)
	{
		this(gb, row, col);
		this.directions = dirs;
		this.direction = dir;
		this.moveRory();
	}
	
	public GameState[] createResultingGameStates()
	{
		int rows = this.gameBoard.rows();
		int cols = this.gameBoard.cols();
		
		int[] dirs = this.getPossibleDirections();
		int len = dirs.length;
		GameState[] states = new GameState[len];
		
		for (int d = 0; d < len; d++)
			states[d] = new GameState(new Grid<GridSpace>(rows, cols, (Integer x, Integer y) -> this.gameBoard.get(x, y).copy()), this.roryRow, this.roryCol, this.directions, dirs[d]);
		return states;
	}
	
	private int[] getPossibleDirections()
	{
		LinkedList<Integer> dirList = new LinkedList<>();
		for (int d = 0, len = Directions.LIST.length; d < len; d++)
		{
			int dir = Directions.LIST[d];
			if (canGo(dir)) dirList.add(Integer.valueOf(dir));
		}
		
		return dirList.stream().mapToInt(Integer::intValue).toArray();
	}
	
	private boolean canGo(int dir)
	{
		if (Directions.isVertical(dir))
		{
			boolean vert = this.checkDir(Directions.verticalComponent(dir));
			if (Directions.isHorizontal(dir)) return vert && this.checkDir(Directions.horizontalComponent(dir)) && this.checkDir(dir);
			return vert;
		}
		else if (Directions.isHorizontal(dir))
		{
			return this.checkDir(Directions.horizontalComponent(dir));
		}
		return false;
	}
	
	private boolean checkDir(int dir)
	{
		int x = this.roryRow + Directions.verticalChange(dir);
		int y = this.roryCol + Directions.horizontalChange(dir);
		return this.gameBoard.inRange(x, y) && !this.gameBoard.get(x, y).isSolid();
	}
	
	private void moveRory()
	{
		if (this.canGo(this.direction))
		{
			int len = this.directions.length;
			int[] dirs = new int[len + 1];
			System.arraycopy(this.directions, 0, dirs, 0, len);
			dirs[len] = this.direction;
			this.directions = dirs;
			
			while (this.canGo(this.direction))
			{
				this.roryRow += Directions.verticalChange(this.direction);
				this.roryCol += Directions.horizontalChange(this.direction);
			}
			
			this.gameBoard.get(this.roryRow, this.roryCol).landedOn(this);
		}
	}
	
	public void changeDirection(int dir)
	{
		this.direction = dir;
	}
	
	public boolean success()
	{
		return this.gameBoard.whileTrue((GridSpace gs) -> !gs.hasArt());
	}
	
	public int hashCode() { return (this.gameBoard.hashCode() << 12) + (this.roryRow << 8) + (this.roryCol << 4) + this.directions[this.directions.length - 1]; }
	public boolean equals(Object o) { return (o instanceof GameState) && (o.hashCode() == this.hashCode()); }
	
	
	private static String dirsToString(int[] dirs)
	{
		return java.util.Arrays.toString(java.util.Arrays.stream(dirs).mapToObj((int i) -> Directions.NAMES[i]).toArray());
	}
	
	private String getBoard()
	{
		StringBuilder sb = new StringBuilder();
		for (int x = 0, rows = this.gameBoard.rows(); x < rows; x++)
		{
			for (int y = 0, cols = this.gameBoard.cols(); y < cols; y++)
			{
				sb.append((x == this.roryRow && y == this.roryCol) ? 'R' : this.gameBoard.get(x, y).toString());
			}
			sb.append('\n');
		}
		return sb.toString();
	}
	
	public static void main(String[] args)
	{
		GameState gs;
		{
			String[] board = {
				"WWWWWW",
				"WWR WW",
				"WW  WW",
				"W    W",
				"W    W",
				"W    W",
				"WW  WW",
				"WWWWWW"
			};
			Grid<GridSpace> gameBoard = new Grid<>(board.length, board[0].length(), (Integer x, Integer y) -> board[x].charAt(y) == 'W' ? GridSpace.getWall() : GridSpace.getSpace());
			gameBoard.get(2, 3).setArt();
			
			int roryRow = 2;
			int roryCol = 2;
			spawn:
			for (int x = 0; x < board.length; x++)
			{
				for (int y = 0; y < board[0].length(); y++)
				{
					if (board[x].charAt(y) == 'R')
					{
						roryRow = x;
						roryCol = y;
						break spawn;
					}
				}
			}
			gs = new GameState(gameBoard, roryRow, roryCol);
		}
		System.out.println(gs.getBoard());
		GameState[] states = gs.createResultingGameStates();
		for (int i = 0; i < states.length; i++) System.out.println(states[i].success());
	}
}
