package utility;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

import static java.util.Objects.requireNonNull;


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
	
	public int rows()
	{
		return this.rows;
	}
	
	public int cols()
	{
		return this.cols;
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
		this.rows = 0;
		this.cols = 0;
	}
	
	public Position positionOf(T o)
	{
		for (int x = 0; x < this.rows; x++)
			for (int y = 0; y < this.cols; y++)
				if (Objects.equals(o, this.grid[x][y]))
					return new Position(x, y);
		return null;
	}
	
	public boolean contains(T o)
	{
		return this.positionOf(o) != null;
	}
	
	public boolean inRange(int row, int col)
	{
		return row >= 0 && row < this.rows && col >= 0 && col < this.cols;
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
	
	public void forEachPosition(BiConsumer<Integer, Integer> action)
	{
		requireNonNull(action);
		final int expectedRows = this.rows;
		final int expectedCols = this.cols;
		
		for (int x = 0; x < this.rows; x++)
		{
			for (int y = 0; y < this.cols; y++)
			{
				this.checkConcurrentModification(expectedRows, expectedCols);
				action.accept(x, y);
			}
		}
	}
	
	public boolean whileTrue(Predicate<? super T> action)
	{
		requireNonNull(action);
		final int expectedRows = this.rows;
		final int expectedCols = this.cols;
		
		for (int x = 0; x < this.rows; x++)
		{
			for (int y = 0; y < this.cols; y++)
			{
				this.checkConcurrentModification(expectedRows, expectedCols);
				if (action.test(this.grid[x][y]) == false) return false;
			}
		}
		
		return true;
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
		if (rows < 0 || rows > this.rows || cols < 0 || cols > this.cols) throw new IndexOutOfBoundsException("Dimensions: " + Position.pair(this.rows, this.cols) + ", Given: " + Position.pair(rows, cols));
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
	
	private void checkRow(int row) { if (row < 0 || row >= this.rows) throw new IndexOutOfBoundsException("Rows: " + this.rows + ", Given: " + row); }
	private void checkCol(int col) { if (col < 0 || col >= this.cols) throw new IndexOutOfBoundsException("Cols: " + this.cols + ", Given: " + col); }
	private void checkPos(int row, int col) { if (!this.inRange(row, col)) throw new IndexOutOfBoundsException("Dimensions: " + Position.pair(this.rows, this.cols) + ", Given: " + Position.pair(row, col)); }
	private void checkConcurrentModification(int expectedRows, int expectedCols) { if (expectedRows != this.rows || expectedCols != this.cols) throw new ConcurrentModificationException(); }
	
	public static class Position
	{
		public final int row;
		public final int col;
		
		public Position(int row, int col)
		{
			this.row = row;
			this.col = col;
		}
		private static final String COMMA = ", ";
		public static String pair(int x, int y) { return new StringBuilder().append('(').append(x).append(COMMA).append(y).append(')').toString(); }
		public String toString() { return pair(this.row, this.col); }
		public boolean equals(Object o) { return o instanceof Position && (this == o || (((Position)o).row == this.row && ((Position)o).col == this.col)); }
		public int hashCode() { return this.row * 31 + this.col; }
	}
	
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
}
