package gridspace;

import solver.GameState;
import utility.Directions;

public abstract class GridSpace
{
	private static final GridSpace WALL = new Wall();
	
	public static GridSpace getWall() { return WALL; }
	public static GridSpace getSpace() { return new Space(); }
	
	
	private boolean art = false;
	public boolean hasArt() { return this.art; }
	public boolean setArt() { return this.art = true; }
	
	private boolean laser = false;
	public boolean hasLaser() { return this.laser; }
	public boolean setLaser(boolean las) { return this.laser = las; }
	
	private int laserSource = Directions.NONE;
	private boolean laserBlue = false;
	private boolean laserOn = false;
	public int laserSourceDirection() { return this.laserSource; }
	public int setLaserSource(int dir) { return this.laserSource = dir; }
	public boolean laserSourceOn() { return this.laserOn; }
	
	private int laserArtHash()
	{
		return (this.laserSource << 4) +
		       (this.laserBlue ? 0b1000 : 0) +
		       (this.laserOn ? 0b0100 : 0) +
		       (this.laser ? 0b0010 : 0) +
		       (this.art ? 0b0001 : 0);
	}
	
	
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
	
	
	protected abstract int ID();
	protected abstract int metadataHash();
	
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
