package gridspace;

import utility.Color;
import utility.Direction;
import utility.Grid;
import utility.RailDirection;

public final class GridLiaison
{
	private Grid<GridSpace> grid;
	private Grid<GridSpaceLiaison> liaisons;
	private int roryRow;
	private int roryCol;
	
	public GridLiaison(int rows, int cols, int roryRow, int roryCol)
	{
		this.grid = new Grid<>(rows, cols, (Integer x, Integer y) -> new Space());
		this.liaisons = new Grid<>(rows, cols);
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
	
	public void ensureCapacity(int rows, int cols) { this.grid.ensureCapacity(rows, cols); this.liaisons.ensureCapacity(rows, cols); }
	public void trim(int rows, int cols) { this.grid.trim(rows, cols); this.liaisons.trim(rows, cols); }
	
	public int getCellID(int row, int col) { return this.grid.get(row, col).ID(); }
	
	public GridSpaceLiaison getCell(int row, int col)
	{
		GridSpaceLiaison cached = this.liaisons.get(row, col);
		if (cached == null) this.liaisons.set(row, col, cached = new GridSpaceLiaison(row, col, this.grid));
		return cached;
	}
	
	public GridSpaceLiaison setCell(int row, int col, int ID)
	{
		this.grid.set(row, col, GridSpace.createInstance(ID, row, col));
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
				if (ID == GridSpace.PORTAL) ((Portal)this.grid.get(this.row, this.col)).color = color;
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
}
