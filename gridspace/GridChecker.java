package gridspace;

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
		(7) teleporters of the same length having a mismatch in entry-exit directions
		(8) 1 or 3+ teleporters of a color (can have only 0 or 2)
		
		@todo Add the following
		(9) rails that are cycles have all sliding blocks going the same direction
		(A) rails that are unidirectional (eg, UP_UP) have no more than one sliding block on it
	**/
	public static void checkGrid(Grid<GridSpace> grid) throws IllegalStateException
	{
		int colors = Color.values().length;
		int[] teleporterColors = new int[colors];
		Direction[][] teleporterDirections = new Direction[colors][];
		Grid.Position[] teleporterLocations = new Grid.Position[colors];
		
		for (int row = 0, rows = grid.rows(); row < rows; row++)
		{
			for (int col = 0, cols = grid.cols(); col < cols; col++)
			{
				GridSpace cell = grid.get(row, col);
				int ID = cell.ID();
				
				if (ID == GridSpace.SLIDE_DOOR)
				{
					SlideDoor sd = (SlideDoor)cell;
					Direction dir = sd.heading;
					RailDirection rail = sd.rail;
					if (sd.up && !rail.contains(dir)) throw new IllegalStateException(thingAtPoints("Sliding block", row, col, dir).append(" on a rail that goes ").append(rail.getFirst()).append(" and ").append(rail.getLast()).toString());
					
					Direction first = rail.getFirst();
					checkRail(grid, row, col, first);
					
					Direction last = rail.getLast();
					if (!last.equals(first)) checkRail(grid, row, col, last);
				}
				else if (ID == GridSpace.BOOSTER)
				{
					Booster b = (Booster)cell;
					if (b.rotates)
						checkBoosterRotates(grid, row, col);
					else
						checkBooster(grid, row, col, b.direction);
				}
				else if (ID == GridSpace.TELEPORTER)
				{
					Teleporter tp = (Teleporter)cell;
					int color = tp.color.hash;
					if (++teleporterColors[color] >= 3) throw new IllegalStateException(new StringBuilder("More than two ").append(tp.color).append(" teleporters").toString());
					Direction[] posDirs = GridTraveler.getPossibleDirections(grid, row, col);
					if (teleporterDirections[color] == null)
					{
						teleporterDirections[color] = posDirs;
						teleporterLocations[color] = new Grid.Position(row, col);
					}
					else
					{
						Grid.Position pos = teleporterLocations[color];
						checkTeleporters(tp.color, row, col, pos.row, pos.col, posDirs, teleporterDirections[color]);
					}
				}
			}
		}
		
		for (Color color : Color.values()) if (teleporterColors[color.hash] == 1) throw new IllegalStateException(new StringBuilder("Found only 1 ").append(color).append(" teleporter at ").append(teleporterLocations[color.hash]).toString());
	}
	
	private static String coords(int row, int col) { return Grid.Position.pair(row, col); }
	private static String points(Direction dir)    { return "points ".concat(dir.toString()); }
	
	private static StringBuilder thingAt      (String thing, int row, int col)                { return new StringBuilder(thing).append(" at ").append(coords(row, col)).append(' '); }
	private static StringBuilder thingAtPoints(String thing, int row, int col, Direction dir) { return thingAt(thing, row, col).append(points(dir)); }
	
	private static <T> boolean contains(T[] things, T thing) { for (T t : things) if (t.equals(thing)) return true; return false; }
	
	private static void checkRail(Grid<GridSpace> grid, int row, int col, Direction dir)
	{
		int r = row + dir.verticalChange();
		int c = col + dir.horizontalChange();
		if (!grid.inRange(r, c)) throw new IllegalStateException(thingAtPoints("Rail", row, col, dir).append(" out of bounds").toString());
		GridSpace oCell = grid.get(r, c);
		if (oCell.ID() != GridSpace.SLIDE_DOOR) throw new IllegalStateException(thingAtPoints("Rail", row, col, dir).append(" into a non-rail").toString());
		SlideDoor osd = (SlideDoor)oCell;
		RailDirection oRail = osd.rail;
		if (!oRail.contains(dir.opposite())) throw new IllegalStateException(thingAtPoints("Rail", row, col, dir).append(" into rail that does not point ").append(dir.opposite()).toString());
	}
	
	private static void checkBooster         (Grid<GridSpace> grid, int row, int col, Direction dir) { checkBoosterDirection(grid, row, col, dir, false); }
	private static void checkBoosterRotates  (Grid<GridSpace> grid, int row, int col)                { for (Direction dir : Direction.values()) if (dir.isCardinal()) checkBoosterDirection(grid, row, col, dir, true); }
	private static void checkBoosterDirection(Grid<GridSpace> grid, int row, int col, Direction dir, boolean rotates)
	{
		String name = rotates ? "Rotating booster" : "Booster";
		int r = row + dir.verticalChange();
		int c = col + dir.horizontalChange();
		if (!grid.inRange(r, c)) throw new IllegalStateException(thingAt(name, row, col).append(rotates ? "can rotate to point ".concat(dir.toString()) : points(dir)).append(" out of bounds").toString());
		GridSpace oCell = grid.get(r, c);
		int oID = oCell.ID();
		if (oID == GridSpace.WALL || oID == GridSpace.BUTTON_DOOR || oID == GridSpace.MOVE_DOOR || oID == GridSpace.SLIDE_DOOR)
			throw new IllegalStateException(thingAt(name, row, col).append(rotates ? "can rotate to point ".concat(dir.toString()) : points(dir)).append(" directly into a block that can be solid").toString());
	}
	
	private static void checkTeleporters(Color color, int entryRow, int entryCol, int exitRow, int exitCol, Direction[] entries, Direction[] exits)
	{
		for (Direction dir : Direction.values())
		{
			if (dir != Direction.NONE)
			{
				if (contains(entries, dir.opposite()) && !contains(exits, dir))
					throw new IllegalStateException(teleporterMismatch(color, new Grid.Position(entryRow, entryCol), new Grid.Position(exitRow, exitCol), dir));
				else if (contains(exits, dir.opposite()) && !contains(entries, dir))
					throw new IllegalStateException(teleporterMismatch(color, new Grid.Position(exitRow, exitCol), new Grid.Position(entryRow, entryCol), dir));
			}
		}
	}
	private static String teleporterMismatch(Color color, Grid.Position entry, Grid.Position exit, Direction dir) { return new StringBuilder().append(color).append(" teleporters have mismatch in directions: exit teleporter at ").append(exit).append(" does not allow for entry in teleporter at ").append(entry).append(" going ").append(dir).toString(); }
}