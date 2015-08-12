public class Grid<T>
{
	private T[][] grid;
	
	public Grid(int rows, int cols)
	{
		this.grid = new T[rows][cols];
	}
}