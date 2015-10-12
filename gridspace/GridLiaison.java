package gridspace;

import utility.*;

public final class GridLiaison
{
	private Grid<GridSpace> grid;
	private int roryRow = -1;
	private int roryCol = -1;
	
	private static final Wall THE_WALL = new Wall();
	
	public GridLiaison(int rows, int cols, int roryRow, int roryCol)
	{
		this.grid = new Grid<>(rows, cols, (Integer x, Integer y) -> new Space());
		this.setRory(roryRow, roryCol);
	}
	
	public void setRory(int row, int col)
	{
		this.roryRow = row;
		this.roryCol = col;
	}
	
	public int roryRow() { return this.roryRow; }
	public int roryCol() { return this.roryCol; }
	
	public int size() { return this.grid.size(); }
	public int rows() { return this.grid.rows(); }
	public int cols() { return this.grid.cols(); }
	
	public void ensureCapacity(int rows, int cols) { this.grid.ensureCapacity(rows, cols); }
	public void trim(int rows, int cols) { this.grid.trim(rows, cols); }
	
	public int getCellID(int row, int col) { return this.grid.get(row, col).ID(); }
	public GridSpaceLiaison getCell(int row, int col) { return new GridSpaceLiaison(row, col, this.grid); }
	public GridSpaceLiaison setCell(int row, int col, int ID)
	{
		GridSpace gs = THE_WALL;
		
		if (ID == GridSpace.SPACE)       gs = new Space();
		if (ID == GridSpace.SPIKE)       gs = new Spike();
		if (ID == GridSpace.BOOSTER)     gs = new Booster();
		if (ID == GridSpace.BUTTON)      gs = new Button();
		if (ID == GridSpace.BUTTON_DOOR) gs = new ButtonDoor();
		if (ID == GridSpace.MOVE_DOOR)   gs = new MoveDoor();
		if (ID == GridSpace.TELEPORTER)  gs = new Teleporter();
		if (ID == GridSpace.SLIDE_DOOR)  gs = new SlideDoor();
		
		this.grid.set(row, col, gs);
		
		return this.getCell(row, col);
	}
	
	public final class GridSpaceLiaison
	{
		int row;
		int col;
		Grid<GridSpace> grid;
		
		GridSpaceLiaison(int row, int col, Grid<GridSpace> grid)
		{
			this.row = row;
			this.col = col;
			this.grid = grid;
		}
		
		private boolean isOpen(int row, int col) { return this.grid.inRange(row, col) && this.grid.get(row, col).ID() > GridSpace.WALL; }
		
		public GridSpaceLiaison setArt(boolean art)
		{
			if (this.isOpen(this.row, this.col) && (GridLiaison.this.getCellID(this.row, this.col) != GridSpace.BOOSTER)) this.grid.get(this.row, this.col).art = art;
			return this;
		}
		
		public GridSpaceLiaison setLaserSourceDirection(Direction dir)
		{
			if (this.isOpen(this.row, this.col)) this.grid.get(this.row, this.col).laserSourceDirection = dir;
			return this;
		}
		
		public GridSpaceLiaison setLaserSourceBlue(boolean blue)
		{
			if (this.isOpen(this.row, this.col)) this.grid.get(this.row, this.col).laserSourceBlue = blue;
			return this;
		}
		
		public GridSpaceLiaison setLaserSourceOn(boolean on)
		{
			if (this.isOpen(this.row, this.col)) this.grid.get(this.row, this.col).laserSourceOn = on;
			return this;
		}
		
		
		public GridSpaceLiaison setUp(boolean up)
		{
			if (this.isOpen(this.row, this.col))
			{
				int ID = GridLiaison.this.getCellID(this.row, this.col);
				
				if (ID == GridSpace.SPIKE) ((Spike)this.grid.get(this.row, this.col)).up = up;
				if (ID == GridSpace.BUTTON) ((Button)this.grid.get(this.row, this.col)).up = up;
				if (ID == GridSpace.BUTTON_DOOR) ((ButtonDoor)this.grid.get(this.row, this.col)).up = up;
				if (ID == GridSpace.MOVE_DOOR) ((MoveDoor)this.grid.get(this.row, this.col)).up = up;
				if (ID == GridSpace.SLIDE_DOOR) ((SlideDoor)this.grid.get(this.row, this.col)).up = up;
			}
			return this;
		}
		
