package gridspace;

import solver.GameState;
import utility.Direction;
import java.util.ArrayList;
import java.util.List;

public abstract class GridSpace
{
	static final List<Class> IDs = new ArrayList<>(9);
	static
	{
		IDs.add(Wall.class);
		IDs.add(Space.class);
		IDs.add(Spike.class);
		IDs.add(Booster.class);
		IDs.add(Button.class);
		IDs.add(ButtonDoor.class);
		IDs.add(MoveDoor.class);
		IDs.add(Teleporter.class);
		IDs.add(SlideDoor.class);
	}
	
	public static final int WALL        = IDs.indexOf(Wall.class);
	public static final int SPACE       = IDs.indexOf(Space.class);
	public static final int SPIKE       = IDs.indexOf(Spike.class);
	public static final int BOOSTER     = IDs.indexOf(Booster.class);
	public static final int BUTTON      = IDs.indexOf(Button.class);
	public static final int BUTTON_DOOR = IDs.indexOf(ButtonDoor.class);
	public static final int MOVE_DOOR   = IDs.indexOf(MoveDoor.class);
	public static final int TELEPORTER  = IDs.indexOf(Teleporter.class);
	public static final int SLIDE_DOOR  = IDs.indexOf(SlideDoor.class);
	
	
	int row;
	int col;
	boolean art = false;
	boolean laser = false;
	Direction laserSourceDirection = Direction.NONE;
	boolean laserSourceBlue = false;
	boolean laserSourceOn = false;
	boolean isLaserSource() { return this.laserSourceDirection != Direction.NONE; }
	
	private final int laserArtHash()
	{
		return (this.laserSourceDirection.hash << 4) +
		       (this.isLaserSource() ? (b2i(this.laserSourceBlue) << 3) + (b2i(this.laserSourceOn) << 2) : 0) + 
		       (b2i(this.laser) << 1) +
		       b2i(this.art);
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
	
	
	final int ID() { return IDs.indexOf(this.getClass()); }
	abstract int metadataHash();
	
	abstract GridSpace makeCopy();
	public final GridSpace copy()
	{
		GridSpace gs = this.makeCopy();
		gs.art = this.art;
		gs.laser = this.laser;
		if (this.isLaserSource())
		{
			gs.laserSourceOn = this.laserSourceOn;
			gs.laserSourceBlue = this.laserSourceBlue;
			gs.laserSourceDirection = this.laserSourceDirection;
		}
		return gs;
	}
	
	
	final GridSpace createInstance(int ID, int row, int col);
	{
		GridSpace gs;
		
		if      (ID == SPACE)       gs = new Space();
		else if (ID == SPIKE)       gs = new Spike();
		else if (ID == BOOSTER)     gs = new Booster();
		else if (ID == BUTTON)      gs = new Button();
		else if (ID == BUTTON_DOOR) gs = new ButtonDoor();
		else if (ID == MOVE_DOOR)   gs = new MoveDoor();
		else if (ID == TELEPORTER)  gs = new Teleporter();
		else if (ID == SLIDE_DOOR)  gs = new SlideDoor();
		else                        gs = new Wall();
		
		gs.row = row;
		gs.col = col;
		return gs;
	}
	
	
	String toString(char l)
	{
		StringBuilder sb = new StringBuilder();
		if (this.laser && this.art) sb.append("\033[43m");
		else if (this.laser) sb.append("\033[41m");
		else if (this.art) sb.append("\033[42m");
		sb.append(l);
		if (this.art || this.laser) sb.append("\033[0m");
		return sb.toString();
	}
	
	
	public final int hashCode() { return (this.metadataHash() << 12) + (this.laserArtHash() << 4) + this.ID(); }
	public final boolean equals(Object obj)
	{
		if (obj == this) return true;
		if (obj instanceof GridSpace)
		{
			GridSpace that = (GridSpace)obj;
			return (this.row == that.row) && (this.col == that.col) && (this.hashCode() == that.hashCode());
		}
		return false;
	}
	
	static final int b2i(boolean thing) { return thing ? 1 : 0; }
}
