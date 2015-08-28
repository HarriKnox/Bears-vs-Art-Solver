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
	public boolean setArt(boolean has) { return this.art = has; }
	
	private boolean laser = false;
	public boolean hasLaser() { return this.laser; }
	public boolean setLaser(boolean las) { return this.laser = las; }
	
	private int laserSource = Directions.NONE;
	private boolean laserBlue = false;
	private boolean laserOn = false;
	public int laserSourceDirection() { return this.laserSource; }
	public int setLaserSource(int dir) { return this.laserSource = dir; }
	public boolean laserSourceOn() { return this.laserOn; }
	public boolean setLaserSourceOn(boolean on) { return this.laserOn = on; }
	
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
		if (this.art) return "\033[41ma\033[0m");
		return String.valueOf(l);
	}
	
	
	public abstract boolean isSolid();
	public void passThrough(GameState state) {;}
	public void endOfMove(GameState state) { this.laserOn = this.laserSource != Directions.NONE && (this.laserBlue || !this.laserOn); }
	public void landedOn(GameState state) { this.art = false; }
	
	
	protected abstract int ID();
	protected abstract int metadataHash();
	
	public abstract GridSpace copy();
	protected final GridSpace copy(GridSpace gs) { gs.art = this.art; return gs; }
	
	public final int hashCode() { return (this.metadataHash() << 12) + (this.laserArtHash() << 4) + this.ID(); }
	public final boolean equals(Object that) { return (that instanceof GridSpace) && (this.hashCode() == that.hashCode()); }
}
