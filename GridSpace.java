public abstract class GridSpace
{
	private static final GridSpace WALL = new Wall();
	
	public static GridSpace getWall() { return WALL; }
	public static GridSpace getSpace() { return new Space(); }
	
	private boolean art = false;
	public boolean hasArt() { return this.art; }
	public boolean setArt() { return this.art = true; }
	public abstract boolean isSolid();
	public abstract GridSpace copy();
	public abstract String toString();
	public int hashCode() { return (this.art ? 0b100000 : 0); }
	public boolean equals(Object o) { return o instanceof GridSpace && (((GridSpace)o).art == this.art); }
	
	private static final class Wall extends GridSpace
	{
		public boolean hasArt() { return false; }
		public boolean isSolid() { return true; }
		public GridSpace copy() { return this; }
		public String toString() { return "W"; }
		public int hashCode() { return 0b0001; }
		public boolean equals(Object o) { return o instanceof Wall; }
	}
	
	private static final class Space extends GridSpace
	{
		public boolean isSolid() { return false; }
		public GridSpace copy() { return new Space(); }
		public String toString() { return " "; }
		public int hashCode() { return 0b0010 + super.hashCode(); }
		public boolean equals(Object o) { return o instanceof Space && super.equals(o); }
	}
}
