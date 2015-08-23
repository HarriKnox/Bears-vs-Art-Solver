package solver;

import java.util.Arrays;
import java.util.LinkedList;

import gridspace.GridSpace;
import utility.Directions;
import utility.Grid;

public class GameState
{
	private Grid<GridSpace> gameBoard;
	private int roryRow;
	private int roryCol;
	private int[] directions;
	private int direction;
	
	private boolean alive = true;
	
	GameState(Grid<GridSpace> gb, int row, int col)
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
	
	public int[] getPossibleDirections()
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
	
	boolean success()
	{
		return this.gameBoard.whileTrue((GridSpace gs) -> !gs.hasArt());
	}
	
	boolean stillAlive(int maxMoves)
	{
		return this.alive && this.directions.length <= maxMoves;
	}
	
	public int hashCode() { return (this.gameBoard.hashCode() << 12) + (this.roryRow << 6) + this.roryCol; }
	
	public boolean equals(Object o)
	{
		if (!(o instanceof GameState)) return false;
		GameState gs = (GameState)o;
		return this.gameBoard.equals(gs.gameBoard) && (this.roryRow == gs.roryRow) && (this.roryCol == gs.roryCol);
	}
	
	int[] getDirections()
	{
		return this.directions;
	}
	
	
	public static String dirsToString(int[] dirs)
	{
		return Arrays.toString(Arrays.stream(dirs).mapToObj((int i) -> Directions.NAMES[i]).toArray());
	}
	
	public String getBoard()
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
}
