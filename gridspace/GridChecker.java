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
		(7) portals of the same length having a mismatch in entry-exit directions
		(8) 1 or 3+ portals of a color (can have only 0 or 2)
		
		@todo Add the following
		(9) rails that are cycles have all sliding blocks going the same direction
		(A) rails that are unidirectional (only go back and forth) have no more than one sliding block on it
	**/
	public static void checkGrid(Grid<GridSpace> grid) throws IllegalStateException
	{
		int colors = Color.values().length;
		int[] portalColors = new int[colors];
		Direction[][] portalDirections = new Direction[colors][];
		Grid.Position[] portalLocations = new Grid.Position[colors];
		
		List<SlideDoor> slideDoors = new LinkedList<>();
		
		for (int row = 0, rows = grid.rows(); row < rows; row++)
		{
			for (int col = 0, cols = grid.cols(); col < cols; col++)
			{
				GridSpace cell = grid.get(row, col);
				int ID = cell.ID();
				
				if (ID == GridSpace.SLIDE_DOOR) checkRail(grid, row, col, (SlideDoor)cell);
				else if (ID == GridSpace.BOOSTER)
				{
					Booster b = (Booster)cell;
					if (b.rotates) checkBoosterRotates(grid, row, col);
					else checkBooster(grid, row, col, b.direction);
				}
				else if (ID == GridSpace.PORTAl)
				{
					Portal tp = (Portal)cell;
					int color = tp.color.hash;
					
					if (++portalColors[color] >= 3)
						throw new IllegalStateException(new StringBuilder("More than two ").append(tp.color).append(" portals").toString());
					
					Direction[] posDirs = GridTraveler.getPossibleDirections(grid, row, col);
					if (portalDirections[color] == null)
					{
						portalDirections[color] = posDirs;
						portalLocations[color] = new Grid.Position(row, col);
					}
					else
					{
						Grid.Position pos = portalLocations[color];
						checkPortals(tp.color, row, col, pos.row, pos.col, posDirs, portalDirections[color]);
					}
				}
			}
		}
		
		for (Color color : Color.values())
			if (portalColors[color.hash] == 1)
				throw new IllegalStateException(new StringBuilder("Found only 1 ").append(color).append(" portal at ").append(portalLocations[color.hash]).toString());
	}
	
	
	private static String coords(int row, int col) { return Grid.Position.pair(row, col); }
	private static String points(Direction dir, boolean rotates)    { return (rotates ? "rotates to point " : "points ").concat(dir.toString()); }
	
	private static StringBuilder thingAt (String thing, int row, int col) { return new StringBuilder(thing).append(" at ").append(coords(row, col)).append(' '); }
	private static StringBuilder thingAtPoints(String thing, int row, int col, Direction dir, boolean rotates) { return thingAt(thing, row, col).append(points(dir, rotates)); }
	
	private static <T> boolean contains(T[] things, T thing) { for (T t : things) if (t.equals(thing)) return true; return false; }
	
	
	private static void checkRail(Grid<GridSpace> grid, int row, int col, SlideDoor sd)
	{
		Direction dir = sd.heading;
		RailDirection rail = sd.rail;
		
		if (sd.up && !rail.contains(dir))
			throw new IllegalStateException(thingAtPoints("Sliding block", row, col, dir).append(" on a rail that goes ").append(rail.getFirst()).append(" and ").append(rail.getLast()).toString());
		
		Direction first = rail.getFirst();
		checkRailDirection(grid, row, col, first);
		
		Direction last = rail.getLast();
		if (!last.equals(first)) checkRailDirection(grid, row, col, last);
		
		slideDoors.add(sd);
	}
	
	private static void checkRailDirection(Grid<GridSpace> grid, int row, int col, Direction dir)
	{
		int r = row + dir.verticalChange();
		int c = col + dir.horizontalChange();
		if (!grid.inRange(r, c))
			throw new IllegalStateException(thingAtPoints("Rail", row, col, dir, false).append(" out of bounds").toString());
			
		GridSpace oCell = grid.get(r, c);
		if (oCell.ID() != GridSpace.SLIDE_DOOR)
			throw new IllegalStateException(thingAtPoints("Rail", row, col, dir, false).append(" into a non-rail").toString());
			
		SlideDoor osd = (SlideDoor)oCell;
		RailDirection oRail = osd.rail;
		if (!oRail.contains(dir.opposite()))
			throw new IllegalStateException(thingAtPoints("Rail", row, col, dir, false).append(" into rail that does not point ").append(dir.opposite()).toString());
	}
	
	
	private static void checkBooster         (Grid<GridSpace> grid, int row, int col, Direction dir) { checkBoosterDirection(grid, row, col, dir, false); }
	private static void checkBoosterRotates  (Grid<GridSpace> grid, int row, int col)                { for (Direction dir : Direction.values()) if (dir.isCardinal()) checkBoosterDirection(grid, row, col, dir, true); }
	private static void checkBoosterDirection(Grid<GridSpace> grid, int row, int col, Direction dir, boolean rotates)
	{
		String name = rotates ? rotatingBoosterName : boosterName;
		int r = row + dir.verticalChange();
		int c = col + dir.horizontalChange();
		if (!grid.inRange(r, c))
			throw new IllegalStateException(thingAtPoints(name, row, col, dir, rotates).append(" out of bounds").toString());
			
		GridSpace oCell = grid.get(r, c);
		int oID = oCell.ID();
		if (oID == GridSpace.WALL || oID == GridSpace.BUTTON_DOOR || oID == GridSpace.MOVE_DOOR || oID == GridSpace.SLIDE_DOOR)
			throw new IllegalStateException(thingAtPoints(name, row, col, dir, rotates).append(" directly into a block that can be solid").toString());
	}
	private static final String rotatingBoosterName = "Rotating booster";
	private static final String boosterName = "Booster";
	
	
	private static void checkPortals(Color color, int entryRow, int entryCol, int exitRow, int exitCol, Direction[] entries, Direction[] exits)
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
	private static String portalMismatch(Color color, Grid.Position entry, Grid.Position exit, Direction dir) { return new StringBuilder().append(color).append(" portals have mismatch in directions: exit portal at ").append(exit).append(" does not allow for entry in portal at ").append(entry).append(" going ").append(dir).toString(); }
}
