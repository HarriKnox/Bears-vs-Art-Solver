package gridspace;

import solver.GameState;
import utility.Directions;
import java.util.HashMap;
import java.util.Map;

public abstract class GridSpace
{
	static final Map<Class, Integer> IDs = new HashMap<>();
	static
	{
		IDs.put(Wall.class, 1);
		IDs.put(Space.class, 2);
		IDs.put(Spike.class, 3);
		IDs.put(Booster.class, 4);
	}
	
	public static final int WALL = IDs.get(Wall.class);
	public static final int SPACE = IDs.get(Space.class);
	public static final int SPIKE = IDs.get(Spike.class);
	public static final int BOOSTER = IDs.get(Booster.class);
	
	
	boolean art = false;
	boolean laser = false;
	int laserSourceDirection = Directions.NONE;
	boolean laserSourceBlue = false;
	boolean laserSourceOn = false;
	boolean isLaserSource() { return Directions.isDir(this.laserSourceDirection) && this.laserSourceDirection != Directions.NONE; }
	
	private final int laserArtHash()
	{
		return (this.laserSourceDirection << 4) +
		       (this.isLaserSource() ? (b2i(this.laserSourceBlue) << 3) + (b2i(this.laserSourceOn) << 2) : 0) + 
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
	public void endOfMove(GameState state) { this.laserSourceOn = (this.isLaserSource() && (this.laserSourceBlue || !this.laserSourceOn)); }
	public void landedOn(GameState state)
	{
		if (this.art)
		{
			this.art = false;
			state.decrementArt();
		}
	}
	
	
	final int ID() { return IDs.getOrDefault(this.getClass(), 0); }
	int metadataHash() { return 0; }
	
	abstract GridSpace makeCopy();
	public final GridSpace copy()
	{
		GridSpace gs = this.makeCopy();
		gs.art = this.art;
		gs.laser = this.laser;
		if (gs.isLaserSource())
		{
			gs.laserSourceOn = this.laserSourceOn;
			gs.laserSourceBlue = this.laserSourceBlue;
			gs.laserSourceDirection = this.laserSourceDirection;
		}
		return gs;
	}
	
	public final int hashCode() { return (this.metadataHash() << 12) + (this.laserArtHash() << 4) + this.ID(); }
	public final boolean equals(Object that) { return (that instanceof GridSpace) && (this.hashCode() == that.hashCode()); }
	
	static final int b2i(boolean thing) { return thing ? 1 : 0; }
}
