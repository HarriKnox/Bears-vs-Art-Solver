package gridspace;

import utility.*;

public final class GridLiaison
{
	private Grid<GridSpace> grid;
	private int roryRow = -1;
	private int roryCol = -1;
	
	public GridLiaison(int rows, int cols)
	{
		this.grid = new Grid<>(rows, cols, GridSpace.getWall());
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
	
	public int size() { return this.grid.size(); }
	public int rows() { return this.grid.rows(); }
	public int cols() { return this.grid.cols(); }
	
	public void ensureCapacity(int rows, int cols) { this.grid.ensureCapacity(rows, cols); }
	public void trim(int rows, int cols) { this.grid.trim(rows, cols); }
	
	public int getCell(int row, int col) { return this.grid.get(row, col).ID()); }
	public void setCell(int row, int col, int ID)
	{
		GridSpace gs = new Wall();
		
		switch (ID)
		{
			case GridSpace.SPACE: gs = new Space(); break;
			case GridSpace.SPIKE: gs = new Spike(); break;
			case GridSpace.BOOSTER: gs = new Booster(); break;
		}
		
		this.grid.set(row, col, gs);
	}
	
	public void setArt(int row, int col, boolean art)
	{
		GridSpace gs = this.grid.get(row, col);
		if (gs.ID() > GridSpace.WALL) gs.art = art;
	}
	
	
	public static int countArt(Grid<GridSpace> grid)
	{
		int count = 0;
		for (GridSpace gs : grid)
			if (gs.art)
				count++;
		return count;
	}
	
	public static boolean hasLasers(Grid<GridSpace> grid)
	{
		for (GridSpace gs : grid)
			if (gs.isLaserSource())
				return true;
		return false;
	}
	
	public static Grid<GridSpace> copyGrid(Grid<GridSpace> grid)
	{
		return new Grid<GridSpace>(grid.rows(), grid.cols(), (Integer x, Integer y) -> grid.get(x, y).copy());
	}
	
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
