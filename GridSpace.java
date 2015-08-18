public abstract class GridSpace
{
	public final int row;
	public final int col;
	protected boolean laser = false;
	
	public GridSpace(int row, int col)
	{
		this.row = row;
		this.col = col;
	}
	
	public void interact() {;}
	public void endOfMove() {;}
	public boolean isSolid() { return false; }
	public abstract GridSpace copy();
	
	public boolean hasLaser() { return this.laser; }
	public boolean setLaser(boolean status)
	{
		this.laser = status;
		return this.laser;
	}
	
	public static final class Space extends GridSpace
	{
		public Space(int row, int col) { super(row, col); }
		public GridSpace copy() { return new Space(this.row, this.col); }
		public String toString() { return " "; }
	}
	
	public static final class Wall extends GridSpace
	{
		public Wall(int row, int col) { super(row, col); }
		
		public boolean isSolid() { return true; }
		public boolean setLaser(boolean status) { return false; }
		public GridSpace copy() { return this; }
		public String toString() { return "W"; }
	}
}
