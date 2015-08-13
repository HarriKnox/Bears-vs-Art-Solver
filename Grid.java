import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.NoSuchElementException;
import static java.lang.Integer.valueOf;

@SuppressWarnings("unchecked")
public class Grid<T> implements Iterable<T>
{
	private Object[][] grid;
	private int rows;
	private int cols;
	private T defaultValue;
	
	public Grid(int rows, int cols)
	{
		this.grid = new Object[rows][cols];
		this.rows = rows;
		this.cols = cols;
		this.defaultValue = null;
	}
	
	public Grid(int rows, int cols, T defaultValue)
	{
		this(rows, cols);
		for (int x = 0; x < rows; x++)
			Arrays.fill(this.grid[x], defaultValue);
		this.defaultValue = defaultValue;
	}
	
	public Grid(int rows, int cols, BiFunction<Integer, Integer, T> filler)
	{
		this(rows, cols);
		for (int x = 0; x < rows; x++)
			for (int y = 0; y < cols; y++)
				this.grid[x][y] = filler.apply(x, y);
	}
	
	private static String pair(int x, int y) { return "(" + x + "," + y + ")"; }
	private void checkRow(int row) { if (row < 0 || row >= this.rows) throw new IndexOutOfBoundsException("Rows: " + this.rows + ", Given: " + row); }
	private void checkCol(int col) { if (col < 0 || col >= this.cols) throw new IndexOutOfBoundsException("Cols: " + this.cols + ", Given: " + col); }
	private void checkPos(int row, int col) { if (row < 0 || row >= this.rows || col < 0 || col >= this.cols) throw new IndexOutOfBoundsException("Dimensions: " + pair(this.rows, this.cols) + ", Given: " + pair(row, col)); }
	
	public void clear()
	{
		for (int x = 0; x < this.rows; x++)
			for (int y = 0; y < this.cols; y++)
				this.grid[x][y] = null;
		this.grid = new Object[0][0];
	}
	
	public boolean contains(T o)
	{
		for (int x = 0; x < this.rows; x++)
			for (int y = 0; y < this.cols; y++)
				if (o == null ? this.grid[x][y] == null : o.equals(this.grid[x][y]))
					return true;
		return false;
	}
	
	public void ensureCapacity(int rows, int cols)
	{
		if (rows > this.rows || cols > this.cols)
		{
			Object[][] replacement = new Object[Math.max(rows, this.rows)][Math.max(cols, this.cols)];
			
			for (int x = 0; x < this.rows; x++)
				for (int y = 0; y < this.cols; y++)
					replacement[x][y] = this.grid[x][y];
			
			for (int x = this.rows; x < rows; x++)
				for (int y = 0; y < this.cols; y++)
					replacement[x][y] = this.defaultValue;
			
			for (int x = 0; x < rows; x++)
				for (int y = this.cols; y < cols; y++)
					replacement[x][y] = this.defaultValue;
			
			this.grid = replacement;
			this.rows = rows;
			this.cols = cols;
		}
	}
	
	public T get(int row, int col)
	{
		checkPos(row, col);
		return (T)this.grid[row][col];
	}
	
	public boolean isEmpty()
	{
		return this.size() == 0;
	}
	
	public void removeCol(int col)
	{
		checkCol(col);
		
		Object[][] replacement = new Object[this.rows][this.cols - 1];
		
		for (int x = 0; x < this.rows; x++)
		{
			for (int y = 0; y < col; y++)
				replacement[x][y] = this.grid[x][y];
			
			for (int y = col + 1; y < this.cols; y++)
				replacement[x][y - 1] = this.grid[x][y];
		}
		
		this.grid = replacement;
		this.cols--;
	}
	
	public void removeRow(int row)
	{
		checkRow(row);
		
		Object[][] replacement = new Object[this.rows - 1][this.cols];
		for (int x = 0; x < row; x++)
			replacement[x] = this.grid[x];
		
		for (int x = row + 1; x < this.rows; x++)
			replacement[x - 1] = this.grid[x];
		
		this.grid = replacement;
		this.rows--;
	}
	
	public void set(int row, int col, T value)
	{
		checkPos(row, col);
		this.grid[row][col] = value;
	}
	
	public int size()
	{
		return this.rows * this.cols;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		for (int x = 0; x < this.rows; x++)
			sb.append(Arrays.toString(this.grid[x])).append('\n');
		
		return sb.toString();
	}
	
	public Iterator<T> iterator()
	{
		return new Itr();
	}
	
	private class Itr implements Iterator<T>
	{
		private int cursorRow = 0;
		private int cursorCol = 0;
		
		public boolean hasNext()
		{
			return this.cursorCol + ((this.cursorRow) * Grid.this.cols) < Grid.this.size();
		}
		
		public T next()
		{
			if (!this.hasNext()) throw new NoSuchElementException();
			T element = (T)Grid.this.grid[this.cursorRow][this.cursorCol];
			if (++this.cursorCol >= Grid.this.cols)
			{
				this.cursorCol = 0;
				this.cursorRow++;
			}
			return element;
		}
	}
	
	public static void main(String[] args)
	{
		Grid<Integer> g = new Grid<>(5, 6, (Integer x, Integer y) -> x + y);
		for (Integer i : g)
		{
			System.out.println(i);
		}
	}
}
