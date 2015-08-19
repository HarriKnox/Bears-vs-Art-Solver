public abstract class GridSpace
{
	protected boolean laser = false;
	
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
		public GridSpace copy() { return new Space(this.row, this.col); }
		public String toString() { return " "; }
	}
	
	public static final class Wall extends GridSpace
	{
		public boolean isSolid() { return true; }
		public boolean setLaser(boolean status) { return false; }
		public GridSpace copy() { return this; }
		public String toString() { return "W"; }
	}
}
