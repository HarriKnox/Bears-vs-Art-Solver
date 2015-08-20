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
	protected final GridSpace copy(GridSpace gs) { gs.art = this.art; return gs; }
	public void passOver() {;}
	public void endOfMove() {;}
	public void landedOn() { this.art = false; }
	
	protected abstract int ID();
	private int laserArtHash() { return (this.art ? 1 << 4 : 0); }
	protected int metadataHash() { return 0; }
	public final int hashCode() { return this.metadataHash() + this.laserArtHash() + this.ID(); }
	public boolean equals(Object o) { return o instanceof GridSpace && (((GridSpace)o).art == this.art); }
	public abstract String toString();
	
	private static final class Wall extends GridSpace
	{
		protected int ID() { return 1; }
		public boolean setArt() { return false; }
		public boolean isSolid() { return true; }
		public GridSpace copy() { return this; }
		public String toString() { return "W"; }
		public boolean equals(Object o) { return o instanceof Wall; }
	}
	
	private static final class Space extends GridSpace
	{
		protected int ID() { return 2; }
		public boolean isSolid() { return false; }
		public GridSpace copy() { return super.copy(new Space()); }
		public String toString() { return " "; }
		public boolean equals(Object o) { return o instanceof Space && super.equals(o); }
	}
}
