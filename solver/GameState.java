package solver;

import java.util.Arrays;
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
	public int artCount;
	public boolean hasLasers;
	
	public boolean alive = true;
	
	public GameState(Grid<GridSpace> gb, int row, int col)
	{
		this.setDefaults(gb, row, col);
		
		this.directions = new int[0];
		this.direction = Directions.NONE;
		
		this.artCount = 0;
		for (GridSpace gs : this.gameBoard)
		{
			if (gs.hasArt()) this.artCount++;
			if (gs.isLaserSource()) this.hasLasers = true;
		}
		
		this.updateLasers();
		
		this.checkHazards();
	}
	
	private GameState(Grid<GridSpace> gb, int row, int col, int[] dirs, int dir, int art, boolean lasers)
	{
		this.setDefaults(gb, row, col);
		
		this.directions = dirs;
		this.direction = dir;
		this.artCount = art;
		this.hasLasers = lasers;
		
		this.moveRory();
	}
	
	private void setDefaults(Grid<GridSpace> gb, int row, int col)
	{
		this.gameBoard = gb;
		this.roryRow = row;
		this.roryCol = col;
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
			if (canGo(this.roryRow, this.roryCol, dir)) dirList.add(Integer.valueOf(dir));
		}
		
		return dirList.stream().mapToInt(Integer::intValue).toArray();
	}
	
	private boolean canGo(int row, int col, int dir)
	{
		if (Directions.isVertical(dir))
		{
			boolean vert = this.checkDir(row, col, Directions.verticalComponent(dir));
			if (Directions.isHorizontal(dir)) return vert && this.checkDir(row, col, Directions.horizontalComponent(dir)) && this.checkDir(row, col, dir);
			return vert;
		}
		else if (Directions.isHorizontal(dir))
		{
			return this.checkDir(row, col, Directions.horizontalComponent(dir));
		}
		return false;
	}
	
	public boolean checkDir(int row, int col, int dir)
	{
		int x = row + Directions.verticalChange(dir);
		int y = col + Directions.horizontalChange(dir);
		return this.gameBoard.inRange(x, y) && !this.gameBoard.get(x, y).isSolid();
	}
	
	private void moveRory()
	{
		if (this.canGo(this.roryRow, this.roryCol, this.direction))
		{
			int len = this.directions.length;
			int[] dirs = new int[len + 1];
			System.arraycopy(this.directions, 0, dirs, 0, len);
			dirs[len] = this.direction;
			this.directions = dirs;
			
			while (this.canGo(this.roryRow, this.roryCol, this.direction))
			{
				this.roryRow += Directions.verticalChange(this.direction);
				this.roryCol += Directions.horizontalChange(this.direction);
				
				if (this.checkHazards()) return;
			}
			
			for (int x = 0, rows = this.gameBoard.rows(); x < rows; x++)
			{
				for (int y = 0, cols = this.gameBoard.cols(); y < cols; y++)
				{
					this.gameBoard.get(x, y).endOfMove(this);
				}
			}
			
			this.updateLasers();
			
			if (this.checkHazards()) return;
			
			this.gameBoard.get(this.roryRow, this.roryCol).landedOn(this);
		}
	}
	
	private boolean checkHazards()
	{
		return this.alive = this.gameBoard.get(this.roryRow, this.roryCol).checkHazard(this);
	}
	
	public void changeDirection(int dir)
	{
		this.direction = dir;
	}
	
	public void decrementArt()
	{
		this.artCount--;
	}
	
	public void updateLasers()
	{
		if (this.hasLasers)
		{
			for (GridSpace gs : this.gameBoard)
			{
				gs.setLaser(false);
			}
			for (int x = 0, rows = this.gameBoard.rows(); x < rows; x++)
			{
				for (int y = 0, cols = this.gameBoard.cols(); y < cols; y++)
				{
					GridSpace square = this.gameBoard.get(x, y);
					if (square.isLaserSource() && square.laserSourceOn())
					{
						int dir = square.laserSourceDirection();
						if (this.checkDir(x, y, Directions.NONE))
						{
							square.setLaser(true);
							int row = x;
							int col = y;
							
							while (this.canGo(row, col, dir))
							{
								row += Directions.verticalChange(dir);
								col += Directions.horizontalChange(dir);
								this.gameBoard.get(row, col).setLaser(true);
							}
						}
					}
				}
			}
		}
	}
	
	boolean success()
	{
		return this.gameBoard.whileTrue((GridSpace gs) -> !gs.hasArt());
	}
	
	boolean stillAlive(int maxMoves)
	{
		return this.alive && this.directions.length <= (maxMoves - this.artCount);
	}
	
	Grid<GridSpace> gameBoard()
	{
		return this.gameBoard;
	}
	
	int countArt()
	{
		return this.artCount;
	}
	
	int[] getDirections()
	{
		return this.directions;
	}
	
	int getMoves()
	{
		return this.directions.length;
	}
	
	public int hashCode() { return (this.gameBoard.hashCode() << 12) + (this.roryRow << 6) + this.roryCol; }
	
	public boolean equals(Object o)
	{
		if (!(o instanceof GameState)) return false;
		GameState gs = (GameState)o;
		return this.gameBoard.equals(gs.gameBoard) && (this.roryRow == gs.roryRow) && (this.roryCol == gs.roryCol);
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
