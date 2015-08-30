package gridspace;

import solver.GameState;
import utility.Directions;

public abstract class GridSpace
{
	private static final GridSpace WALL = new Wall();
	
	public static final GridSpace getWall() { return WALL; }
	public static final GridSpace getSpace() { return new Space(); }
	public static final GridSpace getSpike() { return new Spike(); }
	
	
	boolean art = false;
	boolean laser = false;
	int laserSourceDirection = Directions.NONE;
	boolean laserSourceBlue = false;
	boolean laserSourceOn = false;
	
	private final int laserArtHash()
	{
		return (this.laserSourceDirection << 4) +
		       (b2i(this.laserBlue) << 3) +
		       (b2i(this.laserOn) << 2) +
		       (b2i(this.laser) << 1) +
		       b2i(this.art);
	}
	
	
	public abstract String toString();
	String toString(char l)
	{
		StringBuilder sb = new StringBuilder();
		if (this.laser) sb.append("\033[41m");
		if (this.art) sb.append("\033[32m");
		sb.append(l);
		if (this.art || this.laser) sb.append("\033[0m");
		return sb.toString();
	}
	
	
	public boolean isSolid() { return false; }
	
	
	public void passThrough(GameState state) { ; }
	public void checkHazard(GameState state) { if (this.laser || this.isSolid()) state.kill(); }
	public void endOfMove(GameState state) { this.laserOn = (this.isLaserSource() && (this.laserBlue || !this.laserOn)); }
	public void landedOn(GameState state)
	{
		if (this.art)
		{
			this.art = false;
			state.decrementArt();
		}
	}
	
	
	abstract int ID();
	abstract int metadataHash();
	
	public abstract GridSpace copy();
	final GridSpace copy(GridSpace gs)
	{
		gs.art = this.art;
		gs.laser = this.laser;
		gs.laserSourceOn = this.laserSourceOn;
		gs.laserSourceBlue = this.laserSourceBlue;
		gs.laserSourceDirection = this.laserSourceDirection;
		return gs;
	}
	
	public final int hashCode() { return (this.metadataHash() << 12) + (this.laserArtHash() << 4) + this.ID(); }
	public final boolean equals(Object that) { return (that instanceof GridSpace) && (this.hashCode() == that.hashCode()); }
	
	static final int b2i(boolean thing) { return thing ? 1 : 0; }
}
