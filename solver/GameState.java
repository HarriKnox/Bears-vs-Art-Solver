package solver;

import java.util.Arrays;
import java.util.LinkedList;

import gridspace.*;
import utility.*;

public class GameState
{
	private Grid<GridSpace> gameBoard;
	private int roryRow;
	private int roryCol;
	
	private Direction[] directions;
	private Direction direction;
	private int artCount;
	
	private boolean alive = true;
	
	private static boolean hasLasers;
	
	public GameState(Grid<GridSpace> gb, int row, int col)
	{
		this.setDefaults(gb, row, col);
		
		this.directions = new Direction[0];
		this.direction = Direction.NONE;
		
		this.artCount = GridLiaison.countArt(this.gameBoard);
		this.hasLasers = GridLiaison.hasLasers(this.gameBoard);
		
		this.updateLasers();
		this.checkHazards();
	}
	
	private GameState(Grid<GridSpace> gb, int row, int col, Direction[] dirs, Direction dir, int art)
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
				this.roryRow += Direction.verticalChange(this.direction);
				this.roryCol += Direction.horizontalChange(this.direction);
				
				this.checkHazards();
				this.passThrough();
				this.updateLasers();
				this.checkHazards();
				
				if (!this.alive) return;
			}
			
			this.endOfMove();
			
			this.updateLasers();
			this.checkHazards();
			
			this.landedOn();
		}
	}
	
	private void updateLasers() { GridLiaison.updateLasers(this.gameBoard); }
	private void checkHazards() { this.gameBoard.get(this.roryRow, this.roryCol).checkHazard(this); }
	private void passThrough() { this.gameBoard.get(this.roryRow, this.roryCol).passThrough(this); }
	private void landedOn() { this.gameBoard.get(this.roryRow, this.roryCol).landedOn(this); }
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
	
	public void decrementArt() { this.artCount--; }
	public void changeDirection(Direction dir) { this.direction = dir; }
	public void teleportRory(int x, int y) { this.roryRow = x; this.roryCol = y; }
	public void kill() { this.alive = false; }
	
	public Grid<GridSpace> getGameBoard() { return this.gameBoard; }
	public int getRoryRow() { return this.roryRow; }
	public int getRoryCol() { return this.roryCol; }
	
	boolean success() { return this.artCount == 0; }
	boolean stillAlive(int maxMoves) { return this.alive && this.directions.length <= (maxMoves - this.artCount); }
	int countArt() { return this.artCount; }
	Direction[] getDirections() { return this.directions; }
	
	public int hashCode() { return (this.gameBoard.hashCode() << 12) + (this.roryRow << 6) + this.roryCol; }
	public boolean equals(Object o)
	{
		if (!(o instanceof GameState)) return false;
		GameState gs = (GameState)o;
		return this.gameBoard.equals(gs.gameBoard) && (this.roryRow == gs.roryRow) && (this.roryCol == gs.roryCol);
	}
	
	
	
	
	
	public static String dirsToString(Direction[] dirs)
	{
		return Arrays.toString(Arrays.stream(dirs).mapToObj((Direction i) -> Direction.NAMES[i.hash]).toArray());
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
