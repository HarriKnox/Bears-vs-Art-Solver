import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class Grid<T>
{
	private Object[][] grid;
	
	public Grid(int rows, int cols)
	{
		this.grid = new Object[rows][cols];
	}
	
	public Grid(int rows, int cols, T defaultValue)
	{
		this(rows, cols);
		for (int x = 0; x < rows; x++) for (int y = 0; y < cols; y++) this.grid[x][y] = defaultValue;
	}
	
	private int rows() { return this.grid.length; }
	private int cols() { return (this.rows() > 0) ? this.grid[0].length : 0; }
	private String pair(int x, int y) { return "(" + x + "," + y + ")"; }
	private void checkPos(int row, int col) { if (row < 0 || col < 0 || row >= this.rows() || col >= this.cols()) throw new IndexOutOfBoundsException("Position: " + pair(row, col) + ", Dimensions: " + pair(this.rows(), this.cols())); }
	
	public T get(int row, int col)
	{
		checkPos(row, col);
		return (T)this.grid[row][col];
	}
	
	public void set(int row, int col, T value)
	{
		checkPos(row, col);
		this.grid[row][col] = value;
	}
	
	public int size()
	{
		int rows = this.rows();
		return (rows == 0) ? 0 : rows * this.grid[0].length;
	}
	
	public static void main(String[] args)
	{
		System.out.println(new Grid<Integer>(2, 5, Integer.valueOf(3)).get(0, 5));
	}
}