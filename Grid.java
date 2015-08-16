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
			Arrays.fill(this.grid[x], defaultValue);
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
			Arrays.fill(this.grid[x], null);
		this.grid = (T[][])(new Object[0][0]);
	}
	
	public boolean contains(T o)
	{
		for (int x = 0; x < this.rows; x++)
			for (int y = 0; y < this.cols; y++)
				if (Objects.equals(o, this.grid[x][y]))
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
		
		return Arrays.deepEquals(this.grid, ((Grid<?>)o).grid);
	}
	
	public int hashCode()
	{
		return Arrays.deepHashCode(this.grid);
	}
	
	public String toString()
	{
		return Arrays.deepToString(this.grid).replace("], [", "]\n [");
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
			rows = Math.max(rows, this.rows);
			cols = Math.max(cols, this.cols);
			T[][] replacement = (T[][])(new Object[rows][cols]);
			
			for (int x = 0; x < rows; x++)
				Arrays.fill(replacement[x], this.defaultValue);
			
			for (int x = 0; x < this.rows; x++)
				System.arraycopy(this.grid[x], 0, replacement[x], 0, this.cols);
			
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
			System.arraycopy(this.grid[x], 0, replacement[x], 0, col);
			System.arraycopy(this.grid[x], col + 1, replacement[x], col, this.cols - col - 1);
		}
		
		this.grid = replacement;
		this.cols--;
	}
	
	@SuppressWarnings("unchecked")
	public void removeRow(int row)
	{
		checkRow(row);
		
		T[][] replacement = (T[][])(new Object[this.rows - 1][this.cols]);
		
		System.arraycopy(this.grid, 0, replacement, 0, row);
		System.arraycopy(this.grid, row + 1, replacement, row, this.rows - row - 1);
		
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
				if (!Objects.equals(this.grid[x][y], this.defaultValue))
				{
					if (x > lastRow) lastRow = x;
					if (y > lastCol) lastCol = y;
				}
			}
		}
		
		this.trim(lastRow + 1, lastCol + 1);
	}
	
	@SuppressWarnings("unchecked")
	public void trim(int rows, int cols)
	{
		if (rows < 0 || rows > this.rows || cols < 0 || cols > this.cols) throw new IndexOutOfBoundsException("Dimensions: " + pair(this.rows, this.cols) + ", Given: " + pair(rows, cols));
		if (rows != this.rows || cols != this.cols)
		{
			T[][] replacement = (T[][])new Object[rows][cols];
			
			for (int x = 0; x < rows; x++)
				System.arraycopy(this.grid[x], 0, replacement[x], 0, cols);
			
			this.grid = replacement;
			this.rows = rows;
			this.cols = cols;
		}
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
		Integer[] ints = {valueOf(0), valueOf(1), valueOf(2), valueOf(3), valueOf(4), valueOf(5), valueOf(6)};
		int index = 6;
		int size = ints.length;
		int numMoved = size - index - 1;
		
		Integer[] otherInts = new Integer[size - 1];
		System.arraycopy(ints, 0, otherInts, 0, index);
		System.arraycopy(ints, index + 1, otherInts, index, numMoved);
		
		Grid<Integer> g = new Grid<>(3, 4);
		g.set(2, 1, valueOf(2));
		g.trim(3, 5);
		System.out.println(g);
	} 
}
