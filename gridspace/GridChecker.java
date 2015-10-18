package gridspace;

import java.util.LinkedList;
import java.util.Set;
import java.util.List;
import java.util.HashSet;

import utility.Color;
import utility.Direction;
import utility.Grid;
import utility.RailDirection;

public final class GridChecker
{
	/**
		This function checks the given grid and will throw an
		exception if the grid has an illegal state:
		(1) sliding doors heading a direction their rails don't allow
		(2) rails that point out of bounds
		(3) rails that point to a non-rail
		(4) rails that point into rails that don't point back (disconnection)
		(5) boosters that point directly out of bounds
		(6) boosters that point directly into a potentially solid block (boosters
			can't point into solid blocks, and even if it isn't solid yet,
			it may be solid later)
		(7) this.portals of the same length having a mismatch in entry-exit directions
		(8) 1 or 3+ this.portals of a color (can have only 0 or 2)
		
		@todo Add the following
		(9) rails that are cycles have all sliding blocks going the same direction
		(A) rails that are bidirectional (only go back and forth) have no more than one sliding block on it
	**/
	public static void checkGrid(Grid<GridSpace> grid) throws IllegalStateException
	{
		new GridChecker(grid).checkGrid();
	}
	
	private static final int COLORS = Color.values().length;
	private int[] portalColors = new int[COLORS];
	private Direction[][] portalDirections = new Direction[COLORS][];
	private Grid.Position[] portalLocations = new Grid.Position[COLORS];
	
	private LinkedList<SlideDoor> monoSlideDoors = new LinkedList<>();
	private LinkedList<SlideDoor> upSlideDoors = new LinkedList<>();
	
	private Grid<GridSpace> grid;
	
	private GridChecker(Grid<GridSpace> grid)
	{
		this.grid = grid;
	}
	
	private void checkGrid()
	{
		for (int row = 0, rows = this.grid.rows(); row < rows; row++)
		{
			for (int col = 0, cols = this.grid.cols(); col < cols; col++)
			{
				GridSpace cell = this.grid.get(row, col);
				int ID = cell.ID();
				
				if (ID == GridSpace.SLIDE_DOOR) checkRail((SlideDoor)cell);
				else if (ID == GridSpace.BOOSTER) checkBooster((Booster)cell);
				else if (ID == GridSpace.PORTAL) checkPortal((Portal)cell);
			}
		}
		
		checkRailTracks();
		checkPortalCount();
	}
	
	
	private void checkRail(SlideDoor sd)
	{
		int row = sd.row;
		int col = sd.col;
		Direction dir = sd.heading;
		RailDirection rail = sd.rail;
		
		if (sd.up)
		{
			if(!rail.contains(dir))
				throw new IllegalStateException(thingAtPoints("Sliding block", row, col, dir, false).append(" on a rail that goes ").append(rail.getFirst()).append(" and ").append(rail.getLast()).toString());
			this.upSlideDoors.add(sd);
		}
		
		checkRailDirection(row, col, rail.getFirst());
		
		if (!rail.isMono())
			checkRailDirection(row, col, rail.getLast());
		else // rail is mono, rail.first == rail.last
			this.monoSlideDoors.add(sd);
	}
	
	private void checkRailDirection(int row, int col, Direction dir)
	{
		int r = row + dir.verticalChange();
		int c = col + dir.horizontalChange();
		if (!this.grid.inRange(r, c))
			throw new IllegalStateException(thingAtPoints("Rail", row, col, dir, false).append(" out of bounds").toString());
			
		GridSpace oCell = this.grid.get(r, c);
		if (oCell.ID() != GridSpace.SLIDE_DOOR)
			throw new IllegalStateException(thingAtPoints("Rail", row, col, dir, false).append(" into a non-rail").toString());
			
		SlideDoor osd = (SlideDoor)oCell;
		RailDirection oRail = osd.rail;
		if (!oRail.contains(dir.opposite()))
			throw new IllegalStateException(thingAtPoints("Rail", row, col, dir, false).append(" into rail that does not point ").append(dir.opposite()).toString());
	}
	
	private void checkRailTracks()
	{
		HashSet<SlideDoor> checked = new HashSet<>(this.monoSlideDoors.size() + this.upSlideDoors.size(), 1.0F);
		
		for (SlideDoor sd : this.monoSlideDoors)
		{
			if (!checked.contains(sd))
			{
				checked.add(sd);
				
				Grid.Position upDoorPos = null;
				
				SlideDoor door = sd;
				Direction heading = sd.rail.getFirst();
				
				do
				{
					int row = door.row;
					int col = door.col;
					
					if (door.up)
					{
						Grid.Position doorPos = new Grid.Position(row, col);
						if (upDoorPos != null)
							throw new IllegalStateException(twoDoorsOnBidirectional(upDoorPos, doorPos));
						
						upDoorPos = doorPos;
						checked.add(door);
					}
					
					int r = row + heading.verticalChange();
					int c = col + heading.horizontalChange();
					
					door = (SlideDoor)this.grid.get(r, c);
					
					Direction first = door.rail.getFirst();
					heading = (first == heading.opposite()) ? door.rail.getLast() : first;
				}
				while (!door.rail.isMono());
				
				checked.add(door);
				
				if (door.up && (upDoorPos != null))
					throw new IllegalStateException(twoDoorsOnBidirectional(upDoorPos, new Grid.Position(door.row, door.col)));
			}
		}
	}
	
