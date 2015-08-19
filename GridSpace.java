public abstract class GridSpace
{
	private static final GridSpace WALL = new Wall();
	
	public boolean isSolid() { return false; }
	public abstract GridSpace copy();
	
	public static GridSpace getSpace() { return new Space(); }
	public static GridSpace getWall() { return WALL; }
	
	private static final class Space extends GridSpace
	{
		public GridSpace copy() { return new Space(); }
		public String toString() { return " "; }
	}
	
	private static final class Wall extends GridSpace
	{
		public boolean isSolid() { return true; }
		public GridSpace copy() { return this; }
		public String toString() { return "W"; }
	}
}
