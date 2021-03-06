package gridspace;

import solver.GameState;

import utility.Direction;
import utility.Grid;
import utility.RailDirection;

final class SlideDoor extends GridSpace
{
	SlideDoor() {}
	
	RailDirection rail = RailDirection.LEFT_RIGHT;
	Direction heading = Direction.RIGHT;
	boolean up = false;
	boolean touched = false;
	
	
	int metadataHash() { return (this.rail.hash << 5) + (this.heading.hash << 1) + b2i(this.up); }
	
	GridSpace makeCopy()
	{
		SlideDoor sd = new SlideDoor();
		sd.rail = this.rail;
		sd.heading = this.heading;
		sd.up = this.up;
		return sd;
	}
	
	
	public boolean isSolid() { return this.up; }
	
	
	public void endOfMove(GameState state)
	{
		if (this.up && !this.touched)
		{
			this.up = false;
			
			Grid<GridSpace> gameboard = state.getGameBoard();
			
			int dr = this.heading.verticalChange();
			int dc = this.heading.horizontalChange();
			
			int r = this.row + dr;
			int c = this.col + dc;
			SlideDoor sd = (SlideDoor)gameboard.get(r, c);
		
			sd.up = true;
			sd.touched = true;
			
			Direction first = sd.rail.getFirst();
			sd.heading = (first == this.heading.opposite()) ? sd.rail.getLast() : first;
			
			if (state.getRoryRow() == r && state.getRoryCol() == c) state.teleportRory(r + dr, c + dc);
		}
		super.endOfMove(state);
	}
	
	
	public String toString() { return "\033[1;35m" + super.toString(this.up ? this.heading == Direction.UP ? '^' : (this.heading == Direction.DOWN ? 'v' : (this.heading == Direction.LEFT ? '<' : '>')) : '|') + "\033[0m"; }
}
