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
	public boolean isSolid() { return false; };
	
	public boolean hasLaser() { return this.laser; }
	public void setLaser(boolean status) { this.laser = status; }
	
	public static final class Space extends GridSpace {}
	
	public static final class Wall extends GridSpace
	{
		@Override public boolean isSolid() { return true; }
	}
}