		public GridSpaceLiaison setDirection(Direction dir)
		{
			if (this.isOpen(this.row, this.col))
			{
				int ID = GridLiaison.this.getCellID(this.row, this.col);
				
				if (ID == GridSpace.BOOSTER) { if (dir.isCardinal()) ((Booster)this.grid.get(this.row, this.col)).direction = dir; }
				if (ID == GridSpace.SLIDE_DOOR) { if (dir.isCardinal()) ((SlideDoor)this.grid.get(this.row, this.col)).heading = dir; }
			}
			return this;
		}
		
		public GridSpaceLiaison setRotates(boolean rotates)
		{
			if (this.isOpen(this.row, this.col))
			{
				int ID = GridLiaison.this.getCellID(this.row, this.col);
				
				if (ID == GridSpace.BOOSTER) ((Booster)this.grid.get(this.row, this.col)).rotates = rotates;
			}
			return this;
		}
		
		public GridSpaceLiaison setClockwise(boolean clockwise)
		{
			if (this.isOpen(this.row, this.col))
			{
				int ID = GridLiaison.this.getCellID(this.row, this.col);
				
				if (ID == GridSpace.BOOSTER) ((Booster)this.grid.get(this.row, this.col)).clockwise = clockwise;
			}
			return this;
		}
		
		public GridSpaceLiaison setColor(Color color)
		{
			if (this.isOpen(this.row, this.col))
			{
				int ID = GridLiaison.this.getCellID(this.row, this.col);
				
				if (ID == GridSpace.BUTTON) ((Button)this.grid.get(this.row, this.col)).color = color;
				if (ID == GridSpace.BUTTON_DOOR) ((ButtonDoor)this.grid.get(this.row, this.col)).color = color;
				if (ID == GridSpace.TELEPORTER) ((Teleporter)this.grid.get(this.row, this.col)).color = color;
			}
			return this;
		}
		
		public GridSpaceLiaison setToggle(boolean toggle)
		{
			if (this.isOpen(this.row, this.col))
			{
				int ID = GridLiaison.this.getCellID(this.row, this.col);
				
				if (ID == GridSpace.MOVE_DOOR) ((MoveDoor)this.grid.get(this.row, this.col)).toggle = toggle;
			}
			return this;
		}
		
		public GridSpaceLiaison setMovesStarted(int moves)
		{
			if (this.isOpen(this.row, this.col))
			{
				int ID = GridLiaison.this.getCellID(this.row, this.col);
				
				if (ID == GridSpace.MOVE_DOOR)
				{
					MoveDoor md = (MoveDoor)this.grid.get(this.row, this.col);
					md.movesStarted = md.movesRemaining = moves;
				}
			}
			return this;
		}
		
		public GridSpaceLiaison setRailDirection(RailDirection rail)
		{
			if (this.isOpen(this.row, this.col))
			{
				int ID = GridLiaison.this.getCellID(this.row, this.col);
				
				if (ID == GridSpace.SLIDE_DOOR) ((SlideDoor)this.grid.get(this.row, this.col)).rail = rail;
			}
			return this;
		}
	}
	
	public GridSpaceLiaison setArt                 (int row, int col, boolean art)        { return new GridSpaceLiaison(row, col, this.grid).setArt(art); }
	public GridSpaceLiaison setLaserSourceDirection(int row, int col, Direction dir)      { return new GridSpaceLiaison(row, col, this.grid).setLaserSourceDirection(dir); }
	public GridSpaceLiaison setLaserSourceBlue     (int row, int col, boolean blue)       { return new GridSpaceLiaison(row, col, this.grid).setLaserSourceBlue(blue); }
	public GridSpaceLiaison setLaserSourceOn       (int row, int col, boolean on)         { return new GridSpaceLiaison(row, col, this.grid).setLaserSourceOn(on); }
	public GridSpaceLiaison setUp                  (int row, int col, boolean up)         { return new GridSpaceLiaison(row, col, this.grid).setUp(up); }
	public GridSpaceLiaison setDirection           (int row, int col, Direction dir)      { return new GridSpaceLiaison(row, col, this.grid).setDirection(dir); }
	public GridSpaceLiaison setRotates             (int row, int col, boolean rot)        { return new GridSpaceLiaison(row, col, this.grid).setRotates(rot); }
	public GridSpaceLiaison setClockwise           (int row, int col, boolean clock)      { return new GridSpaceLiaison(row, col, this.grid).setClockwise(clock); }
	public GridSpaceLiaison setColor               (int row, int col, Color color)        { return new GridSpaceLiaison(row, col, this.grid).setColor(color); }
	public GridSpaceLiaison setToggle              (int row, int col, boolean toggle)     { return new GridSpaceLiaison(row, col, this.grid).setToggle(toggle); }
	public GridSpaceLiaison setMovesStarted        (int row, int col, int moves)          { return new GridSpaceLiaison(row, col, this.grid).setMovesStarted(moves); }
	public GridSpaceLiaison setRailDirection       (int row, int col, RailDirection rail) { return new GridSpaceLiaison(row, col, this.grid).setRailDirection(rail); }
	
	
	public static int countArt(Grid<GridSpace> grid)
	{
		int count = 0;
		for (GridSpace gs : grid)
			if (gs.art)
				count++;
		return count;
	}
	public int countArt() { return countArt(this.grid); }
	
