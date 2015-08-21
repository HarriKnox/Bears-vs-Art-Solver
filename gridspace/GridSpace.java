package gridspace;

import solver.GameState;

public abstract class GridSpace
{
	private static final GridSpace WALL = new Wall();
	
	public static GridSpace getWall() { return WALL; }
	public static GridSpace getSpace() { return new Space(); }
	
	
	private boolean art = false;
	private boolean laser = false;
	public boolean hasArt() { return this.art; }
	public boolean setArt() { return this.art = true; }
	public boolean hasLaser() { return this.laser; }
	
	protected abstract int ID();
	private int laserArtHash() { return (this.laser ? 0b10 : 0) + (this.art ? 0b01 : 0); }
	protected abstract int metadataHash();
	public abstract String toString();
	protected String toString(char l)
	{
		StringBuilder sb = new StringBuilder();
		if (this.art) sb.append("\033[41m");
		sb.append(l);
		if (this.art) sb.append("\033[0m");
		return sb.toString();
	}
	
	public abstract boolean isSolid();
	public void passThrough(GameState state) {;}
	public void endOfMove(GameState state) {;}
	public void landedOn(GameState state) { this.art = false; }
	
	public abstract GridSpace copy();
	protected final GridSpace copy(GridSpace gs) { gs.art = this.art; return gs; }
	
	public final int hashCode() { return (this.metadataHash() << 12) + (this.laserArtHash() << 4) + this.ID(); }
	public final boolean equals(Object that) { return (that instanceof GridSpace) && (this.hashCode() == that.hashCode()); }
	
	private static final class Wall extends GridSpace
	{
		public boolean setArt() { return false; }
		
		protected int ID() { return 1; }
		protected int metadataHash() { return 0; }
		
		public boolean isSolid() { return true; }
		public GridSpace copy() { return this; }
		
		public String toString() { return super.toString('W'); }
	}
	
	private static final class Space extends GridSpace
	{
		protected int ID() { return 2; }
		protected int metadataHash() { return 0; }
		
		public boolean isSolid() { return false; }
		public GridSpace copy() { return super.copy(new Space()); }
		
		public String toString() { return super.toString(' '); }
	}
}
