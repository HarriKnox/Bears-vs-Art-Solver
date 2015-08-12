import java.util.ArrayList;
import static java.lang.Integer.valueOf;

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
		for (int x = 0; x < rows; x++)
			for (int y = 0; y < cols; y++)
				this.grid[x][y] = defaultValue;
	}
	
	private int rows() { return this.grid.length; }
	private int cols() { return (this.rows() > 0) ? this.grid[0].length : 0; }
	private static String pair(int x, int y) { return "(" + x + "," + y + ")"; }
	
	private void checkPos(int row, int col)
	{
		if (row < 0 || col < 0 || row >= this.rows() || col >= this.cols())
			throw new IndexOutOfBoundsException("Position: " + pair(row, col) + ", Dimensions: " + pair(this.rows(), this.cols()));
	}
	
	public void clear()
	{
		for (int x = 0, rows = this.rows(); x < rows; x++)
			for (int y = 0, cols = this.cols(); y < cols; y++)
				this.grid[x][y] = null;
		this.grid = new Object[0][0];
	}
	
	public boolean contains(T o)
	{
		for (int x = 0, rows = this.rows(); x < rows; x++)
			for (int y = 0, cols = this.cols(); y < cols; y++)
				if ((o == null && this.grid[x][y] == null) || (o != null && o.equals(this.grid[x][y])))
					return true;
		return false;
	}
	
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
		return (rows == 0) ? 0 : rows * this.cols();
	}
	
	public static void main(String[] args)
	{
		Grid<Integer> g = new Grid<>(2, 2);
		g.set(0, 0, valueOf(4));
		g.set(1, 1, valueOf(15));
		g.set(1, 0, valueOf(2));
		System.out.println(g.contains(null));
		System.out.println(g.contains(valueOf(1)));
		System.out.println(g.contains(valueOf(15)));
	}
}
