package gridspace;

import utility.*;

public final class GridLiaison
{
	private Grid<GridSpace> grid;
	private int roryRow = -1;
	private int roryCol = -1;
	
	private static final Wall THE_WALL = new Wall();
	
	public GridLiaison(int rows, int cols)
	{
		this.grid = new Grid<>(rows, cols, THE_WALL);
	}
	
	public GridLiaison(int rows, int cols, int roryRow, int roryCol)
	{
		this(rows, cols);
		this.setRory(roryRow, roryCol);
	}
	
	public void setRory(int row, int col)
	{
		this.roryRow = row;
		this.roryCol = col;
	}
	
	public int roryRow() { return this.roryRow; }
	public int roryCol() { return this.roryCol; }
	
	public int size() { return this.grid.size(); }
	public int rows() { return this.grid.rows(); }
	public int cols() { return this.grid.cols(); }
	
	public void ensureCapacity(int rows, int cols) { this.grid.ensureCapacity(rows, cols); }
	public void trim(int rows, int cols) { this.grid.trim(rows, cols); }
	
	public int getCell(int row, int col) { return this.grid.get(row, col).ID(); }
	public void setCell(int row, int col, int ID)
	{
		GridSpace gs = new Wall();
		
		if (ID == GridSpace.SPACE) gs = new Space();
		if (ID == GridSpace.SPIKE) gs = new Spike();
		if (ID == GridSpace.BOOSTER) gs = new Booster();
		
		this.grid.set(row, col, gs);
	}
	
	
	public void setArt(int row, int col, boolean art) { if (this.isOpen(row, col)) this.grid.get(row, col).art = art; }
	public void setLaserSourceDirection(int row, int col, int dir) { if (this.isOpen(row, col)) this.grid.get(row, col).laserSourceDirection = dir; }
	public void setLaserSourceBlue(int row, int col, boolean blue) { if (this.isOpen(row, col)) this.grid.get(row, col).laserSourceBlue = blue; }
	public void setLaserSourceOn(int row, int col, boolean on) { if (this.isOpen(row, col)) this.grid.get(row, col).laserSourceOn = on; }
	
	public void setUp(int row, int col, boolean up)
	{
		if (this.isOpen(row, col))
		{
			int ID = this.getCell(row, col);
			
			if (ID == GridSpace.SPIKE) ((Spike)this.grid.get(row, col)).up = up;
		}
	}
	
	public void setDirection(int row, int col, int dir)
	{
		if (this.isOpen(row, col) && Directions.isDir(dir))
		{
			int ID = this.getCell(row, col);
			
			if (ID == GridSpace.BOOSTER) { if (Directions.isCardinal(dir) || dir == Directions.NONE) ((Booster)this.grid.get(row, col)).direction = dir; }
		}
	}
	
	private boolean isOpen(int row, int col) { return this.grid.inRange(row, col) && this.grid.get(row, col).ID() > GridSpace.WALL; }
	
	
	public int countArt() { return countArt(this.grid); }
	public static int countArt(Grid<GridSpace> grid)
	{
		int count = 0;
		for (GridSpace gs : grid)
			if (gs.art)
				count++;
		return count;
	}
	
	public boolean hasLasers() { return hasLasers(this.grid); }
	public static boolean hasLasers(Grid<GridSpace> grid)
	{
		for (GridSpace gs : grid)
			if (gs.isLaserSource())
				return true;
		return false;
	}
	
	public Grid<GridSpace> copyGrid() { return copyGrid(this.grid); }
	public static Grid<GridSpace> copyGrid(Grid<GridSpace> grid) { return new Grid<GridSpace>(grid.rows(), grid.cols(), (Integer x, Integer y) -> grid.get(x, y).copy()); }
	
	public void updateLasers() { updateLasers(this.grid); }
	public static void updateLasers(Grid<GridSpace> gameBoard)
	{
		for (GridSpace gs : gameBoard)
		{
			gs.laser = false;
		}
		for (int x = 0, rows = gameBoard.rows(); x < rows; x++)
		{
			for (int y = 0, cols = gameBoard.cols(); y < cols; y++)
			{
				GridSpace square = gameBoard.get(x, y);
				if (square.isLaserSource() && square.laserSourceOn)
				{
					int dir = square.laserSourceDirection;
					if (GridTraveler.checkDir(gameBoard, x, y, Directions.NONE))
					{
						square.laser = true;
						int row = x;
						int col = y;
						
						while (GridTraveler.canGo(gameBoard, row, col, dir))
						{
							row += Directions.verticalChange(dir);
							col += Directions.horizontalChange(dir);
							gameBoard.get(row, col).laser = true;
						}
					}
				}
			}
		}
	}
}
