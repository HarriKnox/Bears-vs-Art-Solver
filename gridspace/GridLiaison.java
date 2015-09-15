package gridspace;

import utility.*;

public final class GridLiaison
{
	private Grid<GridSpace> grid;
	
	public GridLiaison(int rows, int cols)
	{
		this.grid = new Grid<>(rows, cols, GridSpace.getWall());
	}
	
	public int size() { return this.grid.size(); }
	public int rows() { return this.grid.rows(); }
	public int cols() { return this.grid.cols(); }
	
	public void ensureCapacity(int rows, int cols) { this.grid.ensureCapacity(rows, cols); }
	public void trim(int rows, int cols) { this.grid.trim(rows, cols); }
	
	public void setCell(int row, int col, int ID)
	{
		GridSpace gs = null;
		
		switch (ID)
		{
			case Grid.Space: gs = new Space(); break;
			case Grid.Spike: gs = new Spike(); break;
			case Grid.Wall: default: gs = new Wall();
		}
		
		this.grid.set(row, col, gs);
	}
	
	public GridSpace getCell(int row, int col)
	{
		return this.grid.get(row, col).copy();
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