	public static boolean hasLasers(Grid<GridSpace> grid)
	{
		for (GridSpace gs : grid)
			if (gs.isLaserSource())
				return true;
		return false;
	}
	public boolean hasLasers() { return hasLasers(this.grid); }
	
	public static Grid<GridSpace> copyGrid(Grid<GridSpace> grid) { return new Grid<GridSpace>(grid.rows(), grid.cols(), (Integer x, Integer y) -> grid.get(x, y).copy()); }
	public Grid<GridSpace> copyGrid() { return copyGrid(this.grid); }
	
	public static void updateLasers(Grid<GridSpace> gameBoard)
	{
		for (GridSpace gs : gameBoard)
		{
			gs.laser = false;
		}
		for (int x = 0, rows = gameBoard.rows(); x < rows; x++)
		{
			for (int y = 0, cols = gameBoard.cols(); y < cols; y++)
			{
				GridSpace square = gameBoard.get(x, y);
				if (square.isLaserSource() && square.laserSourceOn)
				{
					Direction dir = square.laserSourceDirection;
					if (GridTraveler.checkDir(gameBoard, x, y, Direction.NONE))
					{
						square.laser = true;
						int row = x;
						int col = y;
						
						while (GridTraveler.canGo(gameBoard, row, col, dir))
						{
							row += dir.verticalChange();
							col += dir.horizontalChange();
							gameBoard.get(row, col).laser = true;
						}
					}
				}
			}
		}
	}
	public void updateLasers() { updateLasers(this.grid); }
	
	/**
		This function checks the given grid and will throw an
		exception if the grid has an illegal state:
		- disconnected rails
		- boosters that point directly into a wall
		- 1 or 3+ teleporters of a color (can have only 0 or 2)
		- teleporters positioned next to a wall that would teleport Rory into a wall
	**/
	public static void checkGrid(Grid<GridSpace> grid) throws IllegalStateException
	{
		int colors = Color.values().length;
		int[] teleporterColors = new int[colors];
		Direction[][] teleporterDirections = new Direction[colors][];
		int[][] teleporterLocations = new int[colors][2];
		
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
					teleporterColors[color]++;
					Direction[] posDirs = GridTraveler.getPossibleDirections(grid, row, col);
					if (teleporterDirections[color] == null)
					{
						teleporterDirections[color] = posDirs;
						teleporterLocations[color] = new int[]{row, col};
					}
					else
					{
						int[] pos = teleporterLocations[color];
						checkTeleporters(tp.color, row, col, pos[0], pos[1], posDirs, teleporterDirections[color]);
					}
				}
			}
		}
	}
	public void checkGrid() throws IllegalStateException { checkGrid(this.grid); }
	
	private static String coords(int row, int col) { return new StringBuilder("(").append(row).append(", ").append(col).append(")").toString(); }
	private static String points(Direction dir)    { return "points ".concat(dir.toString()); }
	
	private static StringBuilder thingAt      (String thing, int row, int col)                { return new StringBuilder(thing).append(" at ").append(coords(row, col)).append(' '); }
	private static StringBuilder thingAtPoints(String thing, int row, int col, Direction dir) { return thingAt(thing, row, col).append(points(dir)); }
	
	private static <T extends Enum> boolean contains(T[] things, T thing) { for (T t : things) if (t == thing) return true; return false; }
	
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
					throw new IllegalStateException(teleporterMismatch(color, entryRow, entryCol, exitRow, exitCol, dir));
				else if (contains(exits, dir.opposite()) && !contains(entries, dir))
					throw new IllegalStateException(teleporterMismatch(color, exitRow, exitCol, entryRow, entryCol, dir));
			}
		}
	}
	private static String teleporterMismatch(Color color, int entryRow, int entryCol, int exitRow, int exitCol, Direction dir) { return new StringBuilder().append(color).append(" teleporters have mismatch in directions: exit teleporter at ").append(coords(exitRow, exitCol)).append(" does not allow for entry in teleporter at ").append(coords(entryRow, entryCol)).append(" going ").append(dir).toString(); }
}
