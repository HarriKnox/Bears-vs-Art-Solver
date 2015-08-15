import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

import java.util.function.BiFunction;
import java.util.function.Consumer;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

import static java.util.Objects.requireNonNull;


import java.util.ArrayList;
import static java.lang.Integer.valueOf;

public class Grid<T> implements Iterable<T>
{
	private T[][] grid;
	private int rows;
	private int cols;
	private T defaultValue;
	
	@SuppressWarnings("unchecked")
	public Grid(int rows, int cols)
	{
		this.grid = (T[][])(new Object[rows][cols]);
		this.rows = rows;
		this.cols = cols;
		this.defaultValue = null;
	}
	
	public Grid(int rows, int cols, T defaultValue)
	{
		this(rows, cols);
		for (int x = 0; x < rows; x++)
			for (int y = 0; y < cols; y++)
				this.grid[x][y] = defaultValue;
		this.defaultValue = defaultValue;
	}
	
	public Grid(int rows, int cols, BiFunction<Integer, Integer, T> filler)
	{
		this(rows, cols);
		requireNonNull(filler);
		for (int x = 0; x < rows; x++)
			for (int y = 0; y < cols; y++)
				this.grid[x][y] = filler.apply(x, y);
	}
	
	public T get(int row, int col)
	{
		checkPos(row, col);
		return this.grid[row][col];
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
	
	public boolean isEmpty()
	{
		return this.size() == 0;
	}
	
	@SuppressWarnings("unchecked")
	public void clear()
	{
		for (int x = 0; x < this.rows; x++)
			for (int y = 0; y < this.cols; y++)
				this.grid[x][y] = null;
		this.grid = (T[][])(new Object[0][0]);
	}
	
	public boolean contains(T o)
	{
		for (int x = 0; x < this.rows; x++)
			for (int y = 0; y < this.cols; y++)
				if Objects.equals(o, this.grid[x][y])
					return true;
		return false;
	}
	
	public boolean inRange(int row, int col)
	{
		return row >= 0 && row < this.rows && col >= 0 && col < this.cols;
	}
	
	public boolean equals(Object o)
	{
		if (o == this) return true;
		if (!(o instanceof Grid)) return false;
		
		Grid<?> g = (Grid<?>)o;
		
		return Arrays.deepEquals(this.grid, g.grid);
	}
	
	public int hashCode()
	{
		int hash = 1;
		
		for (T thing : this)
			hash = (15 * hash) + Objects.hashCode(thing);
		
		return hash;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		for (int x = 0; x < this.rows; x++)
			sb.append(Arrays.toString(this.grid[x])).append('\n');
		
		return sb.toString();
	}
	
	public void changeDefault(T newDefault)
	{
		this.defaultValue = newDefault;
	}
	
	@SuppressWarnings("unchecked")
	public void ensureCapacity(int rows, int cols)
	{
		if (rows > this.rows || cols > this.cols)
		{
			T[][] replacement = (T[][])(new Object[Math.max(rows, this.rows)][Math.max(cols, this.cols)]);
			
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
	
	public void forEach(Consumer<? super T> action)
	{
		requireNonNull(action);
		final int expectedRows = this.rows;
		final int expectedCols = this.cols;
		
		for (int x = 0; x < this.rows; x++)
		{
			for (int y = 0; y < this.cols; y++)
			{
				this.checkConcurrentModification(expectedRows, expectedCols);
				action.accept(this.grid[x][y]);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void removeCol(int col)
	{
		checkCol(col);
		
		T[][] replacement = (T[][])(new Object[this.rows][this.cols - 1]);
		
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
	
	@SuppressWarnings("unchecked")
	public void removeRow(int row)
	{
		checkRow(row);
		
		T[][] replacement = (T[][])(new Object[this.rows - 1][this.cols]);
		for (int x = 0; x < row; x++)
			replacement[x] = this.grid[x];
		
		for (int x = row + 1; x < this.rows; x++)
			replacement[x - 1] = this.grid[x];
		
		this.grid = replacement;
		this.rows--;
	}
	
	@SuppressWarnings("unchecked")
	public void trim()
	{
		int lastRow = 0;
		int lastCol = 0;
		
		for (int x = 0; x < this.rows; x++)
		{
			for (int y = 0; y < this.cols; y++)
			{
				if (this.grid[x][y] == null ? this.defaultValue == null : this.grid[x][y].equals(this.defaultValue))
				{
					if (x > lastRow) lastRow = x;
					if (y > lastCol) lastCol = y;
				}
			}
		}
		
		T[][] replacement = (T[][])new Object[lastRow][lastCol];
		
		for (int x = 0; x < lastRow; x++)
		{
			for (int y = 0; y < lastCol; y++)
			{
				replacement[x][y] = this.grid[x][y];
			}
		}
		
		this.grid = replacement;
		this.rows = lastRow;
		this.cols = lastCol;
	}
	
	private static String pair(int x, int y) { return "(" + x + "," + y + ")"; }
	private void checkRow(int row) { if (row < 0 || row >= this.rows) throw new IndexOutOfBoundsException("Rows: " + this.rows + ", Given: " + row); }
	private void checkCol(int col) { if (col < 0 || col >= this.cols) throw new IndexOutOfBoundsException("Cols: " + this.cols + ", Given: " + col); }
	private void checkPos(int row, int col) { if (!this.inRange(row, col)) throw new IndexOutOfBoundsException("Dimensions: " + pair(this.rows, this.cols) + ", Given: " + pair(row, col)); }
	private void checkConcurrentModification(int expectedRows, int expectedCols) { if (expectedRows != this.rows || expectedCols != this.cols) throw new ConcurrentModificationException(); }
	
	public Iterator<T> iterator()
	{
		return new Itr();
	}
	
	private class Itr implements Iterator<T>
	{
		private int cursorRow = 0;
		private int cursorCol = 0;
		
		private final int expectedRows = Grid.this.rows;
		private final int expectedCols = Grid.this.cols;
		
		public boolean hasNext()
		{
			return this.cursorCol + ((this.cursorRow) * Grid.this.cols) < Grid.this.size();
		}
		
		public T next()
		{
			Grid.this.checkConcurrentModification(this.expectedRows, this.expectedCols);
			if (!this.hasNext()) throw new NoSuchElementException();
			T element = Grid.this.grid[this.cursorRow][this.cursorCol];
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
		Grid<Number> g = new Grid<>(3, 6);
		Grid<Integer> h = new Grid<>(6, 3);
		g.set(1, 2, valueOf(5));
		h.set(2, 2, valueOf(5));
		System.out.println(g.equals(h));
		Grid<Integer> i = new Grid<>(6, 3);
		i.set(2, 2, valueOf(5));
		System.out.println(h.equals(i));
	}
}
