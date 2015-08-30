package solver;

import java.util.Arrays;
import java.util.LinkedList;

import gridspace.*;
import utility.*;

public class GameState
{
	public Grid<GridSpace> gameBoard;
	public int roryRow;
	public int roryCol;
	
	public int[] directions;
	public int direction;
	public int artCount;
	
	public boolean alive = true;
	
	public static boolean hasLasers;
	
	public GameState(Grid<GridSpace> gb, int row, int col)
	{
		this.setDefaults(gb, row, col);
		
		this.directions = new int[0];
		this.direction = Directions.NONE;
		
		this.artCount = GridLiaison.countArt(this.gameBoard);
		this.hasLasers = GridLiaison.hasLasers(this.gameBoard);
		
		this.updateLasers();
		this.checkHazards();
	}
	
	private GameState(Grid<GridSpace> gb, int row, int col, int[] dirs, int dir, int art)
	{
		this.setDefaults(gb, row, col);
		
		this.directions = dirs;
		this.direction = dir;
		this.artCount = art;
		
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
		
		int[] dirs = GridTraveler.getPossibleDirections(this.gameBoard, this.roryRow, this.roryCol);
		int len = dirs.length;
		GameState[] states = new GameState[len];
		
		for (int d = 0; d < len; d++)
			states[d] = new GameState(GridLiaison.copyGrid(this.gameBoard), this.roryRow, this.roryCol, this.directions, dirs[d], this.artCount);
		return states;
	}
	
	private void moveRory()
	{
		if (GridTraveler.canGo(this.gameBoard, this.roryRow, this.roryCol, this.direction))
		{
			int len = this.directions.length;
			int[] dirs = new int[len + 1];
			System.arraycopy(this.directions, 0, dirs, 0, len);
			dirs[len] = this.direction;
			this.directions = dirs;
			
			while (GridTraveler.canGo(this.gameBoard, this.roryRow, this.roryCol, this.direction))
			{
				this.roryRow += Directions.verticalChange(this.direction);
				this.roryCol += Directions.horizontalChange(this.direction);
				
				this.checkHazards();
				this.passThrough();
				this.checkHazards();
				
				if (!this.alive) return;
			}
			
			this.endOfMove();
			
			this.updateLasers();
			this.checkHazards();
			
			this.landedOn();
		}
	}
	
	public void updateLasers()
	{
		/*if (hasLasers)
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
		}*/
	}
	
	private void checkHazards()
	{
		this.gameBoard.get(this.roryRow, this.roryCol).checkHazard(this);
	}
	
	
	private void passThrough()
	{
		this.gameBoard.get(this.roryRow, this.roryCol).passThrough(this);
	}
	
	private void endOfMove()
	{
		for (int x = 0, rows = this.gameBoard.rows(); x < rows; x++)
		{
			for (int y = 0, cols = this.gameBoard.cols(); y < cols; y++)
			{
				this.gameBoard.get(x, y).endOfMove(this);
			}
		}
	}
	
	private void landedOn()
	{
		this.gameBoard.get(this.roryRow, this.roryCol).landedOn(this);
	}
	
	public void changeDirection(int dir)
	{
		this.direction = dir;
	}
	
	public void decrementArt()
	{
		this.artCount--;
	}
	
	public void kill()
	{
		this.alive = false;
	}
	
	boolean success()
	{
		return this.artCount == 0;
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
