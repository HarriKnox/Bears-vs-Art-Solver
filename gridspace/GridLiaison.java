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
	
	public static Grid<GridSpace> copyGrid(Grid<GridSpace> grid)
	{
		return new Grid<GridSpace>(grid.rows(), grid.cols(), (Integer x, Integer y) -> grid.get(x, y).copy());
	}
}