	private static String twoDoorsOnBidirectional(Grid.Position first, Grid.Position second) { return new StringBuilder("Two doors at ").append(first).append(" and ").append(second).append(" are on the same bidirectional rail: a collision will eventually occur").toString(); }
	
	
	private void checkBooster(Booster b)
	{
		int row = b.row;
		int col = b.col;
		
		if (b.rotates)
		{
			for (Direction dir : Direction.values())
				if (dir.isCardinal())
					checkBoosterDirection(row, col, dir, true);
		}
		else
		{
			checkBoosterDirection(row, col, b.direction, false);
		}
	}
	
	private void checkBoosterDirection(int row, int col, Direction dir, boolean rotates)
	{
		String name = rotates ? rotatingBoosterName : boosterName;
		int r = row + dir.verticalChange();
		int c = col + dir.horizontalChange();
		if (!this.grid.inRange(r, c))
			throw new IllegalStateException(thingAtPoints(name, row, col, dir, rotates).append(" out of bounds").toString());
			
		GridSpace oCell = this.grid.get(r, c);
		int oID = oCell.ID();
		if (oID == GridSpace.WALL || oID == GridSpace.BUTTON_DOOR || oID == GridSpace.MOVE_DOOR || oID == GridSpace.SLIDE_DOOR)
			throw new IllegalStateException(thingAtPoints(name, row, col, dir, rotates).append(" directly into a block that can be solid").toString());
	}
	
	private static final String rotatingBoosterName = "Rotating booster";
	private static final String boosterName = "Booster";
	
	
	private void checkPortal(Portal tp)
	{
		int color = tp.color.hash;
		int row = tp.row;
		int col = tp.col;
		
		if (++this.portalColors[color] >= 3)
			throw new IllegalStateException(new StringBuilder("More than two ").append(tp.color).append(" this.portals").toString());
		
		Direction[] posDirs = GridTraveler.getPossibleDirections(this.grid, row, col);
		if (this.portalDirections[color] == null)
		{
			this.portalDirections[color] = posDirs;
			this.portalLocations[color] = new Grid.Position(row, col);
		}
		else
		{
			Grid.Position pos = this.portalLocations[color];
			checkPortalDirections(tp.color, row, col, pos.row, pos.col, posDirs, this.portalDirections[color]);
		}
	}
	
	private void checkPortalDirections(Color color, int entryRow, int entryCol, int exitRow, int exitCol, Direction[] entries, Direction[] exits)
	{
		for (Direction dir : Direction.values())
		{
			if (dir != Direction.NONE)
			{
				if (contains(entries, dir.opposite()) && !contains(exits, dir))
					throw new IllegalStateException(portalMismatch(color, new Grid.Position(entryRow, entryCol), new Grid.Position(exitRow, exitCol), dir));
				else if (contains(exits, dir.opposite()) && !contains(entries, dir))
					throw new IllegalStateException(portalMismatch(color, new Grid.Position(exitRow, exitCol), new Grid.Position(entryRow, entryCol), dir));
			}
		}
	}
	
	private void checkPortalCount()
	{
		for (Color color : Color.values())
			if (this.portalColors[color.hash] == 1)
				throw new IllegalStateException(new StringBuilder("Found only 1 ").append(color).append(" portal at ").append(this.portalLocations[color.hash]).toString());
	}
	
	private static String portalMismatch(Color color, Grid.Position entry, Grid.Position exit, Direction dir) { return new StringBuilder().append(color).append(" this.portals have mismatch in directions: exit portal at ").append(exit).append(" does not allow for entry in portal at ").append(entry).append(" going ").append(dir).toString(); }
	
	
	private static String coords(int row, int col) { return Grid.Position.pair(row, col); }
	private static String points(Direction dir, boolean rotates) { return (rotates ? "rotates to point " : "points ").concat(dir.toString()); }
	
	private static StringBuilder thingAt (String thing, int row, int col) { return new StringBuilder(thing).append(" at ").append(coords(row, col)).append(' '); }
	private static StringBuilder thingAtPoints(String thing, int row, int col, Direction dir, boolean rotates) { return thingAt(thing, row, col).append(points(dir, rotates)); }
	
	private static <T> boolean contains(T[] things, T thing) { for (T t : things) if (t.equals(thing)) return true; return false; }
}
